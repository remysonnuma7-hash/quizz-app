package com.example.quiz.quiz;

import java.util.List;

public class Question {
    private final int id;
    private final Subject subject;
    private final String statement;
    private final List<String> choices; // A,B,C,D
    private final char correct; // 'A'..'D'

    public Question(int id, Subject subject, String statement, List<String> choices, char correct) {
        this.id = id;
        this.subject = subject;
        this.statement = statement;
        this.choices = choices;
        this.correct = Character.toUpperCase(correct);
    }

    public int getId() { return id; }
    public Subject getSubject() { return subject; }
    public String getStatement() { return statement; }
    public List<String> getChoices() { return choices; }
    public char getCorrect() { return correct; }
}
