package com.wefox.challenge.controller;

import com.wefox.challenge.model.dto.PokemonDto;
import com.wefox.challenge.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pokemon")
public class PokemonController {

    private final PokemonService pokemonService;

    @Autowired
    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping("/{search}")
    public List<PokemonDto> searchPokemon(@PathVariable String search){
        return pokemonService.searchPokemon(search);
    }

    @PostMapping
    public PokemonDto createPokemon(@RequestBody PokemonDto pokemonDto){
        return pokemonService.createPokemon(pokemonDto);
    }
}
