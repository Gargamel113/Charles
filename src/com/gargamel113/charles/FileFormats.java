package com.gargamel113.charles;

import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.mp4.Mp4Directory;
import com.drew.metadata.png.PngDirectory;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FileFormats {


    //Hashmap holds the value of a String array where
    //Index 0 is the folder the file should be put in
    //Index 1 is what search method to use when finding date
    Map<String, String[]> formats;
    private static FileFormats single_instance = null;

    private FileFormats(){
        formats = new HashMap<>();
        initializeMap();
    }

    private void initializeMap(){

        formats.put("jpg",  new String[]{"picture", "jpg"});

        formats.put("png", new String[]{"picture", "png"});

//        formats.put("tif", "picture");
//        formats.put("gif", "picture");
//        formats.put("raw", "picture");
//
//        formats.put("avi", "movie");
//        formats.put("mov", "movie");
          formats.put("mp4", new String[]{"movie", "mp4"});
//        formats.put("wmv", "movie");
//        formats.put("flv", "movie");
//
//        formats.put("mp3", "music");
//        formats.put("wav", "music");
    }

    public String getType(String key){
        return formats.get(key)[0];
    }

    public static FileFormats getInstance(){
        if (single_instance == null){
            single_instance = new FileFormats();
        }
        return single_instance;
    }

    //PITURE FORMATS
    public Date getJpegDate(Metadata metadata){
        Directory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        Date date = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
        return date;
    }

    public Date getPngDate(Metadata metadata){
        Directory directory = metadata.getFirstDirectoryOfType(PngDirectory.class);
        Date date = directory.getDate(PngDirectory.TAG_LAST_MODIFICATION_TIME);
        return date;
    }

    //MOVIE FORMATS
    public Date getMp4Date(Metadata metadata){
        Directory directory = metadata.getFirstDirectoryOfType(Mp4Directory.class);
        Date date = directory.getDate(Mp4Directory.TAG_CREATION_TIME);
        return date;
    }

    //MUSIC FORMATS
}
