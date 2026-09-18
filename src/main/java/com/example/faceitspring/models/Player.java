package com.example.faceitspring.models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    String userName;
    @Enumerated(EnumType.STRING)
    Role role;
    @Enumerated(EnumType.ORDINAL)
    Level level;
    int elo;
    @JsonBackReference
    @ManyToOne
    Team team;

    public Player (String userName, Role role) {
        this.userName = userName;
        this.role = role;
        this.level = Level.LVL1;
        this.elo = 100;
    }
    public Player (String userName, Role role, Rank inGameRank) {
        this.userName = userName;
        this.role = role;
        this.resetElo(inGameRank);
    }

    public int resetElo(Rank inGameRank) {
        this.level = inGameRank.getLevel();
        this.elo = this.level.getBaseElo();
        return this.elo;
    }

    public void joinTeam(Team team)  {
        if (this.team == null) {
            this.team = team;
            team.addPlayer(this);
        } else {
            throw new IllegalArgumentException("You already have a team");
        }
    }

    public void leaveTeam(boolean kicked) {
        if (this.team == null) {
            throw new IllegalArgumentException("You don't have a team");
        } else {
            if (!kicked) {
                this.team.removePlayer(this, false);
            }
            this.team = null;
        }
    }

    public int updateElo(boolean positive, int eloB) {
        double expected = 1.0 / (1.0 + Math.pow(10.0, (eloB - this.elo) / 400.0));
        int score = positive ? 1 : 0;
        int K = 16;
        int newElo = (int) Math.round(this.elo + K * (score - expected));
        this.elo = newElo;
        this.level = Level.getLevel(this.elo);
        return newElo;
    }
}
