package com.factory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class ConfigParser {

    private File configFile;

    public HashMap<String, Integer> map = new HashMap<>();


    public ConfigParser(File configFile) {
        this.configFile = configFile;
    }

    public void getConfig() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(configFile)) ;
            while(br.ready()) {
                String rawLine = br.readLine();
                String[] lineTokens = rawLine.split("=");
                map.put(lineTokens[0], Integer.parseInt(lineTokens[1]));

            }
        } catch (Exception e) {}
    }
}
