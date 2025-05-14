package sk.tuke.gamestudio.server.DTO.game;

import lombok.Getter;
import sk.tuke.gamestudio.game.buckshot_roulette.core.Game;
import sk.tuke.gamestudio.game.buckshot_roulette.core.GameState;

@Getter
public class GameStateDTO {
    private final GameState state;
    private final int score;
    private final PlayerDTO player;
    private final PlayerDTO opponent;
    private final boolean gunSawed;

    public GameStateDTO(Game game) {
        this.state = game.getGameState();
        this.score = game.getScore();
        this.player = new PlayerDTO(game.getFirstPlayer());
        this.opponent = new PlayerDTO(game.getSecondPlayer());
        this.gunSawed = (game.getGun().getDamage() != 1);
    }
}
