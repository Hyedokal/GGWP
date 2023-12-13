export interface BoardListResponseDto {
    myPos: string;
    wantPos: string;
    useMic: boolean;
    summonerName: string;
    tag_line: string | null;
    rank: string;
    memo: string;
    commentList: any[] | null;
    updatedAt: string;
    qtype: string;
    sid: number;
}