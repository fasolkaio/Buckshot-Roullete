import {ShootAnimation} from "../game.interface.enum.ts";
import Sprite from "../../../utils/Sprite.tsx";
import shootOpponent from "../../../../assets/player-shoot-opponent.png";
import shootSelf from "../../../../assets/player-shoot-himself.png";

interface ShootAnimationProps {
    playerAnimation: ShootAnimation;
}

function ShootAnimationComponent({playerAnimation}: ShootAnimationProps) {
    const animationKeyShoot =
        playerAnimation == ShootAnimation.SHOOT ? 'opponent' :
            playerAnimation == ShootAnimation.SELF_SHOOT ? 'self' :
                'none';

    return (
        <>
            {playerAnimation === ShootAnimation.SHOOT ?
                (<div className={"player-gun-animation"}>
                    <Sprite
                        key={`shoot-${animationKeyShoot}`}
                        sprite={shootOpponent}
                        widthFrame={250}
                        heightFrame={250}
                        frameCount={7}
                        animationDuration={600}
                        scaleToParent={true}
                    />
                </div>)
                : playerAnimation === ShootAnimation.SELF_SHOOT ?
                    (<div className={"player-gun-animation"}>
                        <Sprite
                            key={`self-shoot-${animationKeyShoot}`}
                            sprite={shootSelf}
                            widthFrame={250}
                            heightFrame={250}
                            frameCount={7}
                            animationDuration={600}
                            scaleToParent={true}
                        />
                    </div>)
                    : null}
        </>
    );
}

export default ShootAnimationComponent;