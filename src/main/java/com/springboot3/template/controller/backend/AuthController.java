package com.springboot3.template.controller.backend;

import com.springboot3.template.utils.MvcTools;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Controller
@RequestMapping("/${template.entry-point.auth}")
public class AuthController {

    @GetMapping("/login")
    public String login(Authentication authentication) {
        if (Objects.nonNull(authentication)) {
            return MvcTools.redirectAuth("dashboard");
        }

        return "backend/login";
    }


    @GetMapping("/dashboard")
    public String dashboard() {
        return "backend/dashboard";
    }

}
