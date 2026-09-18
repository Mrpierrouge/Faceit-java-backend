package com.example.faceitspring.services;

import com.example.faceitspring.models.*;
import com.example.faceitspring.repository.MatchRepository;
import com.example.faceitspring.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;

    public Match add(Team team1, Team team2) {
        var match = new Match(team1, team2, new Date());

        return matchRepository.save(match);
    }
    public void play(Integer matchId, Team winner) {
        var match = this.getById(matchId);
        match.endMatch(winner);
    }
    public List<Match> getAllMatches(){
        return matchRepository.findAll();
    }

    public Match getById(int id){
        return matchRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("player not found"));
    }
}
