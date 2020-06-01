package com.wefox.challenge.service;

import com.wefox.challenge.model.Pokemon;
import com.wefox.challenge.model.dto.PokemonDto;
import com.wefox.challenge.repository.PokemonRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PokemonService {

    private final PokemonRepository pokemonRepository;

    public PokemonService(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    public List<PokemonDto> searchPokemon(String search){
        return pokemonRepository.findAllByNameStartsWith(search).map(pokemon -> {
            PokemonDto dto = new PokemonDto();
            BeanUtils.copyProperties(pokemon, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    public PokemonDto createPokemon(PokemonDto pokemonDto){
        Pokemon pokemon = new Pokemon();
        BeanUtils.copyProperties(pokemonDto, pokemon);

        pokemonRepository.save(pokemon);
        BeanUtils.copyProperties(pokemon, pokemonDto);

        return pokemonDto;
    }
}
