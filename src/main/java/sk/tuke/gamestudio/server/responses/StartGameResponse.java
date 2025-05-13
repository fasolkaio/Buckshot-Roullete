package sk.tuke.gamestudio.server.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sk.tuke.gamestudio.server.DTO.GameStateDTO;
import sk.tuke.gamestudio.server.DTO.ReloadActionResultDTO;

@AllArgsConstructor
@Getter
public class StartGameResponse {
    private String sessionId;
    private GameStateDTO game;
    private ReloadActionResultDTO action;
}
