package com.gargamel113.charles;

import com.drew.metadata.Directory;

public class Rules {

    private Rules single_instance = null;
    private FileFormats fileFormats;


    private Rules(){
        this.fileFormats = FileFormats.getInstance();
    }


//    public Directory formatSearchRules(String fileFormat){
//
//    }

    public Rules getIstance(){
        if(single_instance == null){
            single_instance = new Rules();
        }
        return single_instance;
    }
}