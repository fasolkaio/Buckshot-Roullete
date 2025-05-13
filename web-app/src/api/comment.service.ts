import gsApi from "./axios.config.ts";
import {formatDate} from "../components/utils/utils.ts";

export const getComments = (game : string) =>
    gsApi.get(`comment/${game}`);

export const addComment = (game:string, player:string, comment:string) =>
    gsApi.post('comment', {
        game:game,
        player:player,
        comment:comment,
        ratedOn : formatDate(new Date())
    });