package com.wefox.challenge.repository;

import com.wefox.challenge.model.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {

    Stream<Pokemon> findAllByNameStartsWith(String name);
}
