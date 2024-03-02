package com.example.fitconnect.domain;

import com.example.fitconnect.global.exception.BusinessException;
import com.example.fitconnect.domain.user.domain.Role;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.domain.user.domain.UserBaseInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @DisplayName("유효한 UserBaseInfo와 Role로 User 객체 생성")
    @Test
    void createUserTest() {
        UserBaseInfo baseInfo = new UserBaseInfo("test@example.com", "TestNickname",
                "http://example.com/profile.jpg");
        Role role = Role.MEMBER;

        User user = new User(baseInfo, role);

        assertNotNull(user);
        assertEquals("test@example.com", user.getUserBaseInfo().getEmail());
        assertEquals("TestNickname", user.getUserBaseInfo().getNickname());
        assertEquals(Role.MEMBER, user.getRole());
    }

    @DisplayName("UserBaseInfo가 null일 때 예외 발생")
    @Test
    void userBaseInfoNullTest() {
        Role role = Role.MEMBER;

        assertThrows(BusinessException.class, () -> {
            new User(null, role);
        });
    }

    @DisplayName("Role이 null일 때 예외 발생")
    @Test
    void roleNullTest() {
        UserBaseInfo baseInfo = new UserBaseInfo("test@example.com", "TestNickname",
                "http://example.com/profile.jpg");

        assertThrows(BusinessException.class, () -> {
            new User(baseInfo, null);
        });
    }
}
