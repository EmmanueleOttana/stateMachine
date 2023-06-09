package co.develhope.login_system.auth.entities;

import co.develhope.login_system.user.entities.User;
import lombok.Data;

@Data
public class LoginRTO {

    private User user;
    private String JWT;
}
