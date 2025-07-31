package com.example.footballmanager.controllers;

import com.example.footballmanager.dto.PlayerCreateDto;
import com.example.footballmanager.dto.PlayerDto;
import com.example.footballmanager.entity.Player;
import com.example.footballmanager.entity.Team;
import com.example.footballmanager.services.PlayerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.example.footballmanager.util.RequestUtils.getSavedLocation;

@RestController
@RequestMapping("/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<PlayerDto> create(@RequestBody PlayerCreateDto playerDto) {
        Player savedPlayer = playerService.create(convertToPlayer(playerDto));

        return ResponseEntity
                .created(getSavedLocation(savedPlayer.getId()))
                .body(convertToPlayerDto(savedPlayer));
    }

    @GetMapping
    public ResponseEntity<List<PlayerDto>> findAll() {
        return ResponseEntity.ok(playerService.findAll().stream().map(this::convertToPlayerDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(convertToPlayerDto(playerService.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlayerDto> update(@PathVariable Long id, @RequestBody PlayerCreateDto playerDto) {
        Player updatedPlayer = convertToPlayer(playerDto);
        return ResponseEntity.ok(convertToPlayerDto(playerService.update(id, updatedPlayer)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        playerService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Player deleted successfully"));
    }

    private Player convertToPlayer(PlayerCreateDto playerCreateDto) {
        Player player = modelMapper.map(playerCreateDto, Player.class);
        player.setId(null);
        return player;
    }

    private PlayerDto convertToPlayerDto(Player player) {
        return modelMapper.map(player, PlayerDto.class);
    }
}

