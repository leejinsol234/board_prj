package org.koreait.models.board.config;

import lombok.RequiredArgsConstructor;
import org.koreait.commons.Utils;
import org.koreait.commons.exceptions.AlertException;
import org.koreait.entities.Board;
import org.koreait.repositories.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardConfigDeleteService {

    private final BoardRepository repository;
    private final Utils utils;


    //게시판 설정 개별 삭제
    public void delete(String bId){
        Board board = repository.findById(bId).orElseThrow(BoardNotFoundException::new); //존재하지 않으면 예외처리

        repository.delete(board);
        repository.flush();
    }

    //목록에서 삭제(일괄 삭제)
    public void delete(List<Integer> idxes){

        if(idxes == null || idxes.isEmpty()){
            throw new AlertException("삭제할 게시판을 선택하세요.");
        }

        for(int idx : idxes) {
            String bId = utils.getParam("bId_"+idx);
            Board board = repository.findById(bId).orElse(null);
            if(board == null) continue;

            repository.delete(board);
        }
        repository.flush();
    }
}
