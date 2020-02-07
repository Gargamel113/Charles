package com.gargamel113.abillities;

import com.gargamel113.charles.FileFormats;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileSorter {

    public static final String PATTERN = "yyyy-MM-dd";

    private FileInputStream fileInputStream;
    private SimpleDateFormat simpleDateFormat;
    private String originalPath, currentImage;
    private FileFormats fileFormats;

    public FileSorter() {
        this.fileInputStream = null;
        this.simpleDateFormat = new SimpleDateFormat(PATTERN);
        this.fileFormats = FileFormats.getInstance();
    }

    public void verifyPathExists(String originalPath) {
        this.originalPath = originalPath;
        checkUri();

        if (Files.exists(Paths.get(originalPath))) {
            System.out.println("Found your path! just a minute");
            findNextImage();
        } else {
            System.out.println("No such path exists! " + originalPath);
        }
    }

    //LOOP AND FIND EVERY IMAGE IN FOLDER   --DONE
    private void findNextImage() {
        File searchFolder = new File(originalPath);
        File[] elements = searchFolder.listFiles();

        if (searchFolder != null) {
            for (File file : elements) {
                if (file.isFile()) {
                    currentImage = file.getName();
                    organizeImages();
                }
            }
        } else {
            System.out.println("Not a valid path");
        }
    }

    private void organizeImages() {
        fileInputStream = getImage(originalPath, currentImage);

        if (fileInputStream != null) {
            Metadata metadata = getMetaData(fileInputStream);
            String date = getDate(metadata);
            if (date != null) {
                createFolder(date);
            } else {
                System.out.println("Could not retrive date information");
            }
        } else {
            System.out.println("Something went wrong");
        }
    }

    //CREATE FOLDER IF NOT ALREADY EXISTS
    private void createFolder(String date) {
        String abosulutePath = originalPath + "\\" + date;
        File folder = new File(abosulutePath);

        if (!folder.exists()) {
            if (folder.mkdir()) {
                System.out.println("DIRECTORY CREATED " + abosulutePath);
            } else {
                System.out.println("COULD NOT CREATE DIRECTORY " + abosulutePath);
            }
        }

        createSubFolder(abosulutePath);
    }

    private void createSubFolder(String abosulutePath) {
        String format = fileFormats.getType(getFormat());
        File subFolder = new File(abosulutePath + "\\" + format);

        if (!subFolder.exists()) {
            if (subFolder.mkdir()) {
                System.out.println("DIRECTORY CREATED " + subFolder);
            } else {
                System.out.println("COULD NOT CREATE SUBFOLDER");
            }
        }

        moveImageToFolder(originalPath, subFolder.toString());
    }

    //Moves image to a new folder           --DONE
    private void moveImageToFolder(String oldPath, String newPath) {
        oldPath += "\\" + currentImage;
        newPath += "\\" + currentImage;
        File temp = new File(newPath);
        Path move;

        try {
            if (!temp.exists()) {
                move = Files.move(Paths.get(oldPath), Paths.get(newPath));
            } else {
                while (temp.exists()) {
                    newPath = makeCopy(newPath);
                    temp = new File(newPath);
                }
                move = Files.move(Paths.get(oldPath), Paths.get(newPath));
            }
        } catch (IOException e) {
            System.out.println("Could not move image to new path");
            e.printStackTrace();
        }
    }

    private String makeCopy(String source) {
        String target = source.replace(currentImage, "");
        String copy = currentImage.replace(".jpg", "");
        copy += "_copy.jpg";
        target += copy;
        return target;
    }

    public String getDate(Metadata metadata) {
        String format = getFormat();
        Date date = null;

        switch (format) {
            case "png":
                date = fileFormats.getPngDate(metadata);
                break;
            case "jpg":
                date = fileFormats.getJpegDate(metadata);
                break;
            case "mp4":
                date = fileFormats.getMp4Date(metadata);
                break;
        }

        if (date != null) {
            return simpleDateFormat.format(date);
        }
        return null;
    }

    private Metadata getMetaData(FileInputStream fs) {
        try {
            Metadata meta = ImageMetadataReader.readMetadata(fs);
            fileInputStream.close();
            return meta;
        } catch (ImageProcessingException | IOException e) {
            System.out.println("Error while getting metadata");
            e.printStackTrace();
            return null;
        }
    }

    private FileInputStream getImage(String path, String image) {
        try {
            return new FileInputStream(path + image);
        } catch (FileNotFoundException e) {
            System.out.println("No such file exists");
            e.printStackTrace();
            return null;
        }
    }

    private String getFormat() {
        String format = "";
        boolean mark = false;
        for (int i = 0; i < currentImage.length(); i++) {
            if (mark == true) {
                format += currentImage.charAt(i);
            }
            if (currentImage.charAt(i) == '.') {
                mark = true;
            }
        }
        return format;
    }

    private void checkUri() {
        char endingChar = originalPath.charAt(originalPath.length() - 1);

        if (endingChar != '\\' || endingChar != '\u002F') {
            originalPath += "\\";
        }
    }
}
