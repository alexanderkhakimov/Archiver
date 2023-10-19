package org.myPetProject.command;

import org.myPetProject.ConsoleHelper;
import org.myPetProject.ZipFileManager;
import org.myPetProject.exception.PathsNotFoundException;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ZipCreateCommand extends ZipCommand {
    @Override
    public void execute() throws Exception {
        try {
            ConsoleHelper.writeMessage("Создание архива.");

            ZipFileManager zipFileManager = getZipFileManager();

            ConsoleHelper.writeMessage("Введите полное имя файла или директории для архивации:");
            Path sourcePath = Paths.get(ConsoleHelper.readString());
            zipFileManager.createZip(sourcePath);

            ConsoleHelper.writeMessage("Архив создан.");
        } catch (PathsNotFoundException e) {
            ConsoleHelper.writeMessage("Вы неверное указали имя файла или директории.");
        }
    }
}
