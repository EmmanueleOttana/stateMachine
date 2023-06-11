package co.develhope.login_system.auth.controller;

import co.develhope.login_system.auth.entities.RequestPasswordDTO;
import co.develhope.login_system.auth.entities.RestorePasswordDTO;
import co.develhope.login_system.auth.services.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/password")
public class PasswordRestoreController {

    @Autowired
    private PasswordService passwordService;

    @PostMapping("/request")
    public void passwordRequest(@RequestBody RequestPasswordDTO requestPasswordDTO) throws Exception{
        try {
            passwordService.request(requestPasswordDTO);
        }catch (Exception ignored){}

    }

    @PostMapping("/restore")
    public void passwordRestore(@RequestBody RestorePasswordDTO restorePasswordDTO) throws Exception{
        passwordService.restore(restorePasswordDTO);

    }
}
