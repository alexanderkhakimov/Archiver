package org.myPetProject.command;

import org.myPetProject.ConsoleHelper;
import org.myPetProject.ZipFileManager;

import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class ZipCommand implements Command {
    ZipFileManager getZipFileManager()throws Exception{
        ConsoleHelper.writeMessage("Введите полный путь файла архива.");
        Path zipPath = Paths.get(ConsoleHelper.readString());
        return new ZipFileManager(zipPath);
    }
}
