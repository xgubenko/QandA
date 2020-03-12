package com.cgm.qanda.service;

import com.cgm.qanda.dataobject.Question;

import java.util.List;

public interface QuestionAnswerService {
	void addQuestion(String question, String answers);
	List<String> getAnswers(String question);
	Question save(Question t) throws Exception;
}
