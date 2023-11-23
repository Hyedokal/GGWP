package com.ggwp.squadservice.dto;

import com.ggwp.squadservice.domain.Comment;
import com.ggwp.squadservice.domain.Squad;
import com.ggwp.squadservice.enums.Position;
import com.ggwp.squadservice.enums.QType;
import lombok.*;

import java.util.List;

@Getter @Setter @AllArgsConstructor
@NoArgsConstructor @Builder @ToString
public class ResponseFindSquadDto {
    private Long sId;
    private Position myPos;
    private Position wantPos;
    private QType qType;
    private Boolean sMic;
    private String sMemo;
    private List<Comment> commentList;

    public ResponseFindSquadDto(Squad squad){
        this.sId = squad.getSId();
        this.myPos = squad.getMyPos();
        this.wantPos = squad.getWantPos();
        this.qType = squad.getQType();
        this.sMic = squad.getSMic();
        this.sMemo = squad.getSMemo();
    }
}
