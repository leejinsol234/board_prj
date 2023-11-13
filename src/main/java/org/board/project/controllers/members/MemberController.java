package org.board.project.controllers.members;

import lombok.RequiredArgsConstructor;
import org.board.project.commons.Utils;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final Utils utils;

    @GetMapping("/join")
    public String join(){
        return utils.tpl("member/join");
    }

    @GetMapping("/login")
    public String login(String redirectUrl, Model model){
        model.addAttribute("redirectUrl",redirectUrl);
        return utils.tpl("member/login");
    }



}
