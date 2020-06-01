package com.wefox.challenge.model.dto;

import com.wefox.challenge.model.Pokemon;

import java.io.Serializable;

public class PokemonDto implements Serializable {

    private Long id;

    private String name;

    private Pokemon.Type type;

    public PokemonDto() {
    }

    public PokemonDto(Long id, String name, Pokemon.Type type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Pokemon.Type getType() {
        return type;
    }

    public void setType(Pokemon.Type type) {
        this.type = type;
    }
}
