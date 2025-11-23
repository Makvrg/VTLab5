package com.example.input.commands;

import com.example.service.CollectionService;
import com.example.service.RemoveByIdIllegalStateException;
import com.example.validator.CommandValidator;
import com.example.validator.exceptions.RemoveByIdValidationException;

public class RemoveByIdCommand implements ICommand {

    private final CollectionService collectionService;
    private final CommandValidator commandValidator;
    private final String id;

    public RemoveByIdCommand(
            CollectionService collectionService,
            CommandValidator commandValidator,
            String[] args) {
        this.collectionService = collectionService;
        this.commandValidator = commandValidator;
        id = (args.length > 1) ? args[1] : null;
    }

    @Override
    public void execute() {
        try {
            commandValidator.validateRemoveById(id);
            try {
                if (collectionService.removeById(Long.valueOf(id))) {
                    System.out.println(
                            "Объект City успешно удалён из коллекции по заданному id");
                } else {
                    System.out.println(
                            "Объект City с заданным id не найден в коллекции");
                }
            } catch (RemoveByIdIllegalStateException e) {
                System.out.println(
                        "Объект не удалён, так как произошла ошибка во время работы: "
                        + e.getMessage()
                );
            }
        } catch (RemoveByIdValidationException e) {
            System.out.println(e.getMessage());
        }
    }

}
