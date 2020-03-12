package com.cgm.qanda.dataaccessobject;

import com.cgm.qanda.dataobject.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Query(nativeQuery = true, value = "select * from answer where question_id = ?")
    List<Answer> findByQustionId(Long questionId);
}
