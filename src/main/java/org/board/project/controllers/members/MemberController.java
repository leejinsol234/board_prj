package org.board.project.controllers.members;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.board.project.commons.CommonProcess;
import org.board.project.commons.Utils;
import org.board.project.models.member.MemberSaveService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController implements CommonProcess {

    private final Utils utils;
    private final MemberSaveService saveService;
    //CommonProcess: 회원가입 실패 시 공통 처리

    @GetMapping("/join") //양식을 보여줌
    public String join(@ModelAttribute RequestJoin form, Model model){
        //@ModelAttribute: reqeustJoin으로
        commonProcess(model,  Utils.getMessage("회원가입","common"));
        return utils.tpl("member/join");
    }

    @PostMapping("/join")
    public String joinPs(@Valid RequestJoin form, Errors errors, Model model){ //@Valid로 커맨드 객체 검증
        commonProcess(model, Utils.getMessage("회원가입","common"));

        saveService.join(form,errors);

        if(errors.hasErrors()){
            return utils.tpl("member/join"); //실패 시 유입 경로
        }
        return "redirect:/member/login";
    }

    @GetMapping("/login")
    public String login(String redirectUrl, Model model){
        commonProcess(model, Utils.getMessage("로그인","common"));
        model.addAttribute("redirectUrl",redirectUrl);
        return utils.tpl("member/login");
    }




    //로그인한 회원 정보를 조회하는 세 가지 방법
    /*
    public void info(){
        MemberInfo member = (MemberInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info(member.toString());
    }
     */

    /*
    public void info(@AuthenticationPrincipal MemberInfo memberInfo){ //@AuthenticationPrincipal UserDetails의 구현 클래스
        log.info(memberInfo.toString());
    }
     */

    /*
    public void info(Principal principal){
        String email = principal.getName(); //Principal에서는 로그인한 회원 아이디만 조회할 수 있다.
        log.info(email);
    }
     */


}
