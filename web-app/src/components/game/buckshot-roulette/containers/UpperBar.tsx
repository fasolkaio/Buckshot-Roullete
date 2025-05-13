import '../../../../styles/containers.css';
import HealthPoints from "../elements/HealthPoints.tsx";
import {MenuState, Player} from "../game.interface.enum.ts";
import exit from "../../../../assets/x.svg";
import reload from "../../../../assets/arrow-clockwise.svg";


interface UpperBarProps {
    score: number;
    changeMenuState: (state: MenuState) => void;
    restartGame : () => void;
    opponent: Player
}

function UpperBar({score, opponent, restartGame, changeMenuState}: UpperBarProps) {
    return (
        <div className="upper-bar-container">
            <h2>Score: {score}</h2>
            <HealthPoints currentHealth={opponent.lifeCount} maxHealth={opponent.maxLifeCount}/>
            <div className="control-buttons-bar">
                <button onClick={() => {
                    changeMenuState(MenuState.MENU)
                }} className={"control-button"}>
                    <img src={exit}></img>
                </button>
                <button onClick={() => {
                    restartGame();
                }} className={"control-button"}>
                    <img src={reload}></img>
                </button>
            </div>
        </div>
    );
}

export default UpperBar;