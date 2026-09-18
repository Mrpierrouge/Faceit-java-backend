package com.example.faceitspring.services;
import com.example.faceitspring.models.Player;
import com.example.faceitspring.models.Rank;
import com.example.faceitspring.models.Role;
import com.example.faceitspring.models.Team;
import com.example.faceitspring.repository.PlayerRepository;
import com.example.faceitspring.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

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
        var team = player.getTeam();
        player.leaveTeam(false);
        var savedPlayer = playerRepository.save(player);
        if (team != null) {
            teamRepository.save(team);
        }
        return savedPlayer;
    }

    public Player joinTeam(Integer playerId, Team team){
        var player = this.getById(playerId);
        player.joinTeam(team);
        var savedPlayer = playerRepository.save(player);
        teamRepository.save(team);
        return savedPlayer;
    }

    public List<Player> delete(int id){
        playerRepository.delete(getById(id));
        return getAllPlayers();
    }
}

