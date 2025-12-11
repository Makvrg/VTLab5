package ru.ifmo.se.commands;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.ifmo.se.io.input.readers.Reader;
import ru.ifmo.se.io.output.print.Printer;
import ru.ifmo.se.service.CollectionService;
import ru.ifmo.se.service.exceptions.RemoveByIdIllegalStateException;
import ru.ifmo.se.validator.CommandValidator;
import ru.ifmo.se.validator.exceptions.RemoveByIdValidationException;

@RequiredArgsConstructor
public class RemoveByIdCommand implements Command {

    @Getter
    private final String commandSignature = "remove_by_id id";
    @Getter
    private final String commandDescription =
            "удалить элемент из коллекции по его id";

    private final CollectionService collectionService;
    private final CommandValidator commandValidator;
    private final Printer printer;

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
