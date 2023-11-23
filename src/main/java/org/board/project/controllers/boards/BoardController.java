package org.board.project.controllers.boards;

import lombok.RequiredArgsConstructor;
import org.board.project.models.board.config.BoardConfigSaveService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardConfigSaveService boardConfigSaveService;

}
