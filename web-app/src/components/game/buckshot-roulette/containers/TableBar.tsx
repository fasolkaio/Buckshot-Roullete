import ItemsBar from "./ItemsBar.tsx";
import GunBar from "./GunBar.tsx";
import HealthPoints from "../elements/HealthPoints.tsx";
import {Player, ResultResponse} from "../game.interface.enum.ts";
import hands from "../../../../assets/hands.png";

interface TableBarProps {
    sessionId: string;
    player: Player;
    opponent: Player;
    gunSawed: boolean
    onGameUpdate: (updatesList: ResultResponse[]) => void;
    usedByOpponent?: string;
    gunPresent: boolean;
}

function TableBar({sessionId, player, opponent, gunSawed, onGameUpdate, usedByOpponent, gunPresent}: TableBarProps) {
    return <>
        <div className={"table-container"}>
            <ItemsBar sessionId={sessionId} isActive={false} items={opponent.items} onGameUpdate={onGameUpdate}
                      itemUsed={usedByOpponent}/>
            <GunBar sawed={gunSawed} sessionId={sessionId} onGameUpdate={onGameUpdate} gunPresent={gunPresent}/>
            <ItemsBar sessionId={sessionId} isActive={true} items={player.items} onGameUpdate={onGameUpdate}/>
            <div className={"player-health-bar"}>
                <HealthPoints currentHealth={player.lifeCount} maxHealth={player.maxLifeCount}/>
            </div>
        </div>
        {player.cuffed && <div className={"hands-bar"}>
            <img src={hands}></img>
        </div>}
    </>
}

export default TableBar