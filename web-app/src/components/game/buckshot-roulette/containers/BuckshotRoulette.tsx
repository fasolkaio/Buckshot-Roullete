import {useEffect, useState} from "react";
import UpperBar from "./UpperBar.tsx";
import OpponentBar from "./OpponentBar.tsx";
import TableBar from "./TableBar.tsx";
import {initGame, reinitRound} from "../../../../api/game.service.ts";
import {
    GameField,
    MenuState,
    ReloadActionResult,
    ResultResponse,
    ShootActionResult,
    ShootAnimation,
    ShootIn,
    UseActionResult
} from "../game.interface.enum.ts";
import ShootAnimationComponent from "../animations/ShootAnimationComponent.tsx";
import BoomAnimationComponent from "../animations/BoomAnimationComponent.tsx";
import ReloadMessageBar from "./ReloadMessageBar.tsx";
import UseMessageBar from "./UseMessageBar.tsx";
import EndGameMessageBar from "./EndGameMessageBar.tsx";

interface GameProps {
    player: string;
    changeMenuState: (state: MenuState) => void;
}

function BuckshotRoulette({player, changeMenuState}: GameProps) {
    const [sessionId, setSessionId] = useState<string | null>(null);
    const [currentGameField, setCurrentGameField] = useState<GameField | null>(null);
    const [gameState, setGameState] = useState<string>('');

    const [blocked, setBlocked] = useState(false);

    const [opponentAnimation, setOpponentAnimation] = useState<ShootAnimation>(ShootAnimation.NONE);
    const [playerAnimation, setPlayerAnimation] = useState<ShootAnimation>(ShootAnimation.NONE);
    const [shootIn, setShootIn] = useState<ShootIn>(ShootIn.NONE);

    const [itemUsed, setItemUsed] = useState<string>('');
    const [reloaded, setReloaded] = useState<number[]>([]);
    const [useMessage, setUsedMessage] = useState<string[]>([]);

    useEffect(() => {
        startGame();
    }, [player]);

    useEffect(() => {
        if (gameState === 'ROUND_ENDED')
            setBlocked(true);
    }, [gameState]);

    const handleReloadAnimation = async (reloadAction: ReloadActionResult) => {
        try {
            setBlocked(true);
            setReloaded([reloadAction.lives, reloadAction.blanks]);
            await new Promise(resolve => setTimeout(resolve, 2000));
            setReloaded([]);
        } catch (error) {
            console.log(error);
        } finally {
            setBlocked(false);
        }
    };

    const startGame = async () => {
        try {
            const response = await initGame(player);
            setCurrentGameField(response.data.game);
            setSessionId(response.data.sessionId);
            setGameState(response.data.game.state);
            console.log(response.data.action);
            await handleReloadAnimation(response.data.action as ReloadActionResult);
        } catch (error) {
            console.error("Session not found", error);
        }
    };

    const updateGameState = async (updatesList: ResultResponse[]) => {
        try {
            setBlocked(true);
            for (const response of updatesList) {
                console.log(response.action);
                await new Promise(resolve => setTimeout(resolve, 500));

                if (response.action.type === "SHOOT") {
                    const shootAction = response.action as ShootActionResult;
                    triggerShootAnimation(shootAction);
                    await new Promise(resolve => setTimeout(resolve, 2000));
                } else if (response.action.type === "USE") {
                    const useAction = response.action as UseActionResult;
                    if (useAction.usedBy == "Dealer") {
                        triggerOpponentUseAnimation(useAction);
                        await new Promise(resolve => setTimeout(resolve, 2000));
                    }
                    if ((useAction.item.toLowerCase() == 'magnifyingglass' && useAction.usedBy == player)
                        || useAction.item.toLowerCase() == 'beer') {
                        setUsedMessage([useAction.item, useAction.result]);
                        console.log(useMessage)
                        await new Promise(resolve => setTimeout(resolve, 2000));
                        setUsedMessage([]);
                    }
                } else if (response.action.type === "RELOAD") {
                    const reloadAction = response.action as ReloadActionResult;
                    await handleReloadAnimation(reloadAction);
                }

                setCurrentGameField(response.game);
                setGameState(response.game.state);
                console.log(response.game.state);
            }
        } catch (error) {
            console.log(error);
        } finally {
            const lastGameState = updatesList[updatesList.length - 1]?.game.state;
            if (lastGameState !== 'ROUND_ENDED') {
                setBlocked(false);
            }
        }
    };

    const handleReinit = async () => {
        if (sessionId == null) return;
        try {
            const response = await reinitRound(sessionId);
            setCurrentGameField(response.data.game);
            setGameState(response.data.game.state);
            console.log(response.data.action);
            await handleReloadAnimation(response.data.action as ReloadActionResult);
        } catch (error) {
            console.error("Reinit use failed", error);
        } finally {
            setBlocked(false);
        }
    };

    const triggerShootAnimation = (action: ShootActionResult) => {
        if (opponentAnimation !== ShootAnimation.NONE || playerAnimation !== ShootAnimation.NONE) {
            return;
        }

        if (action.shooterName === "Dealer") {
            setOpponentAnimation(action.selfShoot ? ShootAnimation.SELF_SHOOT : ShootAnimation.SHOOT);
            setTimeout(() => setOpponentAnimation(ShootAnimation.NONE), 2000);
        } else {
            setPlayerAnimation(action.selfShoot ? ShootAnimation.SELF_SHOOT : ShootAnimation.SHOOT);
            setTimeout(() => setPlayerAnimation(ShootAnimation.NONE), 2000);
        }

        if (action.success) {
            const shootTarget = action.selfShoot === (action.shooterName === 'Dealer') ? ShootIn.DEALER : ShootIn.PLAYER;
            setTimeout(() => {
                setShootIn(shootTarget);
                setTimeout(() => {
                    setShootIn(ShootIn.NONE);
                }, 1000);
            }, 1000);
        }
    };

    const triggerOpponentUseAnimation = (action: UseActionResult) => {
        if (action.result !== "USE_ITEM_FAILED") {
            setItemUsed(action.item);
            setTimeout(() => setItemUsed(''), 2000);
        } else {
            console.log("Item use animation failed");
        }
    };

    return (
        <>
            {(currentGameField && sessionId) ? (
                <>
                    <UpperBar score={currentGameField.score}
                              opponent={currentGameField.opponent} restartGame={startGame}
                              changeMenuState={changeMenuState}/>
                    <OpponentBar cuffed={currentGameField.opponent.cuffed} animation={opponentAnimation}
                                 isAlive={currentGameField.opponent.lifeCount !== 0}/>
                    <TableBar sessionId={sessionId}
                              opponent={currentGameField.opponent}
                              player={currentGameField.player}
                              gunSawed={currentGameField.gunSawed}
                              onGameUpdate={updateGameState}
                              usedByOpponent={itemUsed}
                              gunPresent={playerAnimation === ShootAnimation.NONE && opponentAnimation === ShootAnimation.NONE}/>
                    {blocked && <div className={"blocked-game"}>
                        {playerAnimation !== ShootAnimation.NONE &&
                            <ShootAnimationComponent key={`shoot-${playerAnimation}`}
                                                     playerAnimation={playerAnimation}/>}
                        {shootIn !== ShootIn.NONE &&
                            <BoomAnimationComponent key={`boom-${shootIn}`}
                                                    shootIn={shootIn}/>}
                        {reloaded.length > 0 &&
                            <ReloadMessageBar lives={reloaded[0]}
                                              blanks={reloaded[1]}/>}
                        {useMessage.length > 0 &&
                            <UseMessageBar item={useMessage[0]}
                                           result={useMessage[1]}/>}
                        {currentGameField.state === 'ROUND_ENDED' &&
                            <EndGameMessageBar continueGame={handleReinit}
                                               restartGame={startGame}
                                               exitToMenu={() => changeMenuState(MenuState.MENU)}
                                               score={currentGameField.score}
                                               playerName={player}
                                               playerWin={currentGameField.player.lifeCount > 0}/>}
                    </div>}
                </>
            ) : (
                <div>Game not found</div>
            )}
        </>
    );
}

export default BuckshotRoulette;