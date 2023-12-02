package org.koreait.controllers.boards;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.koreait.entities.FileInfo;

import java.util.List;
import java.util.UUID;

@Data
public class BoardForm {

    private String mode = "write";

    private Long seq;

    private String bId;

    private String gid = UUID.randomUUID().toString();

    private String category;

    @NotBlank(message="제목을 입력하세요.")
    private String subject;

    @NotBlank(message="작성자를 입력하세요.")
    private String poster;

    @NotBlank(message="내용을 입력하세요.")
    private String content;

    private boolean notice; //공지사항으로 체크할 지 여부

    private String guestPw; // 비회원일 때 비밀번호

    private List<FileInfo> editorImages;

    private List<FileInfo> attachFiles;
}
