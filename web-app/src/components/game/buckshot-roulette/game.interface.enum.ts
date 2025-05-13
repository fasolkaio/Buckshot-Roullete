export interface InitResponse{
    sessionId : string;
    game : GameField;
    actionResult : ReloadActionResult;
}

export interface ResultResponse {
    game : GameField;
    action : ActionResult;
}


export interface GameField {
    state : string;
    score : number;
    player : Player;
    opponent : Player;
    gunSawed : boolean;
}

export interface Player{
    lifeCount : number;
    maxLifeCount : number;
    items : [],
    cuffed : boolean;
}

export interface Gun{
    liveBulletsCount : number;
    bulletsCount : number;
}

export interface ActionResult{
    type : string;
}

export interface ReloadActionResult extends ActionResult{
    blanks : number;
    lives : number;
}

export interface  UseActionResult extends ActionResult{
    item : string;
    usedBy : string;
    result : string;
}

export interface ShootActionResult extends ActionResult{
    shooterName : string;
    selfShoot : boolean;
    success : boolean;
}

export enum ShootAnimation {
    NONE = "none",
    SHOOT = "shoot",
    SELF_SHOOT = "self shoot"
}

export enum ShootIn{
    NONE = 'none',
    PLAYER = 'player',
    DEALER = 'dealer'
}

export enum MenuState{
    MENU = 'menu',
    SOLO_GAME = 'solo game',
    MULTIPLAYER = 'multiplayer',
    SETTINGS = 'settings'
}


