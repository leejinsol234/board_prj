package org.board.project.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.board.project.commons.constants.MemberType;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board extends BaseMember{

    @Id
    @Column(length = 30)
    private String bId;

    @Column(length = 60, nullable = false)
    private String bName;

    private boolean active;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private MemberType authority = MemberType.ALL;

    @Lob
    private String category; //게시판 분류

    // 목록 접근 권한
    @Enumerated(EnumType.STRING)
    @Column(length=10, nullable=false)
    private MemberType listAccessRole = MemberType.ALL;

    // 글보기 접근 권한
    @Enumerated(EnumType.STRING)
    @Column(length=10, nullable=false)
    private MemberType ViewAccessRole = MemberType.ALL;

    // 글쓰기 접근 권한
    @Enumerated(EnumType.STRING)
    @Column(length=10, nullable=false)
    private MemberType writeAccessRole = MemberType.ALL;

    // 답글 접근 권한
    @Enumerated(EnumType.STRING)
    @Column(length=10, nullable=false)
    private MemberType replyAccessRole = MemberType.ALL;

    // 댓글 접근 권한
    @Enumerated(EnumType.STRING)
    @Column(length=10, nullable=false)
    private MemberType commentAccessRole = MemberType.ALL;

    // 에디터 사용 여부
    private boolean useEditor;

    // 파일 첨부 사용여부
    private boolean useAttachFile;

    // 이미지 첨부 사용여부
    private boolean useAttachImage;

    // 글작성 후 이동
    @Column(length=10, nullable=false)
    private String locationAfterWriting = "view";

    // 답글 사용 여부
    private boolean useReply;

    // 댓글 사용 여부
    private boolean useComment;

    /** 비회원 작성, 수정 모드 여부 */
    @Transient
    private boolean isGuest;

    // 게시판 스킨
    @Column(length=20, nullable=false)
    private String skin = "default";

    /**
     * 게시판 분류 목록
     *
     * @return
     */
    public String[] getCategories() {
        if (category == null) {
            return null;
        }
        String[] categories = category.replaceAll("\\r", "").trim().split("\\n");
        return categories;
    }
}
