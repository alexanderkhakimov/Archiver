package org.myPetProject.command;

import org.myPetProject.ConsoleHelper;
import org.myPetProject.FileProperties;
import org.myPetProject.ZipFileManager;
import org.myPetProject.exception.PathsNotFoundException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ZipContentCommand extends ZipCommand{
    @Override
    public void execute() throws Exception {

            ConsoleHelper.writeMessage("Просмотр содержимого архива.");

            ZipFileManager zipFileManager = getZipFileManager();

            ConsoleHelper.writeMessage("Содержимое архива:");

            List<FileProperties> files = zipFileManager.getFilesList();


            for(FileProperties file : files){
                ConsoleHelper.writeMessage(file.toString());
            }

            ConsoleHelper.writeMessage("Содержимое архива прочитано.");

    }
}
