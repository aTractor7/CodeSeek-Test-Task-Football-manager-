package com.example.footballmanager.controllers;

import com.example.footballmanager.dto.TeamCreateDto;
import com.example.footballmanager.dto.TeamDto;
import com.example.footballmanager.entity.Player;
import com.example.footballmanager.entity.Team;
import com.example.footballmanager.services.PlayerService;
import com.example.footballmanager.util.exceptions.ValidationException;
import com.example.footballmanager.services.TeamService;
import com.example.footballmanager.util.validation.TeamValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

import static com.example.footballmanager.util.ErrorUtils.generateFieldErrorMessage;
import static com.example.footballmanager.util.RequestUtils.getSavedLocation;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;
    private final ModelMapper modelMapper;
    private final TeamValidator validator;

    @PostMapping
    public ResponseEntity<TeamDto> create(@RequestBody @Valid TeamCreateDto teamCreateDto, BindingResult bindingResult) {
        validator.validate(teamCreateDto, bindingResult);

        if(bindingResult.hasErrors()) {
            throw new ValidationException(generateFieldErrorMessage(bindingResult.getFieldErrors()));
        }

        Team savedTeam = teamService.create(convertToTeam(teamCreateDto));

        return ResponseEntity
                .created(getSavedLocation(savedTeam.getId()))
                .body(convertToTeamDto(savedTeam));
    }

    @GetMapping
    public ResponseEntity<List<TeamDto>> findAll() {
        return ResponseEntity.ok(teamService.findAll().stream().map(this::convertToTeamDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(convertToTeamDto(teamService.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamDto> update(@PathVariable Long id, @RequestBody @Valid TeamCreateDto teamDto) {
        Team updatedTeam = convertToTeam(teamDto);
        return ResponseEntity.ok(convertToTeamDto(teamService.update(id, updatedTeam)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        teamService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Team deleted successfully"));
    }

    public Team convertToTeam(TeamCreateDto teamCreateDto) {
        Team team = modelMapper.map(teamCreateDto, Team.class);
        if (team.getPlayers() != null)
            team.getPlayers().forEach(p -> p.setId(null));
        return team;
    }

    public TeamDto convertToTeamDto(Team team) {
        return modelMapper.map(team, TeamDto.class);
    }
}

