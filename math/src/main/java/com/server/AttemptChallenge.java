package com.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class AttemptChallenge {
    static String attemptChallenge(int challengeNo, PrintWriter out, BufferedReader in,int participantID) throws SQLException, IOException{
        System.out.println(challengeNo+ " "+participantID);

        // Fetch challenge details
        String Sql = "SELECT attemptDuration, challengeName FROM Challenge WHERE challengeNo = ? AND openDate <= CURDATE() AND closeDate >= CURDATE()";
        try(PreparedStatement stmt = Server.conn.prepareStatement(Sql)){
            stmt.setInt(1, challengeNo);
            try(ResultSet rs = stmt.executeQuery()){
                if (!rs.next()) {
                    return "Challenge is not open or does not exist.";
                }
                else{
                     // Check number of attempts
                     if (hasExceededAttempts(challengeNo,participantID)) {
                        return "You have already attempted this challenge 3 times.";
                    }
                    else{

                        String attemptDurationStr = rs.getString("attemptDuration");
                        LocalTime attemptDuration = LocalTime.parse(attemptDurationStr, DateTimeFormatter.ofPattern("HH:mm:ss"));
                        long durationInSeconds = attemptDuration.toSecondOfDay();
                        String challengeName = rs.getString("challengeName");

                         List<Map<String, Object>> questions = fetchRandomQuestions(challengeNo);

                         String description = String.format("Challenge: %s\nDuration: %s",
                            challengeName, attemptDuration.toString());
                    
                         out.println(description);
                         out.flush();

                         String startResponse = in.readLine();
                         if (!startResponse.equalsIgnoreCase("start")) {
                             return "Challenge cancelled.";
                         }

                         int attemptID = storeAttempt(challengeNo,participantID);
                        return conductChallenge(questions, durationInSeconds, attemptID,out,in);

                    } 
                }
            }
        }          
    }

    static String conductChallenge(List<Map<String, Object>> questions, long durationInSeconds,
     int attemptID, PrintWriter out, BufferedReader in) throws IOException, SQLException {
            int totalScore = 0;
            int totalMarks = 0;
            long startTime = System.currentTimeMillis();
            long endTime = startTime + (durationInSeconds * 1000);
        
            for (int i = 0; i < questions.size(); i++) {
                Map<String, Object> question = questions.get(i);
                long currentTime = System.currentTimeMillis();
                if (currentTime >= endTime) {
                    out.println("Time's up!");
                    out.flush();
                    break;
                }
        
                long remainingTime = endTime - currentTime;
                out.println(String.format("Question %d/%d", i + 1, questions.size()));
                out.println(question.get("question"));
                out.println(String.format("Remaining time: %s", formatDuration(remainingTime)));
                out.println("Enter your answer or '-' to skip:");
                out.flush();
        
                String userAnswer = readLineWithTimeout(remainingTime,in);
                if (userAnswer == null) {
                    out.println("Time's up for this question!");
                    out.flush();
                    userAnswer = "-";
                }
        
                int questionNo = (int) question.get("questionNo");
                int score = evaluateAnswer(questionNo, userAnswer);
                storeAttemptQuestion(attemptID, questionNo, score, userAnswer);
                totalScore += score;
                totalMarks += (int) question.get("marks");
        
                out.println("Answer recorded. Moving to next question...");
                out.flush();
            }
        
            out.println("END_OF_CHALLENGE");
            out.flush();
        
            double percentageMark = (double) totalScore / totalMarks * 100;
            saveAttemptResult(attemptID, startTime, totalScore, percentageMark);
        
            return String.format("Challenge completed. Your score: %d (%.2f%%)", totalScore, percentageMark);
        }

        static String readLineWithTimeout(long timeoutMillis, BufferedReader in) throws IOException {
            long startTime = System.currentTimeMillis();
            StringBuilder input = new StringBuilder();
            while (System.currentTimeMillis() - startTime < timeoutMillis) {
                if (in.ready()) {
                    int c = in.read();
                    if (c == -1 || c == '\n') {
                        break;
                    }
                    input.append((char) c);
                }
                try {
                    Thread.sleep(100); // Small delay to prevent busy-waiting
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return null;
                }
            }
            return input.length() > 0 ? input.toString() : null;
        }

        static boolean hasExceededAttempts(int challengeNo,int participantID) throws SQLException {
            String checkAttemptsSql = "SELECT COUNT(*) as attempts FROM Attempt WHERE challengeNo = ? AND participantID = ?";
            try (PreparedStatement attemptStmt = Server.conn.prepareStatement(checkAttemptsSql)) {
                attemptStmt.setInt(1, challengeNo);
                attemptStmt.setInt(2, participantID);
                ResultSet attemptRs = attemptStmt.executeQuery();
                return attemptRs.next() && attemptRs.getInt("attempts") >= 3;
            }
        }

        static List<Map<String, Object>> fetchRandomQuestions(int challengeNo) throws SQLException {
            String questionSql = "SELECT q.questionNo, q.question, a.answer, a.marksAwarded FROM Question q JOIN Answer a ON q.questionNo = a.questionNo WHERE q.questionBankID = (SELECT questionBankID FROM Challenge WHERE challengeNo = ?) ORDER BY RAND() LIMIT 10";
            List<Map<String, Object>> questions = new ArrayList<>();
            try (PreparedStatement questionStmt = Server.conn.prepareStatement(questionSql)) {
                questionStmt.setInt(1, challengeNo);
                ResultSet questionRs = questionStmt.executeQuery();
                while (questionRs.next()) {
                    Map<String, Object> question = new HashMap<>();
                    question.put("questionNo", questionRs.getInt("questionNo"));
                    question.put("question", questionRs.getString("question"));
                    question.put("answer", questionRs.getString("answer"));
                    question.put("marks", questionRs.getInt("marksAwarded"));
                    questions.add(question);
                }
            }
            return questions;
        }

        static int storeAttempt(int challengeNo, int participantID) throws SQLException {
            String insertAttemptSql = "INSERT INTO Attempt (startTime, participantID, challengeNo, endTime, score, percentageMark) VALUES (?, ?, ?, NULL, NULL, NULL)";
            try (PreparedStatement pstmt = Server.conn.prepareStatement(insertAttemptSql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
                pstmt.setInt(2, participantID);
                pstmt.setInt(3, challengeNo);
                pstmt.executeUpdate();
        
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Creating attempt failed, no ID obtained.");
                    }
                }
            }
        }
            static String formatDuration(long millis) {
            Duration duration = Duration.ofMillis(millis);
            long hours = duration.toHours();
            long minutes = duration.toMinutesPart();
            long seconds = duration.toSecondsPart();
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        }

        static int evaluateAnswer(int questionNo, String userAnswer) throws SQLException {
            String sql = "SELECT answer, marksAwarded FROM Answer WHERE questionNo = ?";
            try (PreparedStatement pstmt = Server.conn.prepareStatement(sql)) {
                pstmt.setInt(1, questionNo);
                ResultSet rs = pstmt.executeQuery();
                
                if (rs.next()) {
                    String correctAnswer = rs.getString("answer");
                    int marks = rs.getInt("marksAwarded");
                    
                    if (userAnswer.equals("-")) {
                        return 0;
                    } else if (userAnswer.equalsIgnoreCase(correctAnswer)) {
                        return marks;
                    } else {
                        return -3;
                    }
                } else {
                    throw new SQLException("No answer found for question " + questionNo);
                }
            }
        }

        static void storeAttemptQuestion(int attemptID, int questionNo, int score, String givenAnswer) throws SQLException {
            String insertSql = "INSERT INTO AttemptQuestion (attemptID, questionNo, score, givenAnswer) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = Server.conn.prepareStatement(insertSql)) {
                pstmt.setInt(1, attemptID);
                pstmt.setInt(2, questionNo);
                pstmt.setInt(3, score);
                pstmt.setString(4, givenAnswer);
                pstmt.executeUpdate();
            }
        }

        static void saveAttemptResult(int attemptID, long startTime, int totalScore, double percentageMark) throws SQLException {
            String saveAttemptSql = "UPDATE Attempt SET endTime = ?, score = ?, percentageMark = ? WHERE attemptID = ?";
            try (PreparedStatement saveStmt = Server.conn.prepareStatement(saveAttemptSql)) {
                saveStmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
                saveStmt.setInt(2, totalScore);
                saveStmt.setDouble(3, percentageMark);
                saveStmt.setInt(4, attemptID);
                saveStmt.executeUpdate();
            }
        }

}
