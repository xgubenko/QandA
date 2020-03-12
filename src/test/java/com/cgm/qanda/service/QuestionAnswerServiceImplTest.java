package com.cgm.qanda.service;

import com.cgm.qanda.QnAApplication;
import com.cgm.qanda.dataaccessobject.QuestionRepository;
import com.cgm.qanda.dataobject.Answer;
import com.cgm.qanda.dataobject.Question;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = QnAApplication.class,
        initializers = ConfigFileApplicationContextInitializer.class)
public class QuestionAnswerServiceImplTest {

    @Autowired
    QuestionAnswerService service;

    @Mock
    QuestionRepository repo;

    @Before
    public void setup() {
        Question question = createQuestionEntity();
        repo.save(question);
    }

    private Question createQuestionEntity() {
        Question question = new Question();
        question.setQuestion("question1");
        Answer answer = new Answer();
        answer.setAnswer("answer1");
        Set<Answer> set = new HashSet<>();
        set.add(answer);
        return question;
    }

    @Test
    public void testGetAnswers() {
        Question q = createQuestionEntity();
        Mockito.when(repo.findByQuestion("question1")).thenReturn(Optional.ofNullable(q));
        List<String> answers = service.getAnswers("question1");
        assertNotNull(answers);
        assertEquals(1, answers.size());
    }

    @Test
    public void addQuestionTest() {
        Question q = createQuestionEntity();
        q.setQuestion("question");
        Mockito.when(repo.save(q)).thenReturn(q);
        Mockito.when(repo.findByQuestion("question")).thenReturn(Optional.ofNullable(q));
        service.addQuestion("question", "answer1");
        List<String> answers = service.getAnswers("question");
        assertNotNull(answers);
        assertEquals("answer1", answers.get(0));

    }
}
