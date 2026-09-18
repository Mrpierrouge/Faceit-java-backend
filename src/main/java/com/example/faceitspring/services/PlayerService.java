package com.example.faceitspring.services;
import com.example.faceitspring.models.Player;
import com.example.faceitspring.models.Rank;
import com.example.faceitspring.models.Role;
import com.example.faceitspring.models.Team;
import com.example.faceitspring.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    public Player add(String userName, Role role, Rank inGameRank) {
        var player = new Player(userName, role, inGameRank);

        return playerRepository.save(player);
    }

    public List<Player> getAllPlayers(){
        return playerRepository.findAll();
    }

    public Player getById(Integer id){
        var player = playerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("player not found"));
        return player;
    }

    public Player leaveTeam(Integer playerId){
        var player = this.getById(playerId);
        player.leaveTeam(false);
        return player;
    }

    public Player joinTeam(Integer playerId, Team team){
        var player = this.getById(playerId);
        player.joinTeam(team);
        return player;
    }
}

