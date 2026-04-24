package com.example.quiz.quiz;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class AnswerForm {

    @NotBlank(message = "Choisissez une réponse.")
    @Pattern(regexp = "^[ABCDabcd]$", message = "Réponse invalide (A, B, C ou D).")
    private String answer;

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }
}
