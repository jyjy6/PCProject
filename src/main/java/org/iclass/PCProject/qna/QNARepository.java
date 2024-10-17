package org.iclass.PCProject.qna;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface QNARepository extends JpaRepository<QNA, Long> {
    Page<QNA> findAllByQuestioner(String username, Pageable pageable);

    //최근꺼 딱 3개만 가져오게
    List<QNA> findTop3ByQuestionerOrderByRegDateDesc(String username);

}
