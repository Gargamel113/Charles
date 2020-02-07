package com.gargamel113.charles;

import com.gargamel113.abillities.FileSorter;

public class Charles {

    private static Charles charles;
    private FileSorter fileSorter;

    private Charles() {
        fileSorter = new FileSorter();
    }


    /**
     *
     * @param folderPath The root folder of the album user wants to sort
     */
    public void sortFiles(String folderPath){
        fileSorter.verifyPathExists(folderPath);
    }

    /**
     * Gets Charles out of his cave
     * @return Charles
     */
    public static Charles getCharles(){
        if (charles == null){
            charles = new Charles();
        }
        return charles;
    }
}