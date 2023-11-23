package org.board.project.models.board.config;

import lombok.RequiredArgsConstructor;
import org.board.project.commons.constants.BoardAuthority;
import org.board.project.controllers.admins.BoardConfigForm;
import org.board.project.entities.Board;
import org.board.project.repositories.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class BoardConfigSaveService { //게시판 설정 저장

    private final BoardRepository boardRepository;

    public void save(BoardConfigForm form){ //수정 및 추가

        String bId = form.getBId();
        String mode = form.getMode();
        Board board = null;
        if(mode.equals("edit") && StringUtils.hasText(bId)){
            board = boardRepository.findById(bId).orElseThrow(BoardNotFoundException::new);
        } else { //기본키 아이디는 최초에 한 번만 추가됨
            board = new Board();
            board.setBId(bId);
        }
        board.setBName(form.getBName());
        board.setActive(form.isActive());
        board.setAuthority(BoardAuthority.valueOf(form.getAuthority()));
        board.setCategory(form.getCategory());

        boardRepository.saveAndFlush(board);

    }
}
