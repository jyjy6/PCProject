package org.iclass.PCProject.member;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String email;

    @Column(unique = true, nullable = false)
    private String displayName;

    private String phone;

    private String address;

    // 아이디 생성 날짜
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    // 계정 정보 마지막 수정 날짜.
    @UpdateTimestamp
    @Column(updatable = true)
    private LocalDateTime updatedAt;

    private String role = "ROLE_USER";


}