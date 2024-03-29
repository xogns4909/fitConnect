package com.example.fitconnect.domain.user.domain;

import com.example.fitconnect.global.error.ErrorMessages;
import com.example.fitconnect.global.exception.BusinessException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.regex.Pattern;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Embeddable
@Slf4j
@Getter
public class UserBaseInfo {

    @Column(nullable = false, unique = true, length = 50)
    private String email;
    @Column(length = 30)
    private String nickname;

    private String profilePictureUrl;

    public UserBaseInfo(String email, String nickname, String profilePictureUrl) {
        validateEmail(email);
        validNicknameLength(nickname);

        this.email = email;
        this.nickname = nickname;
        this.profilePictureUrl = profilePictureUrl;
    }

    public void update(String nickname){
        this.nickname = nickname;
    }
    public UserBaseInfo() {

    }

    private void validateEmail(String email) {
        if (!isValidEmailFormat(email)) {
            log.warn("Invalid email format: {}", email);
            throw new BusinessException(ErrorMessages.INVALID_EMAIL_FORMAT);
        }
    }

    private boolean isValidEmailFormat(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private void validNicknameLength(String nickname) {
        if (nickname.length() > 30) {
            log.warn("nickname 길이가 30을 초과했습니다. nickname.length() : {}", nickname.length());
            throw new BusinessException(ErrorMessages.NICKNAME_LENGTH_EXCEEDED);
        }
    }

}
