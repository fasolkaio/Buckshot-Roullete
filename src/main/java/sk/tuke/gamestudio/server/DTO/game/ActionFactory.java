package sk.tuke.gamestudio.server.DTO.game;

import sk.tuke.gamestudio.game.buckshot_roulette.core.Game;
import sk.tuke.gamestudio.game.buckshot_roulette.core.actions.*;
import sk.tuke.gamestudio.game.buckshot_roulette.core.items.*;
import sk.tuke.gamestudio.game.buckshot_roulette.core.weapon.Gun;
import sk.tuke.gamestudio.server.responses.ResultResponse;

public class ActionFactory {
    public UseActionResultDTO convertToDTO(UseActionResult actionResult){
        return new UseActionResultDTO(ActionType.USE,
                actionResult.getItemClass().getSimpleName(),
                actionResult.getPlayer().getName(),
                actionResult.getItemUseResult());
    }

    public ShootActionResultDTO convertToDTO(ShootActionResult actionResult){
        return new ShootActionResultDTO(ActionType.SHOOT,
                actionResult.getPlayer().getName(),
                actionResult.isSelfShoot(),
                actionResult.isShootResult());
    }

    public SkipActionResultDTO convertToDTO(SkipTurnActionResult actionResult){
        return new SkipActionResultDTO(ActionType.SKIP);
    }

    public ReloadActionResultDTO convertToDTO(Gun gun){
        return new ReloadActionResultDTO(ActionType.RELOAD,
                gun.getBulletsCount() - gun.getLiveBulletsCount(),
                gun.getLiveBulletsCount());
    }

    public Action createUseAction(Game game, String item){
        return new UseItem(game, getItemClass(item.toLowerCase()));
    }

    public Action createShootAction(Game game, boolean self){
        return new Shoot(game, self);
    }

    private Class<? extends Item> getItemClass(String item){
        return switch (item) {
            case "beer" -> Beer.class;
            case "saw" -> Saw.class;
            case "handcuff" -> Handcuff.class;
            case "cigarettes" -> Cigarettes.class;
            case "magnifyingglass" -> MagnifyingGlass.class;
            default -> throw new UnsupportedOperationException("Invalid item: " + item);
        };
    }

    public ResultResponse createResultResponse(Game game, ActionResultDTO actionResultDTO){
        return new ResultResponse(new GameStateDTO(game), actionResultDTO);
    }
}
