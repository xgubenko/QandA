package com.cgm.qanda.dataaccessobject;

import com.cgm.qanda.QnAApplication;
import com.cgm.qanda.dataobject.Answer;
import com.cgm.qanda.dataobject.Question;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static com.cgm.qanda.TestUtils.createUserEntity;
import static com.cgm.qanda.TestUtils.generateString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = QnAApplication.class,
        initializers = ConfigFileApplicationContextInitializer.class)
public class QuestionRepositoryTest {

    @Autowired
    QuestionRepository repository;

    private final int CORRECT_LENGTH = 256;
    private final String QUESTION_DEFAULT_TEXT = "question1";
    private final String ANSWER_DEFAULT_TEXT = "Answer1";

    @Test
    public void TestRepositoryInjected() {
        assertNotNull(repository);
    }

    @Before
    public void beforeEach() {
        repository.deleteAll();
    }

    @Test
    public void testSaveSuccess() {
        assertEquals(0, repository.findAll().size());

        Question question = createUserEntity(QUESTION_DEFAULT_TEXT, ANSWER_DEFAULT_TEXT);
        repository.save(question);
        Optional<Question> q = repository.findByQuestion(QUESTION_DEFAULT_TEXT);
        Question qq = q.get();

        assertEquals(QUESTION_DEFAULT_TEXT, qq.getQuestion());
        assertEquals(1, repository.findAll().size());
    }

    @Test
    public void testSaveCorrectQuestionLengthSuccess() {
        assertEquals(0, repository.findAll().size());

        String questionText = generateString(CORRECT_LENGTH);
        Question question = createUserEntity(QUESTION_DEFAULT_TEXT, ANSWER_DEFAULT_TEXT);
        question.setQuestion(questionText);
        repository.save(question);

        assertEquals(1, repository.findAll().size());
    }

    @Test
    public void testSaveCorrectAnswerLengthSuccess() {
        assertEquals(0, repository.findAll().size());

        String answerText = generateString(CORRECT_LENGTH);
        Question question = createUserEntity(QUESTION_DEFAULT_TEXT, ANSWER_DEFAULT_TEXT);
        Answer answer = new Answer();
        answer.setAnswer(answerText);
        question.getAnswers().add(answer);
        repository.save(question);

        assertEquals(1, repository.findAll().size());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testSaveWrongQuestionLengthFailure() {
        assertEquals(0, repository.findAll().size());

        Question question = createUserEntity(QUESTION_DEFAULT_TEXT, ANSWER_DEFAULT_TEXT);
        question.setQuestion(generateString(CORRECT_LENGTH + 1));
        repository.save(question);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testSaveWrongAnswerLengthFailure() {
        assertEquals(0, repository.findAll().size());

        Question question = createUserEntity(QUESTION_DEFAULT_TEXT, ANSWER_DEFAULT_TEXT);
        Answer answer = new Answer();
        answer.setAnswer(generateString(CORRECT_LENGTH + 1));
        question.getAnswers().add(answer);
        repository.save(question);
    }
}
