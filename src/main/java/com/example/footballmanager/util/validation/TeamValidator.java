package com.example.footballmanager.util.validation;

import com.example.footballmanager.dto.TeamCreateDto;
import com.example.footballmanager.repositories.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

@Component
@RequiredArgsConstructor
public class TeamValidator {

    private final TeamRepository teamRepository;

    public boolean creationValidate(TeamCreateDto teamCreateDto, Errors bindingResult) {
        if(teamCreateDto.getName() == null || teamCreateDto.getName().isBlank())
            return false;

        if(teamRepository.existsByName(teamCreateDto.getName())) {
            bindingResult.rejectValue("name", "400", "Team name already exists");
            return false;
        }

        return true;
    }

    public boolean updateValidate(Long id, TeamCreateDto teamCreateDto, Errors bindingResult) {
        if(teamCreateDto.getName() == null || teamCreateDto.getName().isBlank())
            return false;

        if(teamRepository.existsByNameAndIdNot(teamCreateDto.getName(), id)) {
            bindingResult.rejectValue("name", "400", "Team name already exists");
            return false;
        }

        return true;
    }
}
