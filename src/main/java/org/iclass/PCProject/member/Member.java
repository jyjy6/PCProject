package org.iclass.PCProject.member;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.List;

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

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    private String displayName;

    private String phone;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    //    계정 정보 마지막 수정 날짜.
    @UpdateTimestamp
    @Column(updatable = true)
    private LocalDateTime updatedAt;

    // 기본값을 유지하고 싶을 때 @Builder.Default 사용
    private List<GrantedAuthority> authorities;


    public Member(String username, String displayName, List<GrantedAuthority> authorities) {
        this.username = username;
        this.displayName = displayName;
        this.authorities = authorities;
    }
}
