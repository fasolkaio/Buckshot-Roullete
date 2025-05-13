package sk.tuke.gamestudio.server.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sk.tuke.gamestudio.server.DTO.ActionResultDTO;
import sk.tuke.gamestudio.server.DTO.GameStateDTO;

@AllArgsConstructor
@Getter
public class ResultResponse {
    GameStateDTO game;
    ActionResultDTO action;
}
