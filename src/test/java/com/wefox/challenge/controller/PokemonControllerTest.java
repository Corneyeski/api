package com.wefox.challenge.controller;

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
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration(exclude= SecurityAutoConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PokemonControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

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
        ResponseEntity<Void> response = testRestTemplate.exchange("/pokemon",
                HttpMethod.POST, new HttpEntity<>(charmander), Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void searchPokemon_oneResult(){

        testRestTemplate.exchange("/pokemon",
                HttpMethod.POST, new HttpEntity<>(squirtle), Void.class);

        ResponseEntity<List<PokemonDto>> response = testRestTemplate.exchange("/pokemon/squirtle",
                HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<PokemonDto>>() {
                });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void searchPokemon_manyResults(){

        PokemonDto charmeleon = new PokemonDto(null, "charmeleon", Pokemon.Type.FIRE);
        PokemonDto charizard = new PokemonDto(null, "charizard", Pokemon.Type.FIRE);

        testRestTemplate.exchange("/pokemon",
                HttpMethod.POST, new HttpEntity<>(charmander), Void.class);
        testRestTemplate.exchange("/pokemon",
                HttpMethod.POST, new HttpEntity<>(charmeleon), Void.class);
        testRestTemplate.exchange("/pokemon",
                HttpMethod.POST, new HttpEntity<>(charizard), Void.class);

        ResponseEntity<List<PokemonDto>> response = testRestTemplate.exchange("/pokemon/char",
                HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<PokemonDto>>() {
                });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody().size());
    }

    @Test
    public void searchPokemon_noResults(){

        testRestTemplate.exchange("/pokemon",
                HttpMethod.POST, new HttpEntity<>(bulbasaur), Void.class);

        ResponseEntity<List<PokemonDto>> response = testRestTemplate.exchange("/pokemon/test",
                HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<PokemonDto>>() {
                });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

}
