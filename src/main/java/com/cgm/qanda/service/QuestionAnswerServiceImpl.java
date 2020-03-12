package com.cgm.qanda.service;

import com.cgm.qanda.dataaccessobject.AnswerRepository;
import com.cgm.qanda.dataaccessobject.QuestionRepository;
import com.cgm.qanda.dataobject.Answer;
import com.cgm.qanda.dataobject.Question;
import com.cgm.qanda.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuestionAnswerServiceImpl implements QuestionAnswerService {
    private static final Logger log = LoggerFactory.getLogger(QuestionAnswerServiceImpl.class);

    @Autowired
    QuestionRepository qRep;
    @Autowired
    AnswerRepository aRepo;

    @Override
    public void addQuestion(String question, String answers) {
        if (answers != null && !answers.isEmpty()) {
            Question ques = new Question();
            ques.setQuestion(question);
            String[] answArr = answers.split("\"");
            Set<Answer> s1 = new HashSet<>();
            for (String answer : answArr) {
                answer = answer.trim();
                if (answer != null && !answer.isEmpty()) {
                    boolean validate = ValidationUtil.validateLength(answer);
                    if (validate) {
                        Answer a1 = new Answer();
                        a1.setAnswer(answer);
                        s1.add(a1);
                    } else {
                        System.out.println("Answer length is more than 256 characters for answer " + answer);
                    }
                }
            }

            ques.setAnswers(s1);
            try {
                Optional<Question> queDb = qRep.findByQuestion(question);
                if (!queDb.isPresent()) {
                    save(ques);
                }
            } catch (Exception e) {
                System.out.println("error: " + e.getMessage());
            }
        } else {
            System.out.println("No answer provided for questions");
        }
    }

    @Override
    public List<String> getAnswers(String question) {
        Optional<Question> questionInDb = qRep.findByQuestion(question);
        List<String> returnList = new ArrayList<>();
        if (questionInDb.isPresent()) {
            Question q = questionInDb.get();
            List<Answer> answers = q.getAnswers().stream().collect(Collectors.toList());
            for (Answer answer : answers) {
                returnList.add(answer.getAnswer());
            }
        }
        if (returnList.isEmpty()) {
            returnList.add("\"" + "the answer to life, universe and everything is 42" + "\"" + " according to" + "\""
                    + "The hitchhikers guide to the Galaxy" + "\"");
        }
        return returnList;
    }

    @Override
    @Transactional
    public Question save(Question question) throws Exception {
        Question savedQuestion = qRep.save(question);
        Set<Answer> answers = savedQuestion.getAnswers();
        for (Answer answer : answers) {
            answer.setQuestion(savedQuestion);
            aRepo.save(answer);
        }
        return savedQuestion;
    }

}
