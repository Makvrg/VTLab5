package ru.ifmo.se.io.output.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ru.ifmo.se.io.output.dto.CityForJsonDto;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CityJsonWriter implements JsonWriter<List<CityForJsonDto>> {

    private final ObjectMapper mapper = new ObjectMapper().enable(
            SerializationFeature.INDENT_OUTPUT
    );
    private final String backupFileName = "";

    @Override
    public void write(String fileName, List<CityForJsonDto> cities) throws IOException {
        if (fileName != null) {
            mapper.writeValue(new File(fileName), cities);
        } else {
            // TODO сделать исключение или создание запасного файла и запись в него
        }
    }

}
