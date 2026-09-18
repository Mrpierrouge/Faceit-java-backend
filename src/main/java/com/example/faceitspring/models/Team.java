package com.example.faceitspring.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    String name;

    @OneToMany(mappedBy = "team")
    @JsonManagedReference
    private List<Player> players = new ArrayList<>();

    private int averageElo;
    int winCount;
    int loseCount;

    public Team(String name, Player player) {
        this.name = name;
        player.joinTeam(this);
    }

    public List<Player> addPlayer(Player newPlayer){
        this.players.add(newPlayer);
        this.syncAverageElo();
        return this.players;
    }

    public List<Player> removePlayer(Player removedPlayer, boolean kick) {
        this.players.remove(removedPlayer);
        if (kick) {
            removedPlayer.leaveTeam(true);
        }
        this.syncAverageElo();
        return this.players;
    }

    public void playedMatch(boolean win, Team ennemyTeam){
        if (win) {
            winCount++;
        } else {
            loseCount++;
        }
        this.players.forEach(player -> player.updateElo(win, ennemyTeam.averageElo));
    }
    public void syncAverageElo() {
        int totalElo = 0;
        if (players == null || players.isEmpty()) {
            this.averageElo = 0;
            return;
        }

        for (Player player : players) {
            totalElo += player.getElo();
        }

        this.averageElo = totalElo / players.size();
    }
}

