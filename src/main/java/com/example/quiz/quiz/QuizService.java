package com.example.quiz.quiz;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuizService {

    // Banque de questions (en mémoire)
    private final List<Question> questions = List.of(
           new Question(1, Subject.ENGLISH,
"Comment dit-on « Bonjour » en anglais ?",
List.of("Hello", "Goodbye", "Thank you", "Sorry"), 'A'),

new Question(2, Subject.ENGLISH,
"Comment dit-on « Merci » en anglais ?",
List.of("Please", "Sorry", "Thank you", "Welcome"), 'C'),

new Question(3, Subject.ENGLISH,
"Quel est le pluriel de « child » ?",
List.of("Childs", "Children", "Childes", "Childrens"), 'B'),

new Question(4, Subject.ENGLISH,
"Comment dit-on « Livre » en anglais ?",
List.of("Book", "Pen", "Table", "Chair"), 'A'),

new Question(5, Subject.ENGLISH,
"Comment dit-on « Eau » en anglais ?",
List.of("Water", "Milk", "Juice", "Coffee"), 'A'),

new Question(6, Subject.ENGLISH,
"Comment dit-on « Maison » en anglais ?",
List.of("House", "Car", "Street", "Door"), 'A'),

new Question(7, Subject.ENGLISH,
"Quel est le verbe « être » en anglais ?",
List.of("To go", "To have", "To be", "To do"), 'C'),

new Question(8, Subject.ENGLISH,
"Comment dit-on « Chat » en anglais ?",
List.of("Dog", "Cat", "Bird", "Fish"), 'B'),

new Question(9, Subject.ENGLISH,
"Comment dit-on « Bonsoir » en anglais ?",
List.of("Good morning", "Good evening", "Good night", "Hello"), 'B'),

new Question(10, Subject.ENGLISH,
"Quel mot signifie « Ami » en anglais ?",
List.of("Friend", "Teacher", "Brother", "Father"), 'A'),

new Question(11, Subject.MATH,
"Combien font 5 + 3 ?",
List.of("6", "7", "8", "9"), 'C'),

new Question(12, Subject.MATH,
"Combien font 10 - 4 ?",
List.of("5", "6", "7", "8"), 'B'),

new Question(13, Subject.MATH,
"Combien font 6 × 3 ?",
List.of("12", "18", "21", "24"), 'B'),

new Question(14, Subject.MATH,
"Combien font 20 ÷ 4 ?",
List.of("4", "5", "6", "7"), 'B'),

new Question(15, Subject.MATH,
"Combien font 9 + 7 ?",
List.of("14", "15", "16", "17"), 'C'),

new Question(16, Subject.MATH,
"Combien font 12 × 2 ?",
List.of("22", "24", "26", "28"), 'B'),

new Question(17, Subject.MATH,
"Combien font 15 - 5 ?",
List.of("8", "9", "10", "11"), 'C'),

new Question(18, Subject.MATH,
"Combien font 7 × 7 ?",
List.of("42", "48", "49", "56"), 'C'),

new Question(19, Subject.MATH,
"Combien font 100 ÷ 10 ?",
List.of("5", "8", "10", "12"), 'C'),

new Question(20, Subject.MATH,
"Combien font 11 + 9 ?",
List.of("18", "19", "20", "21"), 'C'),

new Question(21, Subject.CHEMISTRY,
"Quel est le symbole chimique de l'eau ?",
List.of("H2O", "CO2", "O2", "NaCl"), 'A'),

new Question(22, Subject.CHEMISTRY,
"Quel gaz les plantes utilisent-elles pour la photosynthèse ?",
List.of("Oxygène", "Hydrogène", "Dioxyde de carbone", "Azote"), 'C'),

new Question(23, Subject.CHEMISTRY,
"Quel est le symbole chimique de l'oxygène ?",
List.of("O", "Ox", "Og", "Oy"), 'A'),

new Question(24, Subject.CHEMISTRY,
"Quel métal est liquide à température ambiante ?",
List.of("Fer", "Mercure", "Cuivre", "Or"), 'B'),

new Question(25, Subject.CHEMISTRY,
"Quel est le symbole chimique du sodium ?",
List.of("S", "So", "Na", "Sn"), 'C'),

new Question(26, Subject.CHEMISTRY,
"Quel gaz respirons-nous pour vivre ?",
List.of("Oxygène", "Azote", "Hydrogène", "Hélium"), 'A'),

new Question(27, Subject.CHEMISTRY,
"Quel est le pH de l'eau pure ?",
List.of("5", "6", "7", "8"), 'C'),

new Question(28, Subject.CHEMISTRY,
"Quel est le symbole chimique de l'or ?",
List.of("Ag", "Au", "Go", "Or"), 'B'),

new Question(29, Subject.CHEMISTRY,
"Quel élément chimique a pour symbole « Fe » ?",
List.of("Fer", "Fluor", "Francium", "Fermium"), 'A'),

new Question(30, Subject.CHEMISTRY,
"Quel gaz contribue fortement à l'effet de serre ?",
List.of("CO2", "O2", "H2", "N2"), 'A')
    );

    private final Map<Integer, Question> byId = questions.stream()
            .collect(Collectors.toUnmodifiableMap(Question::getId, q -> q));

    public List<Question> getAll() {
        return questions;
    }

    public Question getById(int id) {
        Question q = byId.get(id);
        if (q == null) throw new NoSuchElementException("Question not found: " + id);
        return q;
    }

    public List<Integer> buildQuiz(Subject subject, int count) {
        List<Question> pool;
        if (subject == Subject.MIXED) {
            pool = new ArrayList<>(questions);
        } else {
            pool = questions.stream().filter(q -> q.getSubject() == subject).collect(Collectors.toList());
        }

        Collections.shuffle(pool);
        return pool.stream().limit(Math.max(1, Math.min(count, pool.size())))
                .map(Question::getId)
                .collect(Collectors.toList());
    }

    public boolean isCorrect(Question q, char answer) {
        return Character.toUpperCase(answer) == q.getCorrect();
    }
}
