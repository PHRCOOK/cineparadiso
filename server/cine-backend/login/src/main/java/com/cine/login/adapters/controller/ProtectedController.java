package com.cine.login.adapters.controller;
import com.cine.login.domain.model.CinemaUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/protected")
public class ProtectedController {

    @GetMapping("/protected-endpoint")
    public ResponseEntity<String> protectedEndpoint() {
        return ResponseEntity.ok("¡Has accedido a un endpoint protegido!");
    }

    @GetMapping("/user")
    public Map<String, Long> getCurrentUserId(@AuthenticationPrincipal CinemaUser user) {
        Map<String, Long> response = new HashMap<>();
        response.put("id", user.getId());
        return response;
    }
}