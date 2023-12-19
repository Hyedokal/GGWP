export interface BoardListResponseDto {
    myPos: string;
    wantPos: string;
    useMic: boolean;
    summonerName: string;
    tagLine: string | null;
    rank: string;
    memo: string;
    commentList: any[] | null;
    updatedAt: string;
    qtype: string;
    sid: number;
}