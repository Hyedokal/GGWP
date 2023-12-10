import ResponseDto from "../index";

export default interface SquadResponseDto extends ResponseDto {
    myPos: string;
    wantPos: string;
    useMic: boolean;
    summonerName: string;
    tagLine: string | null;
    rank: string;
    memo: string;
    commentList: any[] | null;
    updatedAt: string;
    qType: string;
    sId: number;
}