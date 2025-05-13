package sk.tuke.gamestudio.server.services;

import sk.tuke.gamestudio.game.buckshot_roulette.core.Game;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class GameService {
    private final Map<String, Game> gameSessions = new ConcurrentHashMap<>();

    public String  createSession(String player) {
        String sessionId = UUID.randomUUID().toString();
        Game game = new Game(player);
        gameSessions.put(sessionId, game);
        return sessionId;
    }

    public Game getGame(String sessionId) {
        return gameSessions.get(sessionId);
    }
}
