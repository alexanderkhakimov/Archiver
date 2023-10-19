package org.myPetProject;
import org.myPetProject.exception.PathsNotFoundException;
import org.myPetProject.exception.WrongZipFileException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipFileManager {
    private Path zipFile;

    public ZipFileManager(Path zipFile) {
        this.zipFile = zipFile;
    }

    public void createZip(Path source)throws Exception{
        Path zipDirectory = zipFile.getParent();
        if(Files.notExists(zipDirectory)){
            Files.createDirectories(zipDirectory);
        }

        try(ZipOutputStream zipOutputStream =new ZipOutputStream(Files.newOutputStream(zipFile))){
            if(Files.isDirectory(source)){
                FileManager fileManager = new FileManager(source);
                List<Path> fileNames = fileManager.getFileList();

                for(Path fileName :fileNames){
                    addNewZopEntry(zipOutputStream,source,fileName);
                }
            }else if(Files.isRegularFile(source)){
                addNewZopEntry(zipOutputStream,source.getParent(),source.getFileName());
            }else {
                throw new PathsNotFoundException();
            }
        }
    }

    public  List<FileProperties> getFilesList () throws Exception{
        if(!Files.isRegularFile(zipFile)){
            throw new WrongZipFileException();
        }

        List<FileProperties> files = new ArrayList<>();

        try(ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipFile))){
            ZipEntry zipEntry = zipInputStream.getNextEntry();

            while (zipEntry !=null){
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                copyData(zipInputStream,baos);

                FileProperties file = new FileProperties(zipEntry.getName(),zipEntry.getSize(), zipEntry.getCompressedSize(), zipEntry.getMethod());
                files.add(file);
                zipEntry=zipInputStream.getNextEntry();
            }
        }
        return files;
    }

    private void addNewZopEntry(ZipOutputStream zipOutputStream, Path filePath, Path fileName) throws IOException {
        Path fullPath = filePath.resolve(fileName);
        try(InputStream inputStream = Files.newInputStream(fullPath)){
            ZipEntry entry = new ZipEntry(fileName.toString());

            zipOutputStream.putNextEntry(entry);

            copyData(inputStream,zipOutputStream);

            zipOutputStream.closeEntry();
        }
    }

    private void copyData(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[8 * 1024];
        int len;
        while ((len=inputStream.read(buffer))>0){
            outputStream.write(buffer,0,len);
        }
    }
}
