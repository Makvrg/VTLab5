package com.example.input;

import com.example.input.commands.*;
import com.example.input.env.IEnvironmentProvider;
import com.example.input.readers.IReader;
import com.example.output.IPrinter;
import com.example.output.dto.CityForJsonDto;
import com.example.output.json.IJsonWriter;
import com.example.service.CollectionService;
import com.example.typer.DataTyper;
import com.example.validator.CommandValidator;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CommandDistributor {

    private final CollectionService collectionService;
    private final CommandValidator commandValidator;
    private final DataTyper dataTyper;
    private final List<IReader> collectionInputReaders;
    private final IPrinter printer;
    private final IEnvironmentProvider environmentProvider;
    private final IJsonWriter<List<CityForJsonDto>> fileWriter;
    private final Map<String, ICommand> commands;

    public CommandDistributor(CollectionService collectionService,
                              CommandValidator commandValidator,
                              DataTyper dataTyper,
                              List<IReader> readers,
                              IPrinter printer,
                              IEnvironmentProvider environmentProvider,
                              IJsonWriter<List<CityForJsonDto>> fileWriter) {
        this.collectionService = collectionService;
        this.commandValidator = commandValidator;
        this.dataTyper = dataTyper;
        this.collectionInputReaders = readers;
        this.printer = printer;
        this.environmentProvider = environmentProvider;
        this.fileWriter = fileWriter;
        commands = buildMapOfCommands();
    }

    public void distribute(String[] inputArgs) {
        if (commands.containsKey(inputArgs[0])) {
            commands.get(inputArgs[0])
                    .execute(inputArgs, collectionInputReaders.getLast());
        } else {
            commands.get("unknown")
                    .execute(inputArgs, null);
        }
    }

    private Map<String, ICommand> buildMapOfCommands() {
        Map<String, ICommand> commands = new LinkedHashMap<>();

        commands.put("unknown", new UnknownCommand(printer));
        commands.put("help", new HelpCommand(
                printer,
                commands.values()
                )
        );
        commands.put("exit", new ExitCommand(collectionService, printer));
        commands.put("info", new InfoCommand(collectionService, printer));
        commands.put("add", new AddCityCommand(
                collectionService,
                commandValidator,
                dataTyper,
                printer
                )
        );
        commands.put("show", new ShowCommand(collectionService, printer));
        commands.put("remove_by_id", new RemoveByIdCommand(
                collectionService,
                commandValidator,
                printer
                )
        );
        commands.put("clear", new ClearCommand(collectionService, printer));
        commands.put("save", new SaveCommand(
                collectionService,
                printer,
                environmentProvider,
                fileWriter
                )
        );
        commands.put("head", new HeadCommand(collectionService, printer));
        commands.put("remove_head", new RemoveHeadCommand(
                collectionService,
                printer
                )
        );
        commands.put("add_if_max", new AddIfMaxCityCommand(
                collectionService,
                commandValidator,
                dataTyper,
                printer
                )
        );
        commands.put("remove_all_by_population_density",
                new RemoveAllByPopulationDensityCommand(
                        collectionService,
                        commandValidator,
                        printer
                )
        );
        commands.put("filter_less_than_population_density",
                new FilterLessThanPopulationDensityCommand(
                        collectionService,
                        commandValidator,
                        printer
                )
        );
        commands.put("print_field_descending_government",
                new PrintFieldDescendingGovernmentCommand(
                        collectionService,
                        printer
                )
        );
        commands.put("update", new UpdateByIdCityCommand(
                collectionService,
                commandValidator,
                dataTyper,
                printer
                )
        );
        commands.put("execute_script", new ExecuteScriptCommand(
                commandValidator,
                collectionInputReaders,
                printer
                )
        );
        return commands;
    }

}
