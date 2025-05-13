package sk.tuke.gamestudio.server.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.game.buckshot_roulette.core.Game;
import sk.tuke.gamestudio.game.buckshot_roulette.core.GameState;
import sk.tuke.gamestudio.game.buckshot_roulette.core.actions.*;
import sk.tuke.gamestudio.server.DTO.ActionFactory;
import sk.tuke.gamestudio.server.DTO.ActionResultDTO;
import sk.tuke.gamestudio.server.DTO.GameStateDTO;
import sk.tuke.gamestudio.server.requests.InitRequest;
import sk.tuke.gamestudio.server.requests.ReinitRequest;
import sk.tuke.gamestudio.server.requests.ShootRequest;
import sk.tuke.gamestudio.server.requests.UseRequest;
import sk.tuke.gamestudio.server.responses.ReinitResponse;
import sk.tuke.gamestudio.server.responses.ResultResponse;
import sk.tuke.gamestudio.server.responses.StartGameResponse;
import sk.tuke.gamestudio.server.services.GameService;

import java.util.*;

@RestController
@RequestMapping("/api/buckshot-roulette")
@AllArgsConstructor
public class BuckshotRouletteController {
    private final GameService gameService = new GameService();
    private final ActionFactory actionFactory = new ActionFactory();

    //Post -> http://localhost:8080/api/buckshot-roulette/new/
    @PostMapping("/new")
    public ResponseEntity<StartGameResponse> initNewGame(@RequestBody InitRequest request) {
        String sessionId = gameService.createSession(request.getName());
        Game game = gameService.getGame(sessionId);
        return ResponseEntity.ok(new StartGameResponse(sessionId, new GameStateDTO(game), actionFactory.convertToDTO(game.getGun())));
    }

    @PostMapping("/use")
    public ResponseEntity<List<ResultResponse>> useAction(@RequestBody UseRequest request ){
        Game game = gameService.getGame(request.getSessionId());
        if (game != null && !game.isDealerTurn() && !game.getActualPlayer().scipTurn()) {
            List<ResultResponse> actionsList = new ArrayList<>();

            Action action = actionFactory.createUseAction(game, request.getItem());
            appendResponseList(actionsList,action,game);
            reload(game,actionsList);

            return ResponseEntity.ok(actionsList);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/shoot")
    public ResponseEntity<List<ResultResponse>> shootAction(@RequestBody ShootRequest request){
        Game game = gameService.getGame(request.getSessionId());
        if (game != null && !game.isDealerTurn() && !game.getActualPlayer().scipTurn()) {
            List<ResultResponse> actionsList = new ArrayList<>();

            Action action = actionFactory.createShootAction(game, request.isSelf());
            appendResponseList(actionsList, action, game);

            while (game.getGameState() == GameState.SECOND_PLAYER_TURN || game.getFirstPlayer().scipTurn()){
                reload(game,actionsList);
                appendResponseList(actionsList, null, game);
            }
            if(game.getGameState() != GameState.ROUND_ENDED)
                reload(game,actionsList);
            return ResponseEntity.ok(actionsList);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/reinit")
    public ResponseEntity<ReinitResponse> reinitGame(@RequestBody ReinitRequest request) {
        Game game = gameService.getGame(request.getSessionId());
        if(game != null) {
            game.reinitRound();
            return ResponseEntity.ok(new ReinitResponse(new GameStateDTO(game), actionFactory.convertToDTO(game.getGun())));
        }
        return ResponseEntity.notFound().build();
    }


    private void appendResponseList(List<ResultResponse> actionsList, Action action, Game game) {
        ActionResult result = game.playTurn(action);
        ActionResultDTO resultDTO;
        if (result instanceof UseActionResult)
            resultDTO = actionFactory.convertToDTO((UseActionResult) result);
        else if (result instanceof ShootActionResult) {
            resultDTO = actionFactory.convertToDTO((ShootActionResult) result);
        }
        else
            resultDTO = actionFactory.convertToDTO((SkipTurnActionResult) result);
        actionsList.add(actionFactory.createResultResponse(game, resultDTO));
    }

    private void reload(Game game, List<ResultResponse> actionsList) {
        if(game.getGun().isEmpty()){
            game.reloadGun();
            game.generateItems();
            actionsList.add(actionFactory.createResultResponse(game, actionFactory.convertToDTO(game.getGun())));
        }
    }
}
