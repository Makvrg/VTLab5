package ru.ifmo.se.commands;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.ifmo.se.io.input.env.EnvironmentProvider;
import ru.ifmo.se.io.input.readers.Reader;
import ru.ifmo.se.io.output.json.CityJsonWriter;
import ru.ifmo.se.io.output.print.Messages;
import ru.ifmo.se.io.output.print.Printer;
import ru.ifmo.se.service.CollectionService;

import java.io.IOException;

@RequiredArgsConstructor
public class SaveCommand implements Command {

    @Getter
    private final String commandSignature = "save";
    @Getter
    private final String commandDescription = "сохранить коллекцию в файл";

    private final CollectionService collectionService;
    private final Printer printer;
    private final EnvironmentProvider environmentProvider;
    private final CityJsonWriter fileWriter;

    @Override
    public void execute(String[] ignoredArgs, Reader ignoredReader) {
        String fileName = environmentProvider.getFileName();
        try {
            if (fileName == null) {
                fileWriter.writeBackup(collectionService.getCitiesForSave());
            } else {
                fileWriter.write(fileName, collectionService.getCitiesForSave());
            }
            printer.forcePrintln("Сохранение коллекции прошло успешно");
        } catch (IOException e) {
            printer.forcePrintln(Messages.SAVE_COLLECTION_EXC);
        }
    }
}
