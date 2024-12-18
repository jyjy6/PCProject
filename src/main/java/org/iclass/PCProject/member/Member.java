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

    private String password;

    @Column(nullable = false)
    private String email;

    @Column(unique = true, nullable = false, name="DISPLAY_NAME")
    private String displayName;

    private String phone;

    private String address;
    //상세주소
    private String address2;

    private String gender;
    private Integer age;
    // 아이디 생성 날짜
    @CreationTimestamp
    @Column(updatable = false, name="CREATED_AT")
    private LocalDateTime createdAt;

    // 계정 정보 마지막 수정 날짜.
    @UpdateTimestamp
    @Column(updatable = true, name="UPDATED_AT")
    private LocalDateTime updatedAt;

    private String role = "ROLE_USER";

    private String name;

}
