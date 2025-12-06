package com.example.input.json;

import com.example.input.dto.json.CityFromJsonDto;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class CityJsonParser implements IJsonParser<List<CityFromJsonDto>> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public List<CityFromJsonDto> parse(InputStreamReader reader) {
        try {
            CityFromJsonDto[] cities = mapper.readValue(reader, CityFromJsonDto[].class);
            return Arrays.asList(cities);

        } catch (InvalidFormatException e) {
            throw new JsonValidationException("Неверный формат поля: "
                    + e.getPathReference() + " - " + e.getValue(), e);

        } catch (MismatchedInputException e) {
            throw new JsonValidationException("Структура JSON не совпадает с ожидаемой: "
                    + e.getPathReference(), e);

        } catch (JsonParseException e) {
            throw new JsonValidationException("JSON повреждён или синтаксически неверен", e);

        } catch (IOException e) {
            throw new JsonValidationException("Ошибка чтения файла JSON", e);
        }
    }

}
