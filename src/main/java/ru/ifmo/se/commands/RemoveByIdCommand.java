package ru.ifmo.se.commands;

import ru.ifmo.se.io.input.readers.Reader;
import ru.ifmo.se.io.output.print.Printer;
import ru.ifmo.se.service.CollectionService;
import ru.ifmo.se.service.exceptions.RemoveByIdIllegalStateException;
import ru.ifmo.se.validator.CommandValidator;
import ru.ifmo.se.validator.exceptions.RemoveByIdValidationException;

public class RemoveByIdCommand extends Command {

    private final CollectionService collectionService;
    private final CommandValidator commandValidator;
    private final Printer printer;

    public RemoveByIdCommand(CollectionService collectionService,
                             CommandValidator commandValidator,
                             Printer printer) {
        super("remove_by_id id", "удалить элемент из коллекции по его id");
        this.collectionService = collectionService;
        this.commandValidator = commandValidator;
        this.printer = printer;
    }

    @Override
    public void execute(String[] inputArgs, Reader ignoredReader) {
        String id = (inputArgs.length > 1) ? inputArgs[1] : null;
        try {
            commandValidator.validateRemoveById(id);
            try {
                if (collectionService.removeById(Long.valueOf(id))) {
                    printer.printlnIfOn(
                            "Объект City успешно удалён из коллекции по заданному id");
                } else {
                    printer.printlnIfOn(
                            "Объект City с заданным id не найден в коллекции");
                }
            } catch (RemoveByIdIllegalStateException e) {
                printer.printlnIfOn(
                        "Объект не удалён, так как произошла ошибка во время работы: "
                        + e.getMessage()
                );
            }
        } catch (RemoveByIdValidationException e) {
            printer.printlnIfOn(e.getMessage());
        }
    }
}
