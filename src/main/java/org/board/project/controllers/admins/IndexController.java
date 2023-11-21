package org.board.project.controllers.admins;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("adminIndexController") //충돌 방지를 위해 bean으로 등록되는 이름을 변경
@RequestMapping("/admin")
public class IndexController {
    @GetMapping
    public String index() {
        return "admin/main/index";
    }
}