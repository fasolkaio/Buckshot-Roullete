package sk.tuke.gamestudio.server.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReinitRequest {
    private  final String sessionId;
}
