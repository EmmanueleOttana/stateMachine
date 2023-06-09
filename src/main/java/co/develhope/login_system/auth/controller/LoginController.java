package co.develhope.login_system.auth.controller;

import co.develhope.login_system.auth.entities.LoginDTO;
import co.develhope.login_system.auth.entities.LoginRTO;
import co.develhope.login_system.auth.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public LoginRTO login(@RequestBody LoginDTO loginDTO) throws Exception{
        LoginRTO loginRto = loginService.login(loginDTO);
        if(loginRto == null) throw new Exception("Cannot login");
        return loginRto;
    }
}