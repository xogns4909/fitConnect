package com.example.fitconnect.domain;

import com.example.fitconnect.domain.user.Role;
import com.example.fitconnect.domain.user.User;
import com.example.fitconnect.domain.user.UserBaseInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @DisplayName("유효한 UserBaseInfo와 Role로 User 객체 생성")
    @Test
    void whenValidUserBaseInfoAndRole_thenSuccessfullyCreated() {
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
    void whenUserBaseInfoIsNull_thenThrowException() {
        Role role = Role.MEMBER;

        assertThrows(IllegalArgumentException.class, () -> {
            new User(null, role);
        });
    }

    @DisplayName("Role이 null일 때 예외 발생")
    @Test
    void whenRoleIsNull_thenThrowException() {
        UserBaseInfo baseInfo = new UserBaseInfo("test@example.com", "TestNickname",
                "http://example.com/profile.jpg");

        assertThrows(IllegalArgumentException.class, () -> {
            new User(baseInfo, null);
        });
    }
}
