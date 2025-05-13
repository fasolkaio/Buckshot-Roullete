import {useEffect, useState} from "react";
import {Score, ServicesProps} from './services.interfaces.ts';
import {getTopScores} from "../../api/score.service.ts";



function TopScore({game, player}: ServicesProps) {
    const [scores, setScores] = useState<Score[] | null>(null);

    useEffect(() => {
        const fetchScores = async () => {
            try {
                const response = await getTopScores(game);
                setScores(response.data);
            } catch (error) {
                console.error("Failed to fetch top scores", error);
            }
        };
        fetchScores();
    }, [player, game]);

    return (
        <div className="top-score-container">
            <h1 className="title">Top Scores</h1>
            {scores ? (
                <ul className="score-list">
                    {scores.map((score, index) => (
                        <li
                            key={index}
                            className={`score-item ${score.player === player ? 'highlight' : ''}`}
                        >
                            <span className="player-name">{score.player}</span>
                            <span className="score-points">{score.points}</span>
                        </li>
                    ))}
                </ul>
            ) : (
                <p className="loading">Loading...</p>
            )}
        </div>
    );
}

export default TopScore;
