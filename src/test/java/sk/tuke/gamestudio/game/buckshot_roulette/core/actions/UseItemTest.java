package sk.tuke.gamestudio.game.buckshot_roulette.core.actions;

import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.game.buckshot_roulette.core.Game;
import sk.tuke.gamestudio.game.buckshot_roulette.core.actions.Action;
import sk.tuke.gamestudio.game.buckshot_roulette.core.actions.UseActionResult;
import sk.tuke.gamestudio.game.buckshot_roulette.core.actions.UseItem;
import sk.tuke.gamestudio.game.buckshot_roulette.core.items.Beer;

import static org.junit.jupiter.api.Assertions.*;

public class UseItemTest {

    @Test
    public void nullTest(){
        Action use = new UseItem(null, null);
        assertThrows(NullPointerException.class, use::execute);
    }

    @Test
    public void testExecute() {
        Game game = new Game("first player", "second player");
        Action use = new UseItem(game, Beer.class );
        assertInstanceOf(UseActionResult.class, use.execute());
    }
}