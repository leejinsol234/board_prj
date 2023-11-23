package org.board.project.models.board.config;

import lombok.RequiredArgsConstructor;
import org.board.project.commons.MemberUtil;
import org.board.project.commons.constants.MemberType;
import org.board.project.entities.Board;
import org.board.project.repositories.BoardRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardConfigInfoService { //게시판 설정 조회

    private final BoardRepository boardRepository;
    private final MemberUtil memberUtil;

    public Board get(String bId, String location){ //프론트, 접근 권한 체크
        return get(bId,location);
    }

    public Board get(String bId, boolean isAdmin, String location) {

        Board board = boardRepository.findById(bId).orElseThrow(BoardConfigNotExistException::new);

        if (!isAdmin) { // 권한 체크
            accessCheck(board, location);
        }

        return board;
    }

    public Board get(String bId, boolean isAdmin) {
        return get(bId, isAdmin, null);
    }

    // 접근 권한 체크
    private void accessCheck(Board board, String location) {

        /**
         * use - false : 모든 항목 접근 불가, 단 관리자만 가능
         */
        if (!board.isActive() && !memberUtil.isAdmin()) {
            throw new BoardNotAllowAccessException();
        }

        MemberType mType = MemberType.ALL;
        if (location.equals("list")) { // 목록 접근 권한
            mType = board.getListAccessRole();

        } else if (location.equals("view")) { // 게시글 접근 권한
            mType = board.getViewAccessRole();

        } else if (location.equals("write")) { // 글쓰기 권한
            mType = board.getWriteAccessRole();

            /** 비회원 게시글 여부 */
            if (!memberUtil.isLogin()) board.setGuest(true);

        } else if (location.equals("reply")) { // 답글 권한
            mType = board.getReplyAccessRole();

        } else if (location.equals("comment")) { // 댓글 권한
            mType = board.getCommentAccessRole();

        }

        if ((mType == MemberType.USER && !memberUtil.isLogin())
                || (mType == MemberType.ADMIN && !memberUtil.isAdmin())) {
            throw new BoardNotAllowAccessException();
        }


    }
}
