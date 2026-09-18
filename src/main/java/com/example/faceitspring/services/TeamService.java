package com.example.faceitspring.services;

import com.example.faceitspring.models.Player;
import com.example.faceitspring.models.Team;
import com.example.faceitspring.repository.TeamRepository;
import com.example.faceitspring.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public Team add(String name, Player player){
        if (player.getTeam() == null) {
            var team = new Team(name, player);
            var saved = teamRepository.save(team);
            playerRepository.save(player);
            return saved;
        } else {
            throw new IllegalArgumentException("this player already has a team");
        }

    }
    public List<Team> getAllTeams(){
        return teamRepository.findAll();
    }

    public Team getById(Integer id) {
        return teamRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("team not found"));
    }

    public List<Team> delete(int id){
        teamRepository.delete(getById(id));
        return getAllTeams();
    }
}
