package sk.tuke.gamestudio.server.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sk.tuke.gamestudio.game.buckshot_roulette.core.Game;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameService {
    private static final long SESSION_TIMEOUT = 30 * 60 * 1000;
    private final Map<String, SessionData> gameSessions = new ConcurrentHashMap<>();

    public String createSession(String player) {
        String sessionId = UUID.randomUUID().toString();
        gameSessions.put(sessionId, new SessionData(new Game(player)));
        return sessionId;
    }

    public Game getGame(String sessionId) {
        SessionData session = gameSessions.get(sessionId);
        if (session == null) return null;
        session.updateLastAccess();
        return session.getGame();
    }

    public void removeSession(String sessionId) {
        gameSessions.remove(sessionId);
    }

    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void cleanUpInactiveSessions() {
        long now = System.currentTimeMillis();
        gameSessions.entrySet().removeIf(entry ->
                now - entry.getValue().getLastAccess() > SESSION_TIMEOUT);
    }

    private static class SessionData {
        private final Game game;
        private long lastAccess;

        public SessionData(Game game) {
            this.game = game;
            this.lastAccess = System.currentTimeMillis();
        }

        public Game getGame() {
            return game;
        }

        public long getLastAccess() {
            return lastAccess;
        }

        public void updateLastAccess() {
            this.lastAccess = System.currentTimeMillis();
        }
    }
}
