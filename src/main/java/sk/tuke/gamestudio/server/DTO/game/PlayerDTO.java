package sk.tuke.gamestudio.server.DTO.game;

import lombok.Getter;
import sk.tuke.gamestudio.game.buckshot_roulette.core.items.Item;
import sk.tuke.gamestudio.game.buckshot_roulette.core.players.Player;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PlayerDTO {
    private final int lifeCount;
    private final int maxLifeCount;
    private final List<String> items;
    private final boolean cuffed;

    public PlayerDTO(Player player){
        lifeCount = player.getLifeCount();
        maxLifeCount = player.getMaxLifeCount();
        items = player.getItems().stream().map(Item::toString).collect(Collectors.toList());
        cuffed = player.scipTurn();
    }
}
