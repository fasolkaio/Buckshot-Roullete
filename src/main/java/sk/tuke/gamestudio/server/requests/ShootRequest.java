package sk.tuke.gamestudio.server.requests;

import lombok.Data;

@Data
public class ShootRequest {
    String sessionId;
    boolean self;
}
