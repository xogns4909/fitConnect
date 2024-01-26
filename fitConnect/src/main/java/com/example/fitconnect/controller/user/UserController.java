package com.example.fitconnect.controller.user;

import com.example.fitconnect.auth.dto.GoogleInfoDto;
import com.example.fitconnect.auth.service.AuthService;
import com.example.fitconnect.config.service.CommonService;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.domain.user.dto.UserUpdateDto;
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
import org.springframework.web.bind.annotation.PathVariable;
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

    private final CommonService commonService;

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
    public ResponseEntity<User> getUserInfo(HttpSession session) {
        Long userId = commonService.extractUserIdFromSession(session);
        User user = userFindService.findUserInfo(userId);
        return ResponseEntity.ok(user);
    }

    @PutMapping("user")
    public ResponseEntity<Void> updateUser(@RequestBody UserUpdateDto userUpdateDto,
            HttpSession session) {
        Long userId = commonService.extractUserIdFromSession(session);
        userUpdateService.updateUser(userUpdateDto, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("user")
    public ResponseEntity<Void> deleteUser(HttpSession session) {
        Long userId = commonService.extractUserIdFromSession(session);
        userDeleteService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }


}
