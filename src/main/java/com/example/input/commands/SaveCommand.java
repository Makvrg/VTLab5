package com.example.input.commands;

import com.example.input.env.IEnvironmentProvider;
import com.example.input.readers.IReader;
import com.example.output.IPrinter;
import com.example.output.dto.CityForJsonDto;
import com.example.output.json.IJsonWriter;
import com.example.service.CollectionService;
import lombok.Getter;

import java.io.IOException;
import java.util.List;

public class SaveCommand implements ICommand {

    @Getter
    private final String commandSignature = "save";
    @Getter
    private final String commandDescription = "сохранить коллекцию в файл";

    private final CollectionService collectionService;
    private final IPrinter printer;
    private final IEnvironmentProvider environmentProvider;
    private final IJsonWriter<List<CityForJsonDto>> fileWriter;

    public SaveCommand(CollectionService collectionService,
                       IPrinter printer,
                       IEnvironmentProvider environmentProvider,
                       IJsonWriter<List<CityForJsonDto>> fileWriter) {
        this.collectionService = collectionService;
        this.printer = printer;
        this.environmentProvider = environmentProvider;
        this.fileWriter = fileWriter;
    }

    @Override
    public void execute(String[] ignoredArgs, IReader ignoredReader) {
        String fileName = environmentProvider.getFileName();
        try {
            fileWriter.write(fileName, collectionService.getCitiesForSave());
            printer.forcePrintln("Сохранение коллекции прошло успешно");
        } catch (IOException | NullPointerException e) {
            printer.forcePrintln(
                    "При сохранении коллекции возникла ошибка, возможно, "
                            + "файл не найден или к нему нет прав:");
            printer.forcePrintln(e.getMessage());
        }
    }

}
