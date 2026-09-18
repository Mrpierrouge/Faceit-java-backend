package com.example.faceitspring.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    Status status;
    Date date;
    @ManyToOne
    private Team team1;

    @ManyToOne
    private Team team2;

    public Match(Team team1, Team team2, Date date) {
        this.date = date;
        this.team1 = team1;
        this.team2 = team2;
        this.status = Status.INCOMING;
    }
    public Team getOtherTeam(Team team) {
        if (team.equals(team1)) {
            return team2;
        }

        if (team.equals(team2)) {
            return team1;
        }

        return null;
    }
    public void endMatch(Team winnerTeam) {
        winnerTeam.playedMatch(true, getOtherTeam(winnerTeam));
        getOtherTeam(winnerTeam).playedMatch(false, winnerTeam);
        this.team1.syncAverageElo();
        this.team2.syncAverageElo();
        this.status = Status.DONE;
    }
}
