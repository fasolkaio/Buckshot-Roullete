import {ShootIn} from "../game.interface.enum.ts";
import Sprite from "../../../utils/Sprite.tsx";
import boom from "../../../../assets/boom-pink.png";

interface BoomAnimationComponentProp {
    shootIn: ShootIn;
}

function BoomAnimationComponent({shootIn}: BoomAnimationComponentProp) {
    const animationKeyBoom =
        shootIn == ShootIn.PLAYER ? 'player' :
            shootIn == ShootIn.DEALER ? 'dealer' :
                'none';

    return <>
        {
            shootIn == ShootIn.DEALER ? (
                <div className={"boom-dealer-animation"}>
                    <Sprite
                        key={`boom-dealer-${animationKeyBoom}`}
                        sprite={boom}
                        widthFrame={1000}
                        heightFrame={1000}
                        frameCount={7}
                        animationDuration={500}
                        scaleToParent={true}
                    />
                </div>
            ) : shootIn == ShootIn.PLAYER ? (
                <div className={"boom-player-animation"}>
                    <Sprite
                        key={`boom-player-${animationKeyBoom}`}
                        sprite={boom}
                        widthFrame={1000}
                        heightFrame={1000}
                        frameCount={7}
                        animationDuration={500}
                        scaleToParent={true}
                    />
                </div>
            ) : null
        }
    </>
}

export default BoomAnimationComponent;