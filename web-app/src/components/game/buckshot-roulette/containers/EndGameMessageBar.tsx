import {useState} from "react";

interface EndGameMessageProps{
    playerWin : boolean;
    score : number;
    exitToMenu : ()=>void;
    restartGame : () =>void;
    continueGame : ()=>void;
}

function EndGameMessageBar({playerWin, score, exitToMenu, restartGame, continueGame}:EndGameMessageProps){
    const [endGame, setEndGame] = useState(false);

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