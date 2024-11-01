package org.iclass.PCProject.admin.repository;

import org.apache.ibatis.annotations.Param;
import org.iclass.PCProject.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminMemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m FROM Member m WHERE YEAR(m.createdAt) = :year ORDER BY m.createdAt DESC")
    Page<Member> findByCreatedAtYear(@Param("year") int year, Pageable pageable);

    List<Member> findByUsernameContainingIgnoreCase(String username);

}
