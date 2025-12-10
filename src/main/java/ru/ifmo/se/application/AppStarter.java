package ru.ifmo.se.application;

import java.io.IOException;

public class AppStarter implements Starter {

    private final AppCompositionRoot appCompositionRoot = new AppCompositionRoot();

    @Override
    public void start() {
        appCompositionRoot.getCollectionService()
                          .addShutdownListener(
                                  appCompositionRoot.getCommandInput()
                          );
        try {
            appCompositionRoot.getCommandInput().initialize();
        } catch (IOException e) {
            appCompositionRoot.getPrinter().forcePrintln(
                    "Произошла ошибка открытия файла при инициализации коллекции: " + e.getMessage()
            );
        }
        appCompositionRoot.getCommandInput().run();
    }

}
