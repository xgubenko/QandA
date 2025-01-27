package com.cgm.qanda.dataobject;

import javax.persistence.*;

@Entity
public class Answer {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 256)
    private String answer;

    @ManyToOne
    private Question question;

    public Answer() {
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
