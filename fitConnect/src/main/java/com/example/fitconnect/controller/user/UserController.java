package com.example.fitconnect.controller.user;

import com.example.fitconnect.auth.dto.GoogleInfoDto;
import com.example.fitconnect.auth.service.AuthService;
import com.example.fitconnect.config.annotation.CurrentUserId;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.dto.user.request.UserUpdateDto;
import com.example.fitconnect.service.user.LoginService;
import com.example.fitconnect.service.user.UserDeleteService;
import com.example.fitconnect.service.user.UserFindService;
import com.example.fitconnect.service.user.UserUpdateService;
import jakarta.servlet.http.HttpSession;
import java.util.Map;
import jdk.jshell.Snippet.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final LoginService loginService;
    private final AuthService authService;

    private final UserFindService userFindService;

    private final UserUpdateService userUpdateService;

    private final UserDeleteService userDeleteService;


    @PostMapping("/api/auth/google")
    public ResponseEntity<Map<String, String>> login(@RequestBody String token,
            HttpSession session) {
        GoogleInfoDto authenticate = authService.authenticate(token);
        Map<String, String> tokens = loginService.processUserLogin(authenticate);
        session.setAttribute("accessToken", tokens.get("accessToken"));
        session.setAttribute("refreshToken", tokens.get("refreshToken"));
        return ResponseEntity.ok(tokens);

    }

    @PostMapping("/api/auth/logout")
    public ResponseEntity<Status> logout(HttpSession session) {
        session.removeAttribute("accessToken");
        session.removeAttribute("refreshToken");
        return ResponseEntity.ok().build();
    }

    @GetMapping("user")
    public ResponseEntity<User> getUserInfo(@CurrentUserId Long userId) {
        User user = userFindService.findUserInfo(userId);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("user")
    public ResponseEntity<Void> updateUser(@RequestBody UserUpdateDto userUpdateDto,
            @CurrentUserId Long userId) {
        userUpdateService.updateUser(userUpdateDto, userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("user")
    public ResponseEntity<Void> deleteUser(@CurrentUserId Long userId) {
        userDeleteService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }


}
