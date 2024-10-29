package org.iclass.PCProject.admin.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.iclass.PCProject.admin.repository.AdminMemberRepository;
import org.iclass.PCProject.member.Member;
import org.iclass.PCProject.member.MemberDTO;
import org.iclass.PCProject.member.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminCustomerService {

    private final MemberRepository memberRepository;
    private final AdminMemberRepository adminMemberRepository;

    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));
    }

    public List<MemberDTO> getAllMemberList() {
        List<Member> list = memberRepository.findAll();
        return list.stream().map(MemberDTO::toDTO).collect(Collectors.toList());
    }

    public void createCustomer(MemberDTO memberDTO) {
        memberRepository.save(memberDTO.toEntity());
    }

    public void updateCustomer(Member member) {
        memberRepository.save(member);
    }

    public void deleteCustomer(Long id) {
        memberRepository.deleteById(id);
    }

    public Page<MemberDTO> findByCreatedAtYear(String year, Pageable pageable) {
        if (year == null || year.isEmpty()) {
            return memberRepository.findAll(pageable).map(this::convertToDTO);
        } else {
            return adminMemberRepository.findByCreatedAtYear(year, pageable).map(this::convertToDTO);
        }
    }

    private MemberDTO convertToDTO(Member member) {
            MemberDTO dto = new MemberDTO();
            dto.setId(member.getId());
            dto.setUsername(member.getUsername());
            dto.setDisplayName(member.getDisplayName());
            dto.setEmail(member.getEmail());
            dto.setPhone(member.getPhone());
            dto.setAddress(member.getAddress());
            dto.setAddress2(member.getAddress2());
            dto.setAge(member.getAge());
            dto.setGender(member.getGender());
            dto.setCreatedAt(member.getCreatedAt());
            dto.setUpdatedAt(member.getUpdatedAt());
            dto.setRole(member.getRole());
            return dto;
        }
}
