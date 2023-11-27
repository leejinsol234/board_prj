package org.koreait.controllers.admins;

import lombok.Data;

@Data
public class BoardSearch {
    private int page = 1;
    private int limit = 20; //한 페이지에 보여질 게시글 수

    private String sopt; // 검색 조건
    private String skey; // 검색 키워드
}
