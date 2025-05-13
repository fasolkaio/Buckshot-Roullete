import gun from "../../../../assets/gun.png"
import sawedGun from "../../../../assets/sawed-gun.png"
import {ResultResponse} from "../game.interface.enum.ts";
import {useState} from "react";
import {shoot} from "../../../../api/game.service.ts";

interface GunProps {
    sessionId: string;
    sawed: boolean;
    onGameUpdate: (updatesList: ResultResponse[]) => void;
    gunPresent: boolean;
}

function GunBar({sessionId, sawed, onGameUpdate, gunPresent}: GunProps) {
    const [showButtons, setShowButtons] = useState(false);

    const handleGunClick = () => {
        setShowButtons(true); // Показуємо 2 кнопки
    };

    const handleChooseClick = async (self: boolean) => {
        try {
            const response = await shoot(sessionId, self);
            onGameUpdate(response.data);
        } catch (error) {
            console.error("Item use failed", error);
        } finally {
            setShowButtons(false);
        }
    };

    return <div className={"gun-container no-margin"}>
        {gunPresent && <>
            {!showButtons && <img src={sawed ? sawedGun : gun} onClick={() => handleGunClick()}/>}
            {showButtons && <div className={"blocked-game-table"}>
                <button className={"shoot-button"} onClick={() => handleChooseClick(false)}>DEALER</button>
                <button className={"shoot-button"} onClick={() => handleChooseClick(true)}>ME</button>
            </div>}
        </>}
    </div>

}

export default GunBar;