package com.springboot3.template.controller.backend;

import com.springboot3.template.utils.MvcTools;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping("/internalServerError")
    public String internalServerError(HttpServletRequest request,Model model) {
        String message = (String)request.getParameter("message");
        model.addAttribute("message",message);
        return "backend/error500";
    }

}
