package sk.tuke.gamestudio.server.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sk.tuke.gamestudio.server.DTO.game.ActionResultDTO;
import sk.tuke.gamestudio.server.DTO.game.GameStateDTO;

@AllArgsConstructor
@Getter
public class ResultResponse {
    GameStateDTO game;
    ActionResultDTO action;
}
