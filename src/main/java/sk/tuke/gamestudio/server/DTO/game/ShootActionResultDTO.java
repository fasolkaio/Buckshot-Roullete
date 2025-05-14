package sk.tuke.gamestudio.server.DTO.game;

import lombok.Getter;

@Getter
public class ShootActionResultDTO extends ActionResultDTO {
    private final String shooterName;
    private final boolean selfShoot;
    private final boolean success;

    protected ShootActionResultDTO(ActionType actionType, String shooterName, boolean selfShoot, boolean success) {
        super(actionType);
        this.shooterName = shooterName;
        this.selfShoot = selfShoot;
        this.success = success;
    }
}
