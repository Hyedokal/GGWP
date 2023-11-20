package com.ggwp.searchservice.match.domain;

import com.ggwp.searchservice.enums.TeamColors;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Team {

    @Id
    private int teamId; // teamId 100 = BLUE 200 = RED

    private boolean win; // 승리

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match; // match랑 연관관계

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Participant> participants = new ArrayList<>(); // 참가자들 5명 씩 팀 1, 팀 2

    public void setMatch(Match match) {
        this.match = match;
    }

    public void addParticipant(Participant participant) {
        if (this.participants == null) {
            this.participants = new ArrayList<>();
        }
        this.participants.add(participant);
    }
}
