package com.csc340.assignment2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class RestApiController {

    /**
     * Hello World API endpoint.
     *
     * @return response string.
     */
    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

    /**
     * Get a list of Pokemon
     *
     * @return a list of Pokemon
     */
    @GetMapping("/pokemon")
    public Object getPokemon() {
        try{

            // Consuming a Restful Web Service (API)
            String url = "https://pokeapi.co/api/v2/pokemon";
            RestTemplate restTemplate = new RestTemplate();
            ObjectMapper mapper = new ObjectMapper();

            String jsonListResponse = restTemplate.getForObject(url, String.class);
            JsonNode root = mapper.readTree(jsonListResponse);

            List<Pokemon> pokemonList = new ArrayList<>();

            JsonNode results = root.get("results");

            for(JsonNode rt : results){
                String name = rt.get("name").asText();

                Pokemon pokemon = new Pokemon(name);
                pokemonList.add(pokemon);
            }
            return pokemonList;

        } catch (JsonProcessingException ex){
            Logger.getLogger(RestApiController.class.getName()).log(Level.SEVERE,
                    null, ex);
            return "error in /pokemon";
        }
    }

}
