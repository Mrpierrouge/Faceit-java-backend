package com.example.faceitspring.controllers;

import com.example.faceitspring.DTO.PlayerCreateRequestDTO;
import com.example.faceitspring.models.Player;
import com.example.faceitspring.models.Rank;
import com.example.faceitspring.models.Role;
import com.example.faceitspring.services.PlayerService;
import com.example.faceitspring.services.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/player")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;
    private final TeamService teamService;

    @PostMapping("/create")
    public Player addPlayer(@RequestBody PlayerCreateRequestDTO request){
        return playerService.add(request.userName(), Role.fromString(request.role()), Rank.fromString(request.inGameRank()));
    }
    @PostMapping("/create/default")
    public Player addDefaultPlayer(@RequestBody String userName) {
        return playerService.add(userName, Role.DPS, Rank.BRONZE);
    }
    @PostMapping("/{id}/team/leave")
    public Player leaveTeam(@PathVariable int id){
        return playerService.leaveTeam(id);
    }
    @PostMapping("/{id}/team/join")
    public Player joinTeam(@PathVariable int id, @RequestBody int teamId){
        var team = teamService.getById(teamId);
        return playerService.joinTeam(id, team);
    }
    @PostMapping("/{id}/resetelo")
    public Player resetElo(@PathVariable int id, @RequestBody String inGameRank) {
        var player = playerService.getById(id);
        player.resetElo(Rank.fromString(inGameRank));
        return player;
    }
    @GetMapping
    public List<Player> getPlayers(){
        return playerService.getAllPlayers();
    }

    @GetMapping("/{id}")
    public Player getPlayerById(@PathVariable int id){
        return playerService.getById(id);
    }

    @DeleteMapping("/{id}/delete")
    public List<Player> deletePlayer(@PathVariable Integer id){
        return playerService.delete(id);
    }
}
