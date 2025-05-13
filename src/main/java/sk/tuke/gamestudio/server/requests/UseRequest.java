package sk.tuke.gamestudio.server.requests;

import lombok.Data;

@Data
public class UseRequest {
    String sessionId;
    String item;
}
