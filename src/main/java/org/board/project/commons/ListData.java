package org.board.project.commons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListData<T> {

    private List<T> content; //게시판 한 페이지에 보이는 게시글 목록
    private Pagination pagination; //< 1 2 3 ... > 전체 페이지 구분
}
