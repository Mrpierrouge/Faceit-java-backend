package com.example.faceitspring.services;

import com.example.faceitspring.models.Player;
import com.example.faceitspring.models.Team;
import com.example.faceitspring.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    public Team add(String name, Player player){
        if (player.getTeam() == null) {
            var team = new Team(name, player);
            return teamRepository.save(team);
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
}
