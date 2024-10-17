package org.iclass.PCProject.qna;

import lombok.RequiredArgsConstructor;
import org.iclass.PCProject.security.CustomUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/qna")
@RequiredArgsConstructor
@Controller
public class QNAController {
    private final QNARepository qnaRepository;

    // QNA의 기본 경로는 /mypage/qna
    // MemberController의 @GetMapping("/mypage/{id}") 에 의해 유동적으로 바뀌고 있고 해당 페이지엔 3개의 qna만을 표시.
    // 여기선 모든 qna목록을 페이지네이션과 함께 볼 수 있음.


    @GetMapping("/all")
    public String allQNA(@RequestParam(value = "page", defaultValue = "0") Integer page,
                         Model model,
                         Authentication auth){
        String username = ((CustomUserDetails) auth.getPrincipal()).getUsername();
        Pageable pageable = PageRequest.of(page, 5); // 페이지당 5개
        Page<QNA> qnaPage = qnaRepository.findAllByQuestioner(username, pageable);
        model.addAttribute("qnaList", qnaPage.getContent());
        model.addAttribute("totalPages", qnaPage.getTotalPages());
        model.addAttribute("currentPage", page);
        return "jung/qna/qna-all";
    }

    @GetMapping("/add")
    public String add() {
        return "jung/qna/qna-form";
    }

    @GetMapping("/{detail}")
    public String qnaDetails(@PathVariable String detail) {

        return "jung/qna/qna-detail";
    }

}
