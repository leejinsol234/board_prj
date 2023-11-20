package org.board.project.controllers.members;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.board.project.commons.MemberUtil;
import org.board.project.commons.Utils;
import org.board.project.entities.Member;
import org.board.project.models.member.MemberInfo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final Utils utils;
    private final MemberUtil memberUtil;

    @GetMapping("/join")
    public String join(){
        return utils.tpl("member/join");
    }

    @GetMapping("/login")
    public String login(String redirectUrl, Model model){
        model.addAttribute("redirectUrl",redirectUrl);
        return utils.tpl("member/login");
    }
    //로그인한 회원 정보를 조회하는 세 가지 방법
    @GetMapping("/info")
    @ResponseBody //해당 메서드에만
    public void info(){

        Member member = memberUtil.getMember();
        if(memberUtil.isLogin()) {
            log.info(member.toString());
        }
        log.info("로그인 여부: {}",memberUtil.isLogin());
    }

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
