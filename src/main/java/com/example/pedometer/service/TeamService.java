package com.example.pedometer.service;

import com.example.pedometer.DTO.TeamResponse;
import com.example.pedometer.model.Team;
import com.example.pedometer.repository.AppUserRepository;
import com.example.pedometer.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    private final AppUserRepository appUserRepository;

    public List<TeamResponse> getAll() {
        return teamRepository.findAll().stream()
                .map(Team::toResponse)
                .collect(Collectors.toList());
    }

    public TeamResponse addTeam(Team team) {
        validateTeam(team);
        return teamRepository.save(team)
                .toResponse();
    }

    public TeamResponse addUserToTeam(String userEmail, String teamName) {

        Team currentTeam = teamRepository.findByTeamName(teamName)
                .orElseThrow(() -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "no team found");
                });
        appUserRepository.findByEmail(userEmail)
                        .ifPresent(appUser -> {
                            currentTeam.getTeamMembers().add(appUser);
                            teamRepository.save(currentTeam);
                        });

        return currentTeam
                .toResponse();
    }

    private void validateTeam(Team team) {
        if (team.getTeamName() == null || team.getTeamName().isBlank()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "no team name found");
        }

        teamRepository.findByTeamName(team.getTeamName())
                .ifPresent(team1 -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "team name is taken");
                });

    }


}
