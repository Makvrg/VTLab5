package com.example.input.json;

import com.example.input.dto.CityRawRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class CityJsonParser implements JsonParser<List<CityRawRequestDto>> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public List<CityRawRequestDto> parse(InputStreamReader reader) throws IOException {
        CityRawRequestDto[] cities = mapper.readValue(reader, CityRawRequestDto[].class);
        return Arrays.asList(cities);
    }

}
