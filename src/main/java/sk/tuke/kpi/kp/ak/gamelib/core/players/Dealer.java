package sk.tuke.kpi.kp.ak.gamelib.core.players;

import sk.tuke.kpi.kp.ak.gamelib.core.Game;
import sk.tuke.kpi.kp.ak.gamelib.core.actions.*;
import sk.tuke.kpi.kp.ak.gamelib.core.items.ItemUseResult;
import sk.tuke.kpi.kp.ak.gamelib.core.items.*;
import sk.tuke.kpi.kp.ak.gamelib.core.utilities.RandomGenerator;

public class Dealer extends Player {
    private final Game game;
    private Chance currentChance;
    private boolean remember;
    private Chance nextChance;
    private boolean sawWasUsed;
    private boolean handcuffWereUsed;

    public Dealer(String name, int maxLifeCount, Game game) {
        super(name, maxLifeCount);
        this.game = game;
        remember = false;
        sawWasUsed = false;
        handcuffWereUsed = false;
    }

    @Override
    public ActionResult doTurn(Action action) {
        currentChance = getCurrentChance();
        if(remember) {
            currentChance = nextChance;
            remember = false;
        }
        Action actionToUse = generateAction();
        ActionResult result = actionToUse.execute();
        if(remember){
            if(result instanceof UseActionResult && ((UseActionResult) result).getItemUseResult() == ItemUseResult.BULLET_WAS_BLANK)
                nextChance = Chance.ZERO;
            else
                nextChance = Chance.FULL;
        }

        return result;
    }

    private Chance getCurrentChance() {
        float chance = (float) game.getGun().getLiveBulletsCount()/(float)game.getGun().getBulletsCount();
        if(chance == 0)
            return Chance.ZERO;
        if(chance < 0.5)
            return Chance.LOW;
        if(chance == 0.5)
            return Chance.MEDIUM;
        if(chance == 1)
            return Chance.FULL;
        return Chance.HIGH;
    }

    private Action generateAction(){
        //use cigarettes if need
        if((getLifeCount() < getMaxLifeCount()) && isItemPresent(Cigarettes.class)){
            return new UseItem(game, Cigarettes.class);
        }

        //use handcuff if can
        if(currentChance != Chance.ZERO && isItemPresent(Handcuff.class) && !handcuffWereUsed){
            handcuffWereUsed = true;
            return new UseItem(game, Handcuff.class);
        }

        //use glass if can
        if(currentChance != Chance.ZERO && currentChance != Chance.FULL && isItemPresent(MagnifyingGlass.class)){
            remember = true;
            return new UseItem(game, MagnifyingGlass.class);
        }

        //use beer if have low chance
        if(Chance.LOW.equals(currentChance) && isItemPresent(Beer.class)) {
            return new UseItem(game, Beer.class);
        }

        //use saw if wanna hoot opponent
        if((currentChance == Chance.HIGH || currentChance == Chance.FULL) && isItemPresent(Saw.class) && !sawWasUsed){
            remember = true;
            sawWasUsed = true;
            return new UseItem(game, Saw.class);
        }

        //shoot
        sawWasUsed = false;
        handcuffWereUsed = false;
        if(shootOpponent()){
            return new Shoot(game, false);
        }
        return new Shoot(game, true);
    }

    private boolean shootOpponent() {
        if(currentChance == Chance.HIGH || currentChance == Chance.FULL)
            return true;
        else if(currentChance == Chance.LOW || currentChance == Chance.ZERO)
            return false;
        else
            return RandomGenerator.tossCoin();
    }

    private boolean isItemPresent(Class<? extends Item> itemClass){
        return (getItems().stream()
                .filter(itemClass::isInstance)
                .findFirst()
                .orElse(null) != null);
    }
}
