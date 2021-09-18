package com.cgm.qanda.dataobject;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Question {
    @Id
    @GeneratedValue
    private Long id;

    @Version
    private int version;

    @Column(length = 256)
    private String question;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question", fetch = FetchType.EAGER)
    private Set<Answer> answers = new HashSet<>();

    public Question(QuestionBuilder builder) {
        id = builder.id;
        question = builder.question;
    }

    public Question() {

    }

    public Question(Long questionId) {
        this.id = questionId;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public static class QuestionBuilder {
        private Long id;

        @Column(length = 256)
        private String question;

        public Long getId() {
            return id;
        }

        public QuestionBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public QuestionBuilder setQuestion(String question) {
            this.question = question;
            return this;
        }

        public Question build() {
            return new Question(this);
        }
    }
}
