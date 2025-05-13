import {useState} from "react";
import {addScore} from "../../../../api/score.service.ts";

interface EndGameMessageProps{
    playerWin : boolean;
    playerName : string;
    score : number;
    exitToMenu : ()=>void;
    restartGame : () =>void;
    continueGame : ()=>void;
}

function EndGameMessageBar({playerWin, playerName, score, exitToMenu, restartGame, continueGame}:EndGameMessageProps){
    const [endGame, setEndGame] = useState(false);

    const writeScore = async() => {
        if(playerName != 'GUEST')
            await addScore('buckshot-roulette',playerName,score)
    }

    return <div className={"end-message-container"}>
        {!endGame ?
        <>
            {playerWin ? <>
                <h1>Double or nothing?</h1>
                <div className={"end-buttons-container"}>
                    <button onClick={()=>{
                        continueGame();
                    }} className={"end-button"}>Yes</button>
                    <button onClick={()=>setEndGame(true)} className={"end-button"}>No</button>
                </div>
            </>: <>
                <h1>Game over... See you next time!</h1>
                <div className={"end-buttons-container"}>
                    <button onClick={()=> {
                        exitToMenu();
                    }} className={"end-button"}>Exit</button>
                    <button onClick={()=> {
                        restartGame();
                    }} className={"end-button"}>Restart</button>
                </div>
            </>}
        </> : <>
                <h1>Congratulations!</h1>
                <h2>{`Final score: ${score}`}</h2>
                <div className={"end-buttons-container"}>
                    <button onClick={()=> {
                        writeScore();
                        exitToMenu();
                    }} className={"end-button"}>Exit</button>
                    <button onClick={()=> {
                        restartGame();
                    }} className={"end-button"}>Restart</button>
                </div>

            </>
        }

    </div>
}

export default  EndGameMessageBar