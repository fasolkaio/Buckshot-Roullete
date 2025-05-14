package sk.tuke.gamestudio.server.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sk.tuke.gamestudio.server.DTO.game.GameStateDTO;
import sk.tuke.gamestudio.server.DTO.game.ReloadActionResultDTO;

@Getter
@AllArgsConstructor
public class ReinitResponse {

    private GameStateDTO game;
    private ReloadActionResultDTO action;
}
