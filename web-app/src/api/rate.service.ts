import gsApi from "./axios.config.ts";
import {formatDate} from "../components/utils/utils.ts";

export const setRating = (game:string, player:string, rating:number) =>
    gsApi.put('rating', {
        game : game,
        player : player,
        rating : rating,
        ratedOn : formatDate(new Date())
    });

export const getAverageRating = (game: string)=>
    gsApi.get(`rating/${game}`);

export const getRating = (game:string, player:string)=>
    gsApi.get(`rating/${game}/${player}`);