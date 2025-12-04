package com.example.output.json;

import com.example.output.dto.CityForJsonDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CityJsonWriter implements IJsonWriter<List<CityForJsonDto>> {

    private final ObjectMapper mapper;

    public CityJsonWriter() {
        this.mapper = new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public void write(String fileName, List<CityForJsonDto> cities) throws IOException {
        mapper.writeValue(new File(fileName), cities);
    }

}
