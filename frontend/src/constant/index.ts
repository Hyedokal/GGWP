export const MAIN_PATH = '/';
export const AUTH_PATH = '/auth';
export const USER_PATH = `/user`;
export const MATCH_PATH= `/match`;




export const SEARCH_PATH:"/search" = '/search'

export const MY_PATH:"/mypage"='/mypage'


export const SUMMONER_PATH:"/summoner"='/summoner'

export const BOARD_NUMBER_PATH_VARIABLE = ':boardNumber';
export const DETAIL_PATH = (boardNumber: number | string) => `detail/${boardNumber}`;
export const UPDATE_PATH = (boardNumber: number | string) => `update/${boardNumber}`;
