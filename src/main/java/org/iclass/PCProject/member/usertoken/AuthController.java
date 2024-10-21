package org.iclass.PCProject.member.usertoken;
import lombok.RequiredArgsConstructor;
import org.iclass.PCProject.member.Member;
import org.iclass.PCProject.member.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/auth")
@Controller
public class AuthController {

    private final EmailService emailService;
    private final UserTokenRepository userTokenRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "jung/member/forgot-password"; // 비밀번호 재설정 요청 페이지
    }

    @PostMapping("/forgot-password")
    public String sendResetEmail(@RequestParam String email) {
        emailService.sendResetPasswordEmail(email);
        return "redirect:/login"; // 로그인 페이지로 리다이렉트
    }

    @GetMapping("/reset-password")
    public String showResetPasswordPage(@RequestParam String token, Model model) {
        model.addAttribute("token", token); // 토큰을 모델에 추가
        return "jung/member/reset-password"; // 비밀번호 재설정 폼을 보여주는 뷰
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String token, @RequestParam String newPassword, Model model) {
        // 토큰 검증
        UserToken userToken = userTokenRepository.findByToken(token);
        if (userToken == null) {
            model.addAttribute("error", "유효하지 않은 토큰입니다."); // 에러 메시지 추가
            return "jung/member/reset-password";
        }

        // 이메일로 사용자 찾기
        Member member = memberRepository.findByEmail(userToken.getEmail());
        if (member == null) {
            model.addAttribute("error", "해당 이메일로 사용자를 찾을 수 없습니다."); // 에러 메시지 추가
            return "jung/member/reset-password";
        }

        // 비밀번호 업데이트
        String encodedPassword = passwordEncoder.encode(newPassword);
        member.setPassword(encodedPassword);
        memberRepository.save(member);

        return "redirect:/login"; // 비밀번호 변경 후 로그인 페이지로 리다이렉트
    }


}