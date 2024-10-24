package org.iclass.PCProject.member.usertoken;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="USER_TOKEN")
public class UserToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String token;

    @CreationTimestamp
    @Column(nullable = false, name="CREATED_AT")
    private LocalDateTime createdAt;


    public UserToken(String email, String token) {
        this.email = email;
        this.token = token;
    }
}