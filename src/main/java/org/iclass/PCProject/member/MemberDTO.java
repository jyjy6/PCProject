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
    private String name;

    //    entity to dto
    public static MemberDTO toDTO(Member entity) {
        return MemberDTO.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .displayName(entity.getDisplayName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .role(entity.getRole())
                .address(entity.getAddress())
                .address2(entity.getAddress2())
                .gender(entity.getGender())
                .age(entity.getAge())
                .build();
    }

    //    dto to entity
    public Member toEntity() {
        return Member.builder()
                .id(this.id)
                .username(this.username)
                .displayName(this.displayName)
                .email(this.email)
                .phone(this.phone)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .role(this.role)
                .address(this.address)
                .address2(this.address2)
                .gender(this.gender)
                .age(this.age)
                .build();
    }

}