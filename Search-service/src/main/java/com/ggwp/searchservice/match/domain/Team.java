package com.ggwp.searchservice.match.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_pk", nullable = false, unique = true)
    private Long teamPk;

    @Column(nullable = false)
    private int teamId; // teamId 100 = BLUE 200 = RED
    @Column(nullable = false)
    private boolean win; // 승리

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match; // match와 연관관계

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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
