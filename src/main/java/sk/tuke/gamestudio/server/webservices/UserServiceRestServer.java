package sk.tuke.gamestudio.server.webservices;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.User;
import sk.tuke.gamestudio.server.requests.LoginRequest;
import sk.tuke.gamestudio.server.responses.LoginResponse;
import sk.tuke.gamestudio.server.security.JwtUtil;
import sk.tuke.gamestudio.service.UserException;
import sk.tuke.gamestudio.service.UserService;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserServiceRestServer {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public void signUp(@RequestBody User user) {
        userService.signUp(user);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        User user = userService.loginUser(request.getName(), request.getPassword());
        if (user == null) {
            throw new UserException("Invalid username or password");
        }
        String token = jwtUtil.generateToken(user.getName());
        return new LoginResponse(token);
    }

    @DeleteMapping("/reset")
    public void reset() {
        userService.reset();
    }
}
