package com.example.input.json;

import com.example.input.dto.CityInputRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class CityJsonParser implements JsonParser<List<CityInputRequestDto>> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public List<CityInputRequestDto> parse(InputStreamReader reader) throws IOException {
        CityInputRequestDto[] cities = mapper.readValue(reader, CityInputRequestDto[].class);
        return Arrays.asList(cities);
    }

}
