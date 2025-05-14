export interface ServicesProps {
    game: string;
    player: string;
}

export interface Score{
    game:string,
    player:string,
    points:number,
    playedOn:Date
}

export interface Comment{
    game:string,
    player:string,
    comment:string,
    commentedOn: string
}