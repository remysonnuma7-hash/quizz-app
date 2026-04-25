package com.example.quiz.quiz;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuizService {

    // Banque de questions (en mémoire)
    private final List<Question> questions = List.of(
            new Question(1, Subject.ENGLISH,
                    "Choose the correct translation of: « Je vais à l'école. »",
                    List.of("I go to school", "I am school", "I have school", "I went school"), 'A'),

            new Question(2, Subject.ENGLISH,
                    "Which sentence is correct?",
                    List.of("She don't like music", "She doesn't like music", "She not likes music", "She doesn't likes music"), 'B'),

            new Question(3, Subject.ENGLISH,
                    "What is the past tense of the verb « to eat »?",
                    List.of("Eated", "Ate", "Eaten", "Eating"), 'B'),

            new Question(4, Subject.ENGLISH,
                    "Complete the sentence: I have lived here ___ 2022.",
                    List.of("for", "during", "since", "from"), 'C'),

            new Question(5, Subject.ENGLISH,
                    "Which word is opposite of « easy »?",
                    List.of("Simple", "Light", "Difficult", "Fast"), 'C'),

            new Question(6, Subject.ENGLISH,
                    "Choose the correct plural: one mouse, two ___",
                    List.of("mouses", "mouse", "mice", "mices"), 'C'),

            new Question(7, Subject.ENGLISH,
                    "What does « library » mean in French?",
                    List.of("Librairie", "Bibliothèque", "Bureau", "École"), 'B'),

            new Question(8, Subject.ENGLISH,
                    "Complete: They ___ playing football now.",
                    List.of("is", "am", "are", "be"), 'C'),

            new Question(9, Subject.ENGLISH,
                    "Which question is correct?",
                    List.of("Where you live?", "Where do you live?", "Where does you live?", "Where are live you?"), 'B'),

            new Question(10, Subject.ENGLISH,
                    "What is the synonym of « smart »?",
                    List.of("Intelligent", "Slow", "Tired", "Noisy"), 'A'),

            new Question(11, Subject.MATH,
                    "Si 3x + 5 = 20, quelle est la valeur de x ?",
                    List.of("3", "4", "5", "6"), 'C'),

            new Question(12, Subject.MATH,
                    "Quel est le carré de 12 ?",
                    List.of("124", "144", "132", "122"), 'B'),

            new Question(13, Subject.MATH,
                    "Combien font 15% de 200 ?",
                    List.of("15", "20", "30", "45"), 'C'),

            new Question(14, Subject.MATH,
                    "Un triangle a des angles de 50° et 60°. Quel est le troisième angle ?",
                    List.of("60°", "70°", "80°", "90°"), 'B'),

            new Question(15, Subject.MATH,
                    "Quelle fraction est équivalente à 0,75 ?",
                    List.of("1/2", "2/3", "3/4", "4/5"), 'C'),

            new Question(16, Subject.MATH,
                    "Combien font 8 × 7 ?",
                    List.of("54", "56", "64", "72"), 'B'),

            new Question(17, Subject.MATH,
                    "Quelle est la racine carrée de 81 ?",
                    List.of("7", "8", "9", "10"), 'C'),

            new Question(18, Subject.MATH,
                    "Si un article coûte 40$ et augmente de 25%, quel est son nouveau prix ?",
                    List.of("45$", "48$", "50$", "60$"), 'C'),

            new Question(19, Subject.MATH,
                    "Quel est le périmètre d'un carré de côté 6 cm ?",
                    List.of("12 cm", "18 cm", "24 cm", "36 cm"), 'C'),

            new Question(20, Subject.MATH,
                    "Combien font 2³ + 4 ?",
                    List.of("6", "8", "10", "12"), 'D'),

            new Question(21, Subject.CHEMISTRY,
                    "Quel est le symbole chimique du carbone ?",
                    List.of("Ca", "C", "Co", "Cr"), 'B'),

            new Question(22, Subject.CHEMISTRY,
                    "Quel est le pH d'une solution neutre ?",
                    List.of("0", "3", "7", "14"), 'C'),

            new Question(23, Subject.CHEMISTRY,
                    "Quelle particule possède une charge négative ?",
                    List.of("Proton", "Neutron", "Électron", "Noyau"), 'C'),

            new Question(24, Subject.CHEMISTRY,
                    "Quel gaz est indispensable à la respiration humaine ?",
                    List.of("Oxygène", "Dioxyde de carbone", "Hélium", "Méthane"), 'A'),

            new Question(25, Subject.CHEMISTRY,
                    "Quelle formule correspond au dioxyde de carbone ?",
                    List.of("CO", "CO2", "C2O", "O2C2"), 'B'),

            new Question(26, Subject.CHEMISTRY,
                    "Quel type de liaison implique le partage d'électrons ?",
                    List.of("Ionique", "Covalente", "Métallique", "Magnétique"), 'B'),

            new Question(27, Subject.CHEMISTRY,
                    "Quel élément a pour symbole Na ?",
                    List.of("Azote", "Nickel", "Sodium", "Néon"), 'C'),

            new Question(28, Subject.CHEMISTRY,
                    "Dans le tableau périodique, H représente quel élément ?",
                    List.of("Hydrogène", "Hélium", "Hafnium", "Holmium"), 'A'),

            new Question(29, Subject.CHEMISTRY,
                    "Une solution avec un pH de 2 est généralement...",
                    List.of("Basique", "Acide", "Neutre", "Salée"), 'B'),

            new Question(30, Subject.CHEMISTRY,
                    "Quel instrument mesure la masse d'un objet en laboratoire ?",
                    List.of("Thermomètre", "Balance", "Bécher", "Pipette"), 'B')
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
