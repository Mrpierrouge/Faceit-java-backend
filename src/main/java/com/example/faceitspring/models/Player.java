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
        var difference = 1 / (1 + 10^((eloB - this.elo) / 400));
        var newElo = this.elo + 16 * (positive ? 1 : 0) - difference;

        return newElo;
    }
}
