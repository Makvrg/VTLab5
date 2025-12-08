package ru.ifmo.se.commands;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.ifmo.se.io.input.readers.Reader;
import ru.ifmo.se.io.output.Printer;
import ru.ifmo.se.service.CollectionService;

@RequiredArgsConstructor
public class ShowCommand implements Command {

    @Getter
    private final String commandSignature = "show";
    @Getter
    private final String commandDescription =
            "вывести в стандартный поток вывода все элементы "
                    + "коллекции в строковом представлении";

    private final CollectionService collectionService;
    private final Printer printer;

    @Override
    public void execute(String[] ignoredArgs, Reader ignoredReader) {
        printer.forcePrint(collectionService.show());
    }

}
