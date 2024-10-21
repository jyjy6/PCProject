package org.iclass.PCProject.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByUsername(String username);
    boolean existsByDisplayName(String displayName);
    Optional<Member> findByUsername(String username);

    Member findByEmail(String email);
}
