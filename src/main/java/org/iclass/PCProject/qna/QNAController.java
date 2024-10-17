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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@RequestMapping("/qna")
@RequiredArgsConstructor
@Controller
public class QNAController {
    private final QNARepository qnaRepository;
    private final QNAService qnaService;

    // QNA의 기본 경로는 /mypage/qna
    // MemberController의 @GetMapping("/mypage/{id}") 에 의해 유동적으로 바뀌고 있고 해당 페이지엔 3개의 qna만을 표시.
    // 여기선 모든 qna목록을 페이지네이션과 함께 볼 수 있음.


    @GetMapping({"/all", "/all/{condition}"})
    public String allQNA(@PathVariable(required = false) String condition,
                         @RequestParam(value = "page", defaultValue = "1") int page,
                         @RequestParam(defaultValue = "3") int size,
                         Model model,
                         Authentication auth) {
        if (condition == null) {
            condition = "";
        }
        int adjustedPage = page - 1;
        String username = ((CustomUserDetails) auth.getPrincipal()).getUsername();

        //페이지네이션
        Page<QNA> result;
        // condition 값에 따라 result 결정
        switch (condition.toLowerCase()) {
            case "unanswered":
                // Answer가 null인 항목만 가져옴
                result = qnaService.getPageByQuestionerAndAnswerIsNull(username, adjustedPage, size);
                break;
            case "answered":
                // Answer가 null이 아닌 항목만 가져옴
                result = qnaService.getPageByQuestionerAndAnswerIsNotNull(username, adjustedPage, size);
                break;
            default:
                // 기본적으로 모든 항목 가져옴
                result = qnaService.getPageByQuestioner(username, adjustedPage, size);
                break;
        }

        int totalPages = result.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("qnaList", result.getContent());
        model.addAttribute("currentPage", page); // 사용자가 입력한 페이지 번호 유지
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", result.getTotalElements());
        model.addAttribute("size", size);
        return "jung/qna/qna-all";
    }


    @GetMapping("/add")
    public String add() {
        return "jung/qna/qna-form";
    }

    @GetMapping("/{detail}")
    public String qnaDetails(@PathVariable Long detail,
                             Model model) {
        QNA qnaDetail = qnaRepository.findBySeq(detail).orElseThrow(()-> new NoSuchFieldError("그런 상담번호는 존재하지 않습니다"));
        model.addAttribute("detail", qnaDetail);
        return "jung/qna/qna-detail";
    }

}
