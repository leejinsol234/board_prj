package org.board.project.controllers.admins;

import lombok.Data;

@Data
public class BoardSearch {
    private int page = 1;
    private int limit = 20;

    private String sopt; // 검색 조건
    private String skey; // 검색 키워드
}
