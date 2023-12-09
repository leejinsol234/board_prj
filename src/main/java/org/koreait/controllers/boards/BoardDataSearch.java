package org.koreait.controllers.boards;

import lombok.Data;

@Data
public class BoardDataSearch {
    private String bId; //게시판 아이디
    private int page = 1;
    private int limit = 20;
    //검색 키워드: 제목, 제목+내용, 작성자명 등
    private String sopt; //검색 옵션
    private String skey; //검색 키워드
    private String category;
}
