package sk.tuke.gamestudio.server.DTO.game;

import lombok.Getter;
import sk.tuke.gamestudio.game.buckshot_roulette.core.items.ItemUseResult;

@Getter
public class UseActionResultDTO extends ActionResultDTO {
    private final String item;
    private final String usedBy;
    private final ItemUseResult result;

    protected UseActionResultDTO(ActionType actionType, String item, String usedBy, ItemUseResult itemUseResult) {
        super(actionType);
        this.item = item;
        this.usedBy = usedBy;
        this.result = itemUseResult;
    }
}
