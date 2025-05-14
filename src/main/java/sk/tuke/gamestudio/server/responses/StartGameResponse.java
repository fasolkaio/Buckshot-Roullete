package sk.tuke.gamestudio.server.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sk.tuke.gamestudio.server.DTO.game.GameStateDTO;
import sk.tuke.gamestudio.server.DTO.game.ReloadActionResultDTO;

@AllArgsConstructor
@Getter
public class StartGameResponse {
    private String sessionId;
    private GameStateDTO game;
    private ReloadActionResultDTO action;
}
