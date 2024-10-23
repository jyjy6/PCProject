package org.iclass.PCProject.qna;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface
QnARepository extends JpaRepository<QnA, String> {

    List<QnA> findAll();
}
