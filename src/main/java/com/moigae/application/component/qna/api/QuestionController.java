package com.moigae.application.component.qna.api;

import com.moigae.application.component.answer.domain.Answer;
import com.moigae.application.component.article.api.request.ArticleForm;
import com.moigae.application.component.article.api.service.FileService;
import com.moigae.application.component.qna.api.service.QuestionService;
import com.moigae.application.component.qna.domain.Sym;
import com.moigae.application.component.qna.dto.AnswerDto;
import com.moigae.application.component.qna.dto.QuestionWithSymCountDto;
import com.moigae.application.component.qna.repository.AnswerRepository;
import com.moigae.application.component.qna.repository.QuestionRepository;
import com.moigae.application.component.qna.repository.SymRepository;
import com.moigae.application.component.question.domain.Question;
import com.moigae.application.component.user.domain.User;
import com.moigae.application.component.user.dto.CustomUser;
import com.moigae.application.component.user.repository.UserRepository;
import com.moigae.application.core.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/questions")
@RequiredArgsConstructor
@Slf4j
public class QuestionController {
    private final QuestionRepository questionRepository;
    private final FileService fileService;
    private final QuestionService questionService;
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;
    private final SymRepository symRepository;

    @GetMapping("/createQuestion")
    @PreAuthorize("isAuthenticated() or (authentication.principal != null and authentication.principal.admin == true)")
    public String createQuestion(Model model,
                                 @AuthenticationPrincipal CustomUser customUser) {

        model.addAttribute("articleForm", new ArticleForm());
        model.addAttribute("customUser", customUser);
        return "questions/createQuestion";
    }

    @PostMapping("/createQuestion")
    @PreAuthorize("isAuthenticated() or (authentication.principal != null and authentication.principal.admin == true)")
    public String createQuestion(
            @ModelAttribute ArticleForm articleForm,
            @AuthenticationPrincipal CustomUser customUser
    ) {
        User user = userRepository.findByEmail(customUser.getUsername());
        Question question = new Question(user, articleForm.getArticleTitle(), articleForm.getContent(), 0);
        questionRepository.save(question);
        return "redirect:/questions/questionList";
    }


    public String getQuestionList(Model model,
                                  @AuthenticationPrincipal CustomUser customUser,
                                  @PageableDefault(size = 10) Pageable pageable,
                                  String viewName) {
        model.addAttribute("customUser", customUser);

        return viewName;
    }

    @GetMapping("/questionList")
    public String questionList(Model model,
                               @AuthenticationPrincipal CustomUser customUser,
                               @PageableDefault(size = 6) Pageable pageable) {

        return getQuestionList(model, customUser, pageable, "questions/questionList");
    }

    @GetMapping("/sort")
    public ResponseEntity<Page<QuestionWithSymCountDto>> sortQuestions(@RequestParam String sort, @RequestParam(required = false) String searchTerm, Pageable pageable) {
        Page<QuestionWithSymCountDto> questions = questionService.getQuestionsWithSymCount(pageable, sort, searchTerm);

        return ResponseEntity.ok(questions);
    }

    @GetMapping("/questionDetail/{id}")
    public String getQuestionDetail(
            @AuthenticationPrincipal CustomUser customUser,
            @PathVariable("id") String questionId,
            Model model) {
        Question question2 = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + questionId));
        question2.setViewCount(question2.getViewCount() + 1);
        questionRepository.save(question2);

        QuestionWithSymCountDto question = questionService.getQuestionWithSymCount(questionId);
        User user = userRepository.findById(question.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + question.getUserId()));

        model.addAttribute("customUser", customUser);
        model.addAttribute("question", question);
        model.addAttribute("user", user);
        return "questions/questionDetail";
    }

    @DeleteMapping("/delete/{questionId}")
    @PreAuthorize("isAuthenticated() or (authentication.principal != null and authentication.principal.admin == true)")
    public ResponseEntity<?> deleteQuestion(
            @AuthenticationPrincipal CustomUser customUser,
            @PathVariable String questionId) {
        questionRepository.deleteById(questionId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/update/{questionId}")
    @PreAuthorize("isAuthenticated() or (authentication.principal != null and authentication.principal.admin == true)")
    public String updateQuestion(
            Model model,
            @AuthenticationPrincipal CustomUser customUser,
            @PathVariable String questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + questionId));
        model.addAttribute("articleForm", new ArticleForm());
        model.addAttribute("customUser", customUser);
        model.addAttribute("question", question);

        return "questions/updateQuestion";
    }

    @PostMapping("/updateQuestion/{questionId}")
    @PreAuthorize("isAuthenticated() or (authentication.principal != null and authentication.principal.admin == true)")
    public String updateQuestion(
            @AuthenticationPrincipal CustomUser customUser,
            @PathVariable("questionId") String questionId,
            @ModelAttribute ArticleForm articleForm
    ) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        if (optionalQuestion.isPresent()) {
            Question question = optionalQuestion.get();
            question.setQuestionTitle(articleForm.getArticleTitle());
            question.setQuestionContent(articleForm.getContent());

            System.out.println(question);
            questionRepository.save(question);
        }
        return "redirect:/questions/questionList";
    }

    @PostMapping("/symUp/{questionId}/{userId}")
    @PreAuthorize("isAuthenticated() or (authentication.principal != null and authentication.principal.admin == true)")
    @ResponseBody
    @Transactional
    public Map<String, Long> symUp(
            @PathVariable("questionId") String questionId,
            @PathVariable("userId") String userId,
            @AuthenticationPrincipal CustomUser customUser
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + questionId));

        Sym sym = symRepository.findByUserIdAndQuestionId(userId, questionId);
        boolean toggle = false;
        if (sym == null) {
            sym = new Sym(question, user, true);
        } else {
            toggle = !sym.isSym();
            sym.setSym(toggle);
        }
        symRepository.save(sym);

        List<Sym> symList = symRepository.findByQuestionIdAndSymTrue(questionId);
        long symLen = symList.size();

        Map<String, Long> response = new HashMap<>();
        response.put("newSymValue", symLen);
        return response;
    }

    @PostMapping("/addAnswer/{questionId}/{userId}")
    @PreAuthorize("isAuthenticated() or (authentication.principal != null and authentication.principal.admin == true)")
    @ResponseBody
    public Map<String, String> addAnswer(
            @PathVariable("questionId") String questionId,
            @PathVariable("userId") String userId,
            @RequestParam("answerContent") String answerContent,
            @AuthenticationPrincipal CustomUser customUser
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + questionId));

        Answer answer = new Answer(question, user, answerContent, false);
        answerRepository.save(answer);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");

        return response;
    }

    @GetMapping("/getAnswers/{questionId}")
    @ResponseBody
    public List<AnswerDto> getAnswers(@PathVariable("questionId") String questionId) {
        // questionId에 해당하는 댓글을 가져와 AnswerDto에 담아서 반환합니다.
        List<Answer> answers = answerRepository.findAnswersByQuestionIdSortedByCreatedAtDesc(questionId);
        List<AnswerDto> answerDtos = answers.stream()
                .map(answer -> new AnswerDto(
                        answer.getId(),
                        answer.getQuestion().getId(),
                        answer.getUser().getEmail(),
                        answer.getAnswerContent(),
                        answer.getCreateTime()
                ))
                .collect(Collectors.toList());

        return answerDtos;
    }

    @DeleteMapping("/deleteAnswer/{answerId}")
    @PreAuthorize("isAuthenticated() or (authentication.principal != null and authentication.principal.admin == true)")
    @ResponseBody
    public Map<String, String> deleteAnswer(
            @AuthenticationPrincipal CustomUser customUser,
            @PathVariable String answerId) {
        answerRepository.deleteById(answerId);
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");

        return response;
    }

    @PostMapping("/editAnswer/{id}")
    @PreAuthorize("isAuthenticated() or (authentication.principal != null and authentication.principal.admin == true)")
    @ResponseBody
    @Transactional
    public Map<String, String> editAnswer(
            @AuthenticationPrincipal CustomUser customUser,
            @PathVariable String id,
            @RequestParam String answerContent
    ) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Answer not found with id " + id));
        answer.setAnswerContent(answerContent);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");

        return response;
    }

    public String getMyQuestionList(Model model,
                                    @AuthenticationPrincipal CustomUser customUser,
                                    @PageableDefault(size = 10) Pageable pageable,
                                    String viewName) {
        model.addAttribute("customUser", customUser);
        return viewName;
    }

    @GetMapping("/myQuestionList")
    public String myQuestionList(Model model,
                                 @AuthenticationPrincipal CustomUser customUser,
                                 @PageableDefault(size = 6) Pageable pageable) {

        return getMyQuestionList(model, customUser, pageable, "questions/myQuestionList");
    }

    @GetMapping("/sortMyQ")
    public ResponseEntity<Page<QuestionWithSymCountDto>> sortMyQuestions(
            @RequestParam String sort,
            @RequestParam(required = false) String searchTerm,
            Pageable pageable,
            @AuthenticationPrincipal CustomUser customUser
    ) {
        Page<QuestionWithSymCountDto> questions = questionService.getQuestionsWithSymCount2(pageable, sort, searchTerm, customUser.getId());

        return ResponseEntity.ok(questions);
    }

    public String getMyAnswerList(Model model,
                                  @AuthenticationPrincipal CustomUser customUser,
                                  @PageableDefault(size = 10) Pageable pageable,
                                  String viewName) {
        model.addAttribute("customUser", customUser);
        return viewName;
    }

    @GetMapping("/myAnswerList")
    public String myAnswerList(Model model,
                               @AuthenticationPrincipal CustomUser customUser,
                               @PageableDefault(size = 6) Pageable pageable) {

        return getMyAnswerList(model, customUser, pageable, "questions/myAnswerList");
    }

    @GetMapping("/sortMyA")
    public ResponseEntity<Page<QuestionWithSymCountDto>> sortMyAnswers(
            @RequestParam String sort,
            @RequestParam(required = false) String searchTerm,
            Pageable pageable,
            @AuthenticationPrincipal CustomUser customUser
    ) {
        Page<QuestionWithSymCountDto> questions = questionService.getQuestionsWithSymCount3(pageable, sort, searchTerm, customUser.getId());

        return ResponseEntity.ok(questions);
    }
}
