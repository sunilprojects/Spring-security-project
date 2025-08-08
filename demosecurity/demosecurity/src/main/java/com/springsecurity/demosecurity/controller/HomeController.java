package com.springsecurity.demosecurity.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.springsecurity.demosecurity.entity.AppUser;
import com.springsecurity.demosecurity.repository.UserRepository;

@Controller
public class HomeController {
	@Autowired
	private UserRepository userRepository;
	 @Autowired
	    private PasswordEncoder passwordEncoder;

    @GetMapping("/default")
    public String redirectAfterLogin(Authentication authentication) {
        if (authentication.getAuthorities().stream()
            .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/admin-home";
        } else {
            return "redirect:/home-guests";
        }
        
    }
    @GetMapping("/home-guests")
    @ResponseBody
    public String adminHome1() {
        return "<h1>Welcome home guests</h1>";
    }


    @GetMapping("/admin-home")
    @ResponseBody
    public String adminHome() {
        return "<h1>Welcome Admin</h1>";
    }

    @GetMapping("/user-home")
//    @ResponseBody
    public String userHome() {
        return "<h1>Welcome User</h1>";
    }
    
    @PostMapping("/register")
    public String processRegister(@RequestParam String username,
                                   @RequestParam String password,
                                   @RequestParam String role) {
        AppUser user = new   AppUser ();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);

        userRepository.save(user);

        return "redirect:/login.html?registered";
    }
    

    
    
}


