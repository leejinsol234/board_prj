package org.board.project.controllers.boards;

import lombok.RequiredArgsConstructor;
import org.board.project.commons.MemberUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class BoardFormValidator implements Validator {

    private final MemberUtil memberUtil;

    @Override
    public boolean supports(Class<?> clazz) {
        return BoardForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        BoardForm boardForm = (BoardForm) target;

        //비회원 비밀번호 체크
        Long id = boardForm.getId();
        Long userNo = boardForm.getUserNo();

        if ((id == null && !memberUtil.isLogin()) // 글 작성시 비회원
                || (id != null && userNo == null)) { // 글 수정시 비회원
            String guestPw = boardForm.getGuestPw();
            if (guestPw == null || guestPw.isBlank()) {
                errors.rejectValue("guestPw", "NotBlank");
            }

            if (guestPw != null && guestPw.length() < 6) {
                errors.rejectValue("guestPw", "Size");
            }
        }

    }
}
