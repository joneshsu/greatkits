package com.joneshsu.great.utilites;

import java.io.File;
import java.util.ArrayList;

import javax.swing.filechooser.FileFilter;

public class ExtensionFileFilter extends FileFilter {

    String description;
    ArrayList<String> extensions = new ArrayList<String>();

    public void addExtension(String extension) {
        if (!extension.startsWith(".")) {
            extension = "." + extension; 
        }
        extensions.add(extension.toLowerCase());
    }

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) return true;
        String name = f.getName().toLowerCase();
        for (String extension: extensions) {
            if (name.endsWith(extension)) return true;
        }
        return false;
    }

    public void setDescription(String d) {
        description = d;
    }

    @Override
    public String getDescription() {
        return description;
    }

    
}