package ru.ifmo.se.io.output.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import ru.ifmo.se.entity.City;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class CityJsonWriter implements JsonWriter<List<City>> {

    private final ObjectMapper mapper = new ObjectMapper().enable(
            SerializationFeature.INDENT_OUTPUT
    );
    private final String backupFileName;

    @Override
    public void write(String fileName, List<City> cities)
            throws IOException {
        File file = new File(fileName);
        file.createNewFile();
        mapper.writeValue(file, cities);
    }

    @Override
    public void writeBackup(List<City> cities)
            throws IOException {
        write(backupFileName, cities);
    }

}
