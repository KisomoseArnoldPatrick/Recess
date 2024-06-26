-- Create the database
CREATE DATABASE IF NOT EXISTS mathcompetition;
USE mathcompetition;

-- Create Administrator table
CREATE TABLE Administrator (
    adminID INT PRIMARY KEY AUTO_INCREMENT,
    adminName VARCHAR(20) NOT NULL,
    userName VARCHAR(20) NOT NULL,
    password VARCHAR(50) NOT NULL
);

-- Create SchoolRepresentative table
CREATE TABLE SchoolRepresentative (
    schoolRepID INT PRIMARY KEY AUTO_INCREMENT,
    userName VARCHAR(20) NOT NULL,
    password VARCHAR(50) NOT NULL,
    emailAddress VARCHAR(30) NOT NULL UNIQUE,
    repName VARCHAR(50) NOT NULL
);

-- Create School table
CREATE TABLE School (
    schoolRegNo INT PRIMARY KEY,
    schoolName VARCHAR(30) NOT NULL,
    district VARCHAR(15) NOT NULL,
    schoolRepID INT NOT NULL,
    adminID INT NOT NULL,
    FOREIGN KEY (schoolRepID) REFERENCES SchoolRepresentative(schoolRepID),
    FOREIGN KEY (adminID) REFERENCES Administrator(adminID)
);

-- Create Participant table
CREATE TABLE Participant (
    participantID INT PRIMARY KEY AUTO_INCREMENT,
    applicantID INT NOT NULL UNIQUE,
    firstName VARCHAR(10) NOT NULL,
    lastName VARCHAR(15) NOT NULL,
    emailAddress VARCHAR(30) NOT NULL UNIQUE,
    dateOfBirth DATE NOT NULL,
    schoolRegNo INT NOT NULL,
    userName VARCHAR(20) NOT NULL,
    imagePath VARCHAR(10) NOT NULL,
    password VARCHAR(50) NOT NULL,
    schoolRepID INT NOT NULL,
    FOREIGN KEY (schoolRegNo) REFERENCES School(schoolRegNo),
    FOREIGN KEY (schoolRepID) REFERENCES SchoolRepresentative(schoolRepID)
);

-- Create Rejected table
CREATE TABLE Rejected (
    rejectedID INT PRIMARY KEY AUTO_INCREMENT,
    schoolRegNo INT NOT NULL,
    emailAddress VARCHAR(30) NOT NULL UNIQUE,
    applicantID INT NOT NULL UNIQUE,
    userName VARCHAR(20) NOT NULL,
    imagePath VARCHAR(10) NOT NULL,
    schoolRepID INT NOT NULL,
    firstName VARCHAR(10) NOT NULL,
    lastName VARCHAR(15) NOT NULL,
    password VARCHAR(50) NOT NULL,
    dateOfBirth DATE NOT NULL,
    FOREIGN KEY (schoolRegNo) REFERENCES School(schoolRegNo),
    FOREIGN KEY (schoolRepID) REFERENCES SchoolRepresentative(schoolRepID)
);

-- Create AnswerBank table
CREATE TABLE AnswerBank (
    answerBankID INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(10) NOT NULL,
    adminID INT,
    FOREIGN KEY (adminID) REFERENCES Administrator(adminID)
);

-- Create Answer table
CREATE TABLE Answer (
    answerNo INT PRIMARY KEY AUTO_INCREMENT,
    answer TEXT NOT NULL,
    marksAwarded INT NOT NULL,
    answerBankID INT,
    FOREIGN KEY (answerBankID) REFERENCES AnswerBank(answerBankID)
);

-- Create QuestionBank table
CREATE TABLE QuestionBank (
    questionBankID INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(10) NOT NULL,
    challengeNo INT NOT NULL,
    adminID INT NOT NULL,
    FOREIGN KEY (adminID) REFERENCES Administrator(adminID)
);

-- Create Question table
CREATE TABLE Question (
    questionNo INT PRIMARY KEY AUTO_INCREMENT,
    question TEXT NOT NULL,
    questionBankID INT,
    answerNo INT,
    FOREIGN KEY (questionBankID) REFERENCES QuestionBank(questionBankID),
    FOREIGN KEY (answerNo) REFERENCES Answer(answerNo)
);

-- Create Challenge table
CREATE TABLE Challenge (
    challengeNo INT PRIMARY KEY AUTO_INCREMENT,
    attemptDuration TIME NOT NULL,
    noOfQuestions INT NOT NULL,
    overallMark INT NOT NULL,
    openDate DATE NOT NULL,
    closeDate DATE NOT NULL,
    adminID INT,
    FOREIGN KEY (adminID) REFERENCES Administrator(adminID)
);

-- Create Attempt table
CREATE TABLE Attempt (
    attemptID INT PRIMARY KEY AUTO_INCREMENT,
    startTime TIME NOT NULL,
    endTime TIME NOT NULL,
    score INT NOT NULL,
    percentageMark DECIMAL(5,2) NOT NULL,
    participantID INT NOT NULL,
    challengeNo INT NOT NULL,
    FOREIGN KEY (participantID) REFERENCES Participant(participantID),
    FOREIGN KEY (challengeNo) REFERENCES Challenge(challengeNo)
);

-- Create AttemptQuestion table
CREATE TABLE AttemptQuestion (
    attemptID INT,
    questionNo INT,
    wrong BOOLEAN NOT NULL,
    correct BOOLEAN NOT NULL,
    negative BOOLEAN NOT NULL,
    givenAnswer TEXT,
    PRIMARY KEY (attemptID, questionNo),
    FOREIGN KEY (attemptID) REFERENCES Attempt(attemptID),
    FOREIGN KEY (questionNo) REFERENCES Question(questionNo)
);

-- Create ParticipantChallenge table
CREATE TABLE ParticipantChallenge (
    challengeNo INT,
    participantID INT,
    PRIMARY KEY (challengeNo, participantID),
    FOREIGN KEY (challengeNo) REFERENCES Challenge(challengeNo),
    FOREIGN KEY (participantID) REFERENCES Participant(participantID)
);

-- Create Applicant table
CREATE TABLE Applicant (
    applicantID INT PRIMARY KEY AUTO_INCREMENT,
    schoolRegNo INT NOT NULL,
    emailAddress VARCHAR(30) NOT NULL UNIQUE,
    userName VARCHAR(20) NOT NULL,
    imagePath VARCHAR(10) NOT NULL,
    firstName VARCHAR(10) NOT NULL,
    lastName VARCHAR(15) NOT NULL,
    password VARCHAR(50) NOT NULL,
    dateOfBirth DATE NOT NULL,
    FOREIGN KEY (schoolRegNo) REFERENCES School(schoolRegNo)
);