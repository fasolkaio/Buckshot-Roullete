import catDefault from "../../../../assets/cat-default.png"
import catCuffed from "../../../../assets/cat-cuffed.png"
import {useState} from "react";
import {ShootAnimation} from "../game.interface.enum.ts";
import Sprite from "../../../utils/Sprite.tsx";
import shootPlayerSprite from "../../../../assets/cat-shoot-player.png";
import shootSelfSprite from "../../../../assets/cat-shoot-himself.png";

interface OpponentBarProps {
    cuffed: boolean;
    animation: ShootAnimation;
    isAlive: boolean;
}

function OpponentBar({cuffed, animation, isAlive}: OpponentBarProps) {
    const [isFlattened, setIsFlattened] = useState(false);

    function toggleFlatten() {
        setIsFlattened((prevState) => !prevState);

        setTimeout(() => {
            setIsFlattened(false);
        }, 300);
    }

    return (
        <div className={"opponent-container no-margin"}>
            {isAlive && <>
                {animation === ShootAnimation.NONE ? (
                    <img
                        src={cuffed ? catCuffed : catDefault}
                        className={`${isFlattened ? "flattened" : ""}`}
                        onClick={toggleFlatten}
                    />
                ) : animation === ShootAnimation.SHOOT ? (
                    <Sprite
                        key={animation}
                        className={"opponent-animation"}
                        sprite={shootPlayerSprite}
                        widthFrame={357}
                        heightFrame={250}
                        frameCount={7}
                        animationDuration={600}
                        scaleToParent={true}
                    />
                ) : animation === ShootAnimation.SELF_SHOOT ? (
                    <Sprite
                        key={animation}
                        className={"opponent-animation"}
                        sprite={shootSelfSprite}
                        widthFrame={357}
                        heightFrame={250}
                        frameCount={7}
                        animationDuration={600}
                        scaleToParent={true}
                    />
                ) : null}
            </>}
        </div>
    );
}

export default OpponentBar;