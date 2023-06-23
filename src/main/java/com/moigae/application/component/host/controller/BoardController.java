package com.moigae.application.component.host.controller;

import com.moigae.application.component.host.dto.AccountDto;
import com.moigae.application.component.host.dto.BankAccountDto;
import com.moigae.application.component.host.service.AccountService;
import com.moigae.application.component.host.service.BoardService;
import com.moigae.application.component.meeting_user.domain.MeetingUser;
import com.moigae.application.component.meeting_user.repositroy.MeetingUserRepository;
import com.moigae.application.component.user.dto.CustomUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

//게시물 목록 데이터를 조회
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/host-center")
public class BoardController {

    private final BoardService boardService;
    private final AccountService accountService;
    private final MeetingUserRepository meetingUserRepository;

    @GetMapping("/board")
    public String getBoard(Model model, @AuthenticationPrincipal CustomUser customUser) {
        log.info("getallboard = {} ", boardService.getAllBoards());
        List<MeetingUser> meetingUsers = meetingUserRepository.findByHostId(customUser.getId());
        //map 으로 짠거 list로 넘겨서 사진이랑제목만
        List<BankAccountDto> boardList = meetingUsers.stream()
                //나중에 바꾸기 (유저별로 들고오는걸로)
                .map(x -> BankAccountDto.builder()
                        .id((x.getMeeting().getId()))
                        .date(x.getMeeting().getMeetingStartDateTime())
                        .meetingStatus(x.getMeeting().getMeetingStatus())
                        .meetingTitle(x.getMeeting().getMeetingTitle())
                        .price(x.getMeeting().getMeetingPrice())
                        .participantRange(x.getMeeting().getParticipantRange())
                        .build())
                .collect(Collectors.toList());
        model.addAttribute("boardList", boardList);
        AccountDto accountDto = accountService.getAccount(customUser.getId());
        if (accountDto == null) accountDto = new AccountDto();
        model.addAttribute("accountDto", accountDto);
        model.addAttribute("customUser", customUser);
        return "host/hostBoard";
    }

    @PostMapping("/board")
    public String postBoard(@ModelAttribute("accountDto") AccountDto accountDto,
                            @AuthenticationPrincipal CustomUser customUser,
                            RedirectAttributes redirectAttributes) {
        if (customUser != null) {
            // 계좌번호가 이미 존재하는 경우
            if (accountService.iscreateAccountExists(accountDto.getAccountNumber())) {
                redirectAttributes.addAttribute("error", "existingAccount");
                return "redirect:/host-center/board";
            }
            // 계좌 저장 로직 처리
            accountService.createAccount(accountDto);
        }
        // 계좌 생성 후에는 원하는 동작을 정의하고 해당 페이지로 리다이렉트
        return "redirect:/host-center/board";
    }
}