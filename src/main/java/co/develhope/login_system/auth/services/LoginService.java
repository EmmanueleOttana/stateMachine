package co.develhope.login_system.auth.services;

import co.develhope.login_system.auth.entities.LoginDTO;
import co.develhope.login_system.auth.entities.LoginRTO;
import co.develhope.login_system.user.entities.User;
import co.develhope.login_system.user.repositories.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class LoginService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    @Value("${JWT.secret}")
    private String secret;

    public LoginRTO login(LoginDTO loginDTO){
        if(loginDTO == null) return null;
        User userFromDB = userRepository.findByEmail(loginDTO.getEmail());
        if( userFromDB == null || !userFromDB.isActive() ) return null;

        boolean canLogin = this.canUserLogin(userFromDB, loginDTO.getPassword());
        if(!canLogin) return null;

        String JWT = generateJWT(userFromDB);

        userFromDB.setPassword(null);
        LoginRTO out = new LoginRTO();
        out.setJWT(JWT);
        out.setUser(userFromDB);

        return out;
    }

    public boolean canUserLogin(User user, String password){
        return passwordEncoder.matches(password, user.getPassword());
    }

    //https://www.baeldung.com/java-date-to-localdate-and-localdatetime
    static Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());
    }

    public String getJWT(User user){
        Date expiresAt = convertToDateViaInstant(LocalDateTime.now().plusDays(15));
        return JWT.create()
                .withIssuer("develhope-demo")
                .withIssuedAt(new Date())
                .withExpiresAt(expiresAt)
                .withClaim("id", user.getId())
                .sign(Algorithm.HMAC512(this.secret));

    }

    public String generateJWT(User user) {
        String JWT = getJWT(user);

        user.setJwtCreatedOn(LocalDateTime.now());
        userRepository.save(user);

        return JWT;
    }
}
