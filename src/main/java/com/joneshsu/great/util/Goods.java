package com.joneshsu.great.util;

public abstract class Goods {
    public abstract void addData(String data);
    public abstract boolean isDataAddingFinished();
    public abstract String[] getExportableData();
    public void printData() {
        System.out.println("Goods printData function");
    }
    
}
