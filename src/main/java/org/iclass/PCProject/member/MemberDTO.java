package org.iclass.PCProject.member;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO{
    private Long id;
    private String username;
    private String displayName;
    private String email;
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String role;
    private String address;
    private String address2;
    private String gender;
    private Integer age;

}