package com.example.input.json;

import com.example.input.dto.json.CityFromJsonDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class CityJsonParser implements IJsonParser<List<CityFromJsonDto>> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public List<CityFromJsonDto> parse(InputStreamReader reader) throws IOException {
        CityFromJsonDto[] cities = mapper.readValue(reader, CityFromJsonDto[].class);
        return Arrays.asList(cities);
    }

}
