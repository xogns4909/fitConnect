package com.example.fitconnect.domain;

import com.example.fitconnect.config.exception.BusinessException;
import com.example.fitconnect.domain.user.domain.UserBaseInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class UserBaseInfoTest {

    @DisplayName("유효한 이메일, 닉네임, 프로필 사진 URL로 객체 생성")
    @ParameterizedTest
    @CsvSource({
            "test@example.com, TestNickname, http://example.com/profile.jpg",
            "another@test.com, AnotherNickname, http://example.com/another.jpg"
    })
    void whenValidEmailNickname_thenSuccessfullyCreated(String email, String nickname, String profilePicUrl) {
        UserBaseInfo baseInfo = new UserBaseInfo(email, nickname, profilePicUrl);

        assertNotNull(baseInfo);
        assertEquals(email, baseInfo.getEmail());
        assertEquals(nickname, baseInfo.getNickname());
        assertEquals(profilePicUrl, baseInfo.getProfilePictureUrl());
    }

    @DisplayName("유효하지 않은 이메일 입력 시 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"invalid-email", "another-invalid"})
    void whenInvalidEmail_thenThrowException(String invalidEmail) {
        String nickname = "TestNickname";
        String profilePicUrl = "http://example.com/profile.jpg";

        assertThrows(BusinessException.class, () -> {
            new UserBaseInfo(invalidEmail, nickname, profilePicUrl);
        });
    }

    @DisplayName("닉네임 길이 초과 시 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"123456789456123789456123546789521354,abcdefghijklmnopqrstuvwxyz"})
    void whenNicknameExceedsLength_thenThrowException(String longNickname) {
        String email = "test@example.com";
        String profilePicUrl = "http://example.com/profile.jpg";

        assertThrows(BusinessException.class, () -> {
            new UserBaseInfo(email, longNickname, profilePicUrl);
        });
    }
}