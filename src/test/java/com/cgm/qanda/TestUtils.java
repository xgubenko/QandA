package com.cgm.qanda;

import com.cgm.qanda.dataobject.Answer;
import com.cgm.qanda.dataobject.Question;

import java.util.HashSet;
import java.util.Set;

public class TestUtils {

    public static Question createUserEntity(String questionText, String answerText) {
        Question question = new Question();
        question.setQuestion(questionText);
        Answer answer = new Answer();
        answer.setAnswer(answerText);
        Set<Answer> set = new HashSet<>();
        set.add(answer);
        return question;
    }

    public static String generateString(int length) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append("w");
        }
        return result.toString();
    }
}
