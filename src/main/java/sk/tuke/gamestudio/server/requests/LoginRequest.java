package sk.tuke.gamestudio.server.requests;

import lombok.Data;

@Data
public class LoginRequest {
    private String name;
    private String password;
}
