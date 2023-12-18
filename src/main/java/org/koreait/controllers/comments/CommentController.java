package org.koreait.controllers.comments;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.koreait.commons.ScriptExceptionProcess;
import org.koreait.commons.Utils;
import org.koreait.commons.exceptions.AlertBackException;
import org.koreait.commons.exceptions.AlertException;
import org.koreait.entities.BoardData;
import org.koreait.models.board.RequiredPasswordCheckException;
import org.koreait.models.comment.CommentDeleteService;
import org.koreait.models.comment.CommentInfoService;
import org.koreait.models.comment.CommentSaveService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController implements ScriptExceptionProcess {

    private final CommentSaveService saveService;
    private final CommentDeleteService deleteService;
    private final CommentInfoService infoService;
    private final Utils utils;

    @GetMapping("/update/{seq}")
    public String update(@PathVariable("seq") Long seq, Model model){

        infoService.isMine(seq);

        CommentForm form = infoService.getForm(seq);
        BoardData boardData = form.getBoardData();

        model.addAttribute("boardData",boardData);
        model.addAttribute("commentForm",form);
        model.addAttribute("mode","update");

        return utils.tpl("board/comment_update");
    }

    @PostMapping("/save")
    public String save(@Valid CommentForm form, Errors errors, Model model) {

        saveService.save(form, errors);

        if (errors.hasErrors()) {
            Map<String, List<String>> messages = Utils.getMessages(errors);
            String message = (new ArrayList<List<String>>(messages.values())).get(0).get(0);
            throw new AlertException(message); //메세지가 순서대로 보여지도록 하기 위해
        }


        Long seq = form.getSeq();
        //댓글 작성 완료 시 부모창을 새로고침 -> 새로운 목록 갱신
        if (seq == null) {
            model.addAttribute("script", "parent.location.reload();");
            return "common/_execute_script";
        } else {
            //댓글 수정 시 게시글 보기 페이지 이동 -> 해당 댓글 위치로 이동
            return "redirect:/board/view/" + form.getBoardDataSeq() + "#comment_"+ seq;
        }

}


    @RequestMapping("/delete/{seq}")
    public String delete(@PathVariable("seq") Long seq){

        BoardData boardData = deleteService.delete(seq);

        return "redirect:/board/view/" + boardData.getSeq() + "#comments";
    }

    @ExceptionHandler(RequiredPasswordCheckException.class)
    public String guestPassword(){
        return utils.tpl("board/password");
    }
}
