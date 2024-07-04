package com.ies.common;

import java.io.Serializable;
import java.util.Date;

public class Challenge implements Serializable {
    private int challengeId;
    private String description;
    private Date startDate;
    private Date endDate;
    private int duration;
    private int numQuestions;

    // Constructor, getters, and setters
}