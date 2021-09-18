package com.cgm.qanda.service;

import com.cgm.qanda.QnAApplication;
import com.cgm.qanda.dataaccessobject.QuestionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static com.cgm.qanda.TestUtils.generateString;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = QnAApplication.class,
        initializers = ConfigFileApplicationContextInitializer.class)
public class QuestionAnswerServiceImplTest {

    @Autowired
    QuestionAnswerService questionAnswerService;

    @Autowired
    QuestionRepository questionRepository;

    private final String QUESTION_DEFAULT_TEXT = "Question1";
    private final String ANSWER_ONE_DEFAULT_TEXT = "Answer1";
    private final String ANSWER_MULTIPLE_DEFAULT_TEXT = "\"answer1\"answer2\"";

    @Before
    public void before() {
        questionRepository.deleteAll();
    }

    @Test
    public void testGetAnswersSuccess() {
        assertEquals(0, questionRepository.findAll().size());

        questionAnswerService.addQuestion(QUESTION_DEFAULT_TEXT, ANSWER_ONE_DEFAULT_TEXT);
        List<String> answersList = questionAnswerService.getAnswers(QUESTION_DEFAULT_TEXT);

        assertEquals(1, answersList.size());
        assertEquals(ANSWER_ONE_DEFAULT_TEXT, answersList.get(0));
    }

    @Test
    public void addOneAnswerQuestionTestSuccess() {
        assertEquals(0, questionRepository.findAll().size());

        questionAnswerService.addQuestion(QUESTION_DEFAULT_TEXT, ANSWER_ONE_DEFAULT_TEXT);

        assertEquals(1, questionRepository.findAll().size());
    }

    @Test
    public void addMultipleAnswersQuestionTestSuccess() {
        assertEquals(0, questionRepository.findAll().size());

        questionAnswerService.addQuestion(QUESTION_DEFAULT_TEXT, ANSWER_MULTIPLE_DEFAULT_TEXT);

        assertEquals(1, questionRepository.findAll().size());
        assertEquals(2, questionRepository.findAll().get(0).getAnswers().size());
    }

    @Test
    public void addLongAnswerTestFailure() {
        assertEquals(0, questionRepository.findAll().size());

        questionAnswerService.addQuestion(QUESTION_DEFAULT_TEXT, generateString(257));

        assertEquals(1, questionRepository.findAll().size());
        assertEquals(0, questionRepository.findAll().get(0).getAnswers().size());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void addLongQuestionTestFailure() {
        assertEquals(0, questionRepository.findAll().size());

        questionAnswerService.addQuestion(generateString(257), ANSWER_ONE_DEFAULT_TEXT);
    }
}
