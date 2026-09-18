package com.example.faceitspring.services;

import com.example.faceitspring.models.*;
import com.example.faceitspring.repository.MatchRepository;
import com.example.faceitspring.repository.TeamRepository;
import com.example.faceitspring.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public Match add(Team team1, Team team2) {
        var match = new Match(team1, team2, new Date());

        return matchRepository.save(match);
    }
    @Transactional
    public void play(Integer matchId, Team winner) {
        var match = this.getById(matchId);
        match.endMatch(winner);
        matchRepository.save(match);
        teamRepository.save(match.getTeam1());
        teamRepository.save(match.getTeam2());
        match.getTeam1().getPlayers().forEach(playerRepository::save);
        match.getTeam2().getPlayers().forEach(playerRepository::save);
    }
    public List<Match> getAllMatches(){
        return matchRepository.findAll();
    }

    public Match getById(int id){
        return matchRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("player not found"));
    }
    public List<Match> deleteMatch(int id){
        matchRepository.delete(getById(id));
        return getAllMatches();
    }
}
