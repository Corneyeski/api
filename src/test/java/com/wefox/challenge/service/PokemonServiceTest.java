package com.wefox.challenge.service;

import com.wefox.challenge.model.Pokemon;
import com.wefox.challenge.model.dto.PokemonDto;
import com.wefox.challenge.repository.PokemonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration(exclude= SecurityAutoConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class PokemonServiceTest {

    @Autowired
    private PokemonService pokemonService;

    @Autowired
    private PokemonRepository pokemonRepository;

    private PokemonDto charmander;
    private PokemonDto squirtle;
    private PokemonDto bulbasaur;

    @Before
    public void setUp(){
        charmander = new PokemonDto(null, "charmander", Pokemon.Type.FIRE);
        squirtle = new PokemonDto(null, "squirtle", Pokemon.Type.WATER);
        bulbasaur = new PokemonDto(null, "bulbasaur", Pokemon.Type.GRASS);

        pokemonRepository.deleteAll();
    }

    @Test
    public void createPokemon(){
        charmander = pokemonService.createPokemon(charmander);
        assertNotNull(charmander.getId());
    }

    @Test
    public void searchPokemon_oneResult(){
        pokemonService.createPokemon(charmander);
        pokemonService.createPokemon(bulbasaur);

        assertEquals(pokemonService.searchPokemon("char").size(), 1);
    }

    @Test
    public void searchPokemon_empty(){
        pokemonService.createPokemon(charmander);
        pokemonService.createPokemon(bulbasaur);

        assertEquals(pokemonService.searchPokemon("squirtle").size(), 0);
    }
}
