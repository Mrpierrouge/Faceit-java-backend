package com.example.faceitspring.controllers;

import com.example.faceitspring.DTO.MatchCreateRequestDTO;
import com.example.faceitspring.models.Match;
import com.example.faceitspring.services.MatchService;
import com.example.faceitspring.services.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/match")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;
    private final TeamService teamService;

    @PostMapping("/create")
    public Match addMatch(@RequestBody MatchCreateRequestDTO request) {
        return matchService.add(teamService.getById(request.team1()), teamService.getById(request.team2()));
    }

    @PostMapping("/{id}/play")
    public Match playMatch(@PathVariable Integer id, @RequestBody Integer winnerTeamId){
        var team = teamService.getById(winnerTeamId);
        matchService.play(id, team);
        return matchService.getById(id);
    }
    @GetMapping
    public List<Match> getMatchs(){
        return matchService.getAllMatches();
    }

    @GetMapping("/{id}")
    public Match getMatchById(@PathVariable int id) {
        return matchService.getById(id);
    }
}
