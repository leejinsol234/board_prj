package org.board.project.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = 1377495200L;

    public static final QBoard board = new QBoard("board");

    public final QBaseMember _super = new QBaseMember(this);

    public final BooleanPath active = createBoolean("active");

    public final EnumPath<org.board.project.commons.constants.BoardAuthority> authority = createEnum("authority", org.board.project.commons.constants.BoardAuthority.class);

    public final StringPath bId = createString("bId");

    public final StringPath bName = createString("bName");

    public final StringPath category = createString("category");

    public final EnumPath<org.board.project.commons.constants.MemberType> commentAccessRole = createEnum("commentAccessRole", org.board.project.commons.constants.MemberType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final EnumPath<org.board.project.commons.constants.MemberType> listAccessRole = createEnum("listAccessRole", org.board.project.commons.constants.MemberType.class);

    public final StringPath locationAfterWriting = createString("locationAfterWriting");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public final EnumPath<org.board.project.commons.constants.MemberType> replyAccessRole = createEnum("replyAccessRole", org.board.project.commons.constants.MemberType.class);

    public final StringPath skin = createString("skin");

    public final BooleanPath useAttachFile = createBoolean("useAttachFile");

    public final BooleanPath useAttachImage = createBoolean("useAttachImage");

    public final BooleanPath useComment = createBoolean("useComment");

    public final BooleanPath useEditor = createBoolean("useEditor");

    public final BooleanPath useReply = createBoolean("useReply");

    public final EnumPath<org.board.project.commons.constants.MemberType> ViewAccessRole = createEnum("ViewAccessRole", org.board.project.commons.constants.MemberType.class);

    public final EnumPath<org.board.project.commons.constants.MemberType> writeAccessRole = createEnum("writeAccessRole", org.board.project.commons.constants.MemberType.class);

    public QBoard(String variable) {
        super(Board.class, forVariable(variable));
    }

    public QBoard(Path<? extends Board> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoard(PathMetadata metadata) {
        super(Board.class, metadata);
    }

}

