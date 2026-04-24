package com.example.quiz.quiz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuizSession implements Serializable {
    private Subject subject;
    private List<Integer> questionIds = new ArrayList<>();
    private int index = 0;
    private int score = 0;

    public QuizSession() {}

    public QuizSession(Subject subject, List<Integer> questionIds) {
        this.subject = subject;
        this.questionIds = questionIds;
    }

    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }

    public List<Integer> getQuestionIds() { return questionIds; }
    public void setQuestionIds(List<Integer> questionIds) { this.questionIds = questionIds; }

    public int getIndex() { return index; }
    public void setIndex(int index) { this.index = index; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public int total() { return questionIds == null ? 0 : questionIds.size(); }

    public boolean finished() { return total() == 0 || index >= total(); }
}
