package com.example.quiz.quiz;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class QuizController {

    private static final String SESSION_KEY = "QUIZ_SESSION";

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/start")
    public String start(@RequestParam("subject") Subject subject,
                        @RequestParam(name = "count", defaultValue = "5") int count,
                        HttpSession session) {

        var ids = quizService.buildQuiz(subject, count);
        session.setAttribute(SESSION_KEY, new QuizSession(subject, ids));
        return "redirect:/quiz";
    }

    @GetMapping("/quiz")
    public String quiz(Model model, HttpSession session, @RequestParam(value = "reset", required = false) String reset) {
        if (reset != null) {
            session.removeAttribute(SESSION_KEY);
            return "redirect:/";
        }

        QuizSession qs = (QuizSession) session.getAttribute(SESSION_KEY);
        if (qs == null) return "redirect:/";

        if (qs.finished()) return "redirect:/result";

        int qid = qs.getQuestionIds().get(qs.getIndex());
        Question q = quizService.getById(qid);

        model.addAttribute("quizSession", qs);
        model.addAttribute("question", q);
        model.addAttribute("answerForm", new AnswerForm());
        return "quiz";
    }

    @PostMapping("/quiz")
    public String submit(@Valid @ModelAttribute("answerForm") AnswerForm form,
                         BindingResult bindingResult,
                         Model model,
                         HttpSession session) {

        QuizSession qs = (QuizSession) session.getAttribute(SESSION_KEY);
        if (qs == null) return "redirect:/";

        if (qs.finished()) return "redirect:/result";

        int qid = qs.getQuestionIds().get(qs.getIndex());
        Question q = quizService.getById(qid);

        if (bindingResult.hasErrors()) {
            model.addAttribute("quizSession", qs);
            model.addAttribute("question", q);
            return "quiz";
        }

        char answer = form.getAnswer().trim().toUpperCase().charAt(0);
        boolean correct = quizService.isCorrect(q, answer);
        if (correct) qs.setScore(qs.getScore() + 1);
        qs.setIndex(qs.getIndex() + 1);
        session.setAttribute(SESSION_KEY, qs);

        return "redirect:/quiz";
    }

    @GetMapping("/result")
    public String result(Model model, HttpSession session) {
        QuizSession qs = (QuizSession) session.getAttribute(SESSION_KEY);
        if (qs == null) return "redirect:/";

        model.addAttribute("quizSession", qs);

        int total = qs.total();
        int score = qs.getScore();
        String message;
        if (total == 0) message = "Aucune question.";
        else {
            double pct = (score * 100.0) / total;
            if (pct >= 80) message = "Excellent 👏";
            else if (pct >= 60) message = "Très bien ✅";
            else if (pct >= 40) message = "Pas mal 🙂";
            else message = "À améliorer 💪";
        }
        model.addAttribute("message", message);

        return "result";
    }
}
