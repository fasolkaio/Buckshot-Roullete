import {useState} from "react";
import '../../../../styles/containers.css';
import '../../../../styles/animations.css';
import '../../../../styles/menu.css';
import {MenuState} from "../game.interface.enum.ts";
import BuckshotRoulette from "./BuckshotRoulette.tsx";
import TypingText from "../../../utils/TypingText.tsx";

interface BuckshotRouletteMenuProps {
    player: string;
}

function MenuBuckshotRoulette({player}: BuckshotRouletteMenuProps) {
    const [menuState, setMenuState] = useState<MenuState>(MenuState.MENU);
    const [showMessage, setShowMessage] = useState(false);

    const handleMultiplayerButton = async () => {
        setShowMessage(true);
        setTimeout(() => setShowMessage(false), 10000);
    };


    return (
        <div className="background-game-container">
            {menuState === MenuState.SOLO_GAME && (
                <BuckshotRoulette changeMenuState={setMenuState} player={player}/>
            )}

            {menuState === MenuState.MENU && (
                <>
                    <div className="logo">
                        <h1>Welcome to Catshot Roulette game!</h1>
                    </div>

                    <div className="menu-buttons-container">
                        <button className="menu-buttons" onClick={() => setMenuState(MenuState.SOLO_GAME)}>
                            New game
                        </button>
                        <button className="menu-buttons" onClick={handleMultiplayerButton}>
                            Multiplayer
                        </button>
                    </div>

                    {showMessage && (
                        <div className="message">
                            <TypingText text="Coming soon..." speed={100}/>
                        </div>
                    )}
                </>
            )}
        </div>
    );
}

export default MenuBuckshotRoulette;