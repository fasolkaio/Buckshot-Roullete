import gsApi from "./axios.config.ts";
import {formatDate} from "../components/utils/utils.ts";

export const addScore = (game: string, player:string, score:number) =>
    gsApi.post('score', {
        game : game,
        player : player,
        points : score,
        playedOn : formatDate(new Date())
    });

export const getTopScores = (game : string)=>
    gsApi.get(`score/${game}`)
