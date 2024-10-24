package org.iclass.PCProject.qna;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface
QNARepository extends JpaRepository<QNA, String> {

    List<QNA> findAll();
}
