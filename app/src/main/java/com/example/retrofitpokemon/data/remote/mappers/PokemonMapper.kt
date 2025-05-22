package com.example.retrofitpokemon.data.remote.mappers

import com.example.retrofitpokemon.data.remote.model.pokemondatabase.Ability
import com.example.retrofitpokemon.data.remote.model.pokemondatabase.AbilityX
import com.example.retrofitpokemon.data.remote.model.pokemondatabase.Animated
import com.example.retrofitpokemon.data.remote.model.pokemondatabase.BlackWhite
import com.example.retrofitpokemon.data.remote.model.pokemondatabase.Cries
import com.example.retrofitpokemon.data.remote.model.pokemondatabase.Crystal
import com.example.retrofitpokemon.data.remote.model.pokemondatabase.DiamondPearl
import com.example.retrofitpokemon.data.remote.model.pokemondatabase.DreamWorld
import com.example.retrofitpokemon.data.remote.model.pokemondatabase.Emerald
import com.example.retrofitpokemon.data.remote.model.pokemondatabase.FireredLeafgreen
import com.example.retrofitpokemon.data.remote.model.pokemondatabase.GenerationI
import com.example.retrofitpokemon.data.remote.model.pokemondatabase.GenerationIi
import com.example.retrofitpokemon.data.remote.model.pokemondatabase.GenerationIii
import com.example.retrofitpokemon.data.remote.model.pokemondatabase.GenerationIv
import com.example.retrofitpokemon.data.remote.model.pokemondatabase.GenerationV
import com.example.retrofitpokemon.data.remote.model.pokemondatabase.GenerationVi
import com.example.retrofitpokemon.data.remote.model.pokemondatabase.GenerationVii
import com.example.retrofitpokemon.data.remote.model.pokemondatabase.GenerationViii
import com.example.retrofitpokemon.data.remote.model.pokemondatabase.Gold
import com.example.retrofitpokemon.data.remote.model.pokemondatabase.HeartgoldSoulsilver
import com.example.retrofitpokemon.data.remote.model.pokemondatabase.Home
import com.example.retrofitpokemon.data.remote.model.pokemondatabase.Icons
import com.example.retrofitpokemon.data.remote.model.pokemondatabase.OfficialArtwork
import com.example.retrofitpokemon.data.remote.model.pokemondatabase.OmegarubyAlphasapphire
import com.example.retrofitpokemon.data.remote.model.pokemondatabase.Other
import com.example.retrofitpokemon.data.remote.model.pokemondatabase.Platinum
import com.example.retrofitpokemon.data.remote.model.pokemondatabase.PokemonDatabase
import com.example.retrofitpokemon.data.remote.model.pokemondatabase.RedBlue
import com.example.retrofitpokemon.data.remote.model.pokemondatabase.RubySapphire
import com.example.retrofitpokemon.data.remote.model.pokemondatabase.Showdown
import com.example.retrofitpokemon.data.remote.model.pokemondatabase.Silver
import com.example.retrofitpokemon.data.remote.model.pokemondatabase.Species
import com.example.retrofitpokemon.data.remote.model.pokemondatabase.Sprites
import com.example.retrofitpokemon.data.remote.model.pokemondatabase.Type
import com.example.retrofitpokemon.data.remote.model.pokemondatabase.TypeX
import com.example.retrofitpokemon.data.remote.model.pokemondatabase.UltraSunUltraMoon
import com.example.retrofitpokemon.data.remote.model.pokemondatabase.Versions
import com.example.retrofitpokemon.data.remote.model.pokemondatabase.XY
import com.example.retrofitpokemon.data.remote.model.pokemondatabase.Yellow
import com.example.retrofitpokemon.domain.model.Pokemon

object PokemonMapper {
    fun pokemonToPokemonDatabase(pokemon: Pokemon): PokemonDatabase {
        return PokemonDatabase(
            id = pokemon.id,
            name = pokemon.nombre,
            types = pokemon.tipos.mapIndexed { index, tipo ->
                Type(
                    slot = index + 1,
                    type = TypeX(name = tipo, url = "")
                )
            },
            abilities = pokemon.habilidades.mapIndexed { index, nombre ->
                Ability(
                    ability = AbilityX(name = nombre, url = ""),
                    is_hidden = false,
                    slot = index + 1
                )
            },

            base_experience = 0,
            cries = Cries(latest = "", legacy = ""),
            forms = emptyList(),
            game_indices = emptyList(),
            height = 0,
            held_items = emptyList(),
            is_default = true,
            location_area_encounters = "",
            moves = emptyList(),
            order = 0,
            past_abilities = emptyList(),
            past_types = emptyList(),
            species = Species(name = "", url = ""),
            sprites = Sprites(
                back_default = "",
                back_female = null,
                back_shiny = "",
                back_shiny_female = null,
                front_default = "",
                front_female = null,
                front_shiny = "",
                front_shiny_female = null,
                other = Other(
                    dream_world = DreamWorld("", null),
                    home = Home("", null, "", null),
                    officialArtwork = OfficialArtwork("", ""),
                    showdown = Showdown("", null, "", null, "", null, "", null)
                ),
                versions = Versions(
                    generationi = GenerationI(
                        RedBlue("", "", "", "", "", ""),
                        Yellow("", "", "", "", "", "")
                    ),
                    generationii = GenerationIi(
                        Crystal("", "", "", "", "", "", "", ""),
                        Gold("", "", "", "", ""),
                        Silver("", "", "", "", "")
                    ),
                    generationiii = GenerationIii(
                        Emerald("", ""),
                        FireredLeafgreen("", "", "", ""),
                        RubySapphire("", "", "", "")
                    ),
                    generationiv = GenerationIv(
                        DiamondPearl("", null, "", null, "", null, "", null),
                        HeartgoldSoulsilver("", null, "", null, "", null, "", null),
                        Platinum("", null, "", null, "", null, "", null)
                    ),
                    generationv = GenerationV(
                        BlackWhite(
                            Animated("", null, "", null, "", null, "", null),
                            "",
                            null,
                            "",
                            null,
                            "",
                            null,
                            "",
                            null
                        )
                    ),
                    generationvi = GenerationVi(
                        OmegarubyAlphasapphire("", null, "", null),
                        XY("", null, "", null)
                    ),
                    generationvii = GenerationVii(
                        icons = Icons("", null),
                        ultraSunUltraMoon = UltraSunUltraMoon("", null, "", null)
                    ),
                    generationviii = GenerationViii(
                        icons = Icons("", null)
                    )
                )
            ),
            stats = emptyList(),
            weight = 0
        )
    }

    fun pokemonDatabaseToPokemon(pokemonDatabase: PokemonDatabase): Pokemon {
        return Pokemon(
            id = pokemonDatabase.id,
            nombre = pokemonDatabase.name,
            tipos = pokemonDatabase.types.map {
                it.type.name
            },
            habilidades = pokemonDatabase.abilities.map {
                it.ability.name
            },
            urlFoto = pokemonDatabase.sprites.front_default
        )
    }
}