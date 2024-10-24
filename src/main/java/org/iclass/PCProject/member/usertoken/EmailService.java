package org.iclass.PCProject.member.usertoken;

import lombok.RequiredArgsConstructor;
import org.iclass.PCProject.member.Member;
import org.iclass.PCProject.member.MemberRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;


@RequiredArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final MemberRepository memberRepository;
    private final UserTokenRepository userTokenRepository;

    // 사용자가 이메일을 입력하여 비밀번호 재설정 요청을 보내는 메서드
    public void sendResetPasswordEmail(String email) {
        Member member = memberRepository.findByEmail(email);
        if (member != null) {
            String token = UUID.randomUUID().toString(); // 랜덤 토큰 생성
            userTokenRepository.save(new UserToken(member.getEmail(), token)); // 이메일과 토큰 저장

            // 이메일 발송 로직
            String resetUrl = "http://localhost:8080/auth/reset-password?token=" + token;
            // 이메일 메시지 설정
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(member.getEmail());
            message.setSubject("비밀번호 재설정 요청");
            message.setText("다음 링크를 클릭하여 비밀번호를 재설정하세요: " + resetUrl);
            // 이메일 전송
            mailSender.send(message);
        }
    }

}
