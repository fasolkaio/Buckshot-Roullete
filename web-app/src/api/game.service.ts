import gsApi from "./axios.config.ts";

export const initGame = (player:string) =>
    gsApi.post('buckshot-roulette/new', {
    name : player
});

export const shoot = (sessionId : string, self : boolean) =>
    gsApi.post('buckshot-roulette/shoot', {
    sessionId : sessionId,
    self : self
});

export const chooseItem = (sessionId : string, item : string) =>
    gsApi.post('buckshot-roulette/use', {
    sessionId : sessionId,
    item : item
});

export const reinitRound = (sessionId : string) =>
    gsApi.post('buckshot-roulette/reinit', {sessionId})
