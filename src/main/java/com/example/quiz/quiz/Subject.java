package com.example.quiz.quiz;

public enum Subject {
    ENGLISH("Anglais"),
    MATH("Maths"),
    CHEMISTRY("Chimie"),
    MIXED("Mixte");

    private final String label;

    Subject(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }
}
