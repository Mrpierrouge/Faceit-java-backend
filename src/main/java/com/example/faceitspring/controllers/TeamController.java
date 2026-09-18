package com.example.faceitspring.controllers;

import com.example.faceitspring.DTO.TeamCreateRequestDTO;
import com.example.faceitspring.models.Team;
import com.example.faceitspring.services.PlayerService;
import com.example.faceitspring.services.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/team")
@RequiredArgsConstructor
public class TeamController {

    private final PlayerService playerService;
    private final TeamService teamService;


    @PostMapping("/create")
    public Team addTeam(@RequestBody TeamCreateRequestDTO request){
        var player = playerService.getById(request.playerId());
        return teamService.add(request.name(), player);
    }

    @GetMapping
    public List<Team> getTeams(){
        return teamService.getAllTeams();
    }

    @GetMapping("/{id}")
    public Team getTeamById(@PathVariable int id){
        return teamService.getById(id);
    }

    @DeleteMapping("/{id}/delete")
    public List<Team> deleteTeam(@PathVariable Integer id){
        return teamService.delete(id);
    }
}