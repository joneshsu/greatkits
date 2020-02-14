package com.joneshsu.great.utilites;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OmronGoods extends Goods {

    static final String[] LAST_DATA_IDENTITIES = {
        "0  0 0  - 0",
		"0  0 1 1 - 0",
		"0  0 0  - 2"
    };

    static final String DATA_IDENTITY_REGEX = "^[0-9]{1,3}[A-Za-z]{1,2}[0-9]{1,9}$";
    static final String CURRENCY_IDENTITY = ".";
    static final String UNIT_IDENTITY = "PCS";
    static final String SPECIAL_IDENTITY_FOR_QUANTITY = "0001";
    static final String SPECIAL_IDENTITY_FOR_UNIT = "2";
    
    final static Pattern dataIdentityPattern = Pattern.compile(DATA_IDENTITY_REGEX);
    
    static final int CURRENCY_IDENTITY_LENGTH = 4;
    static final int AMOUNT_OF_INDEX_OF_SHIFT_OF_NUMBER = 1;
    static final int AMOUNT_OF_INDEX_OF_SHIFT_OF_PRICE = 4;

    String name = "";
    String number = "";
    String origin = "";
    String quantity = "";
    String unitPrice = "";
    String totalPrice = "";
    String unit = "";

    int dataIdentityIndex = 0;

    boolean isGotDataIdentityIndex = false;
    boolean isDataAddingFinished = false;

    ArrayList<String> dataColumns = new ArrayList<String>();
    String[] exportableData;

    private static boolean isMatchedDataIdentity(String data) {
        Matcher identityMatcher = dataIdentityPattern.matcher(data);
        return identityMatcher.matches();
    }

    private void setName() {
        if (dataIdentityIndex == 0) return;
        for (int i = 0; i < dataIdentityIndex; i++) {
            name += dataColumns.get(i);
        }
    }

    private void setNumber() {
        number = dataColumns.get(dataIdentityIndex + AMOUNT_OF_INDEX_OF_SHIFT_OF_NUMBER);
    }

    private void setOrigin() {
        int lastIndex = dataColumns.size() - 1;
        origin = dataColumns.get(lastIndex);
    }

    private void setPrice() {
        String rowData = dataColumns.get(dataIdentityIndex + AMOUNT_OF_INDEX_OF_SHIFT_OF_PRICE);
        String[] splitData = rowData.split(" ");
        unitPrice = splitData[0];
        totalPrice = splitData[1];

        String partsOfUnitPriceAndQuantity = splitData[0].trim();
        String partsOfTotalPriceAndUnit = splitData[1];
        int indexOfEndOfPrice = partsOfUnitPriceAndQuantity.indexOf(CURRENCY_IDENTITY) + CURRENCY_IDENTITY_LENGTH;
        int indexOfEndOfQuantity = partsOfUnitPriceAndQuantity.lastIndexOf(SPECIAL_IDENTITY_FOR_QUANTITY);

        unitPrice = partsOfUnitPriceAndQuantity.substring(0, indexOfEndOfPrice);
        quantity = partsOfUnitPriceAndQuantity.substring(indexOfEndOfPrice, indexOfEndOfQuantity);

        int indexOfEndOfTotalPrice = partsOfTotalPriceAndUnit.indexOf(CURRENCY_IDENTITY) + CURRENCY_IDENTITY_LENGTH;

        totalPrice = partsOfTotalPriceAndUnit.substring(0, indexOfEndOfTotalPrice);

        if (partsOfTotalPriceAndUnit.contains(UNIT_IDENTITY)) {
            unit = UNIT_IDENTITY;
        } else {
            unit = partsOfTotalPriceAndUnit.substring(indexOfEndOfPrice);
            int indexOfEndOfUnit = unit.indexOf(SPECIAL_IDENTITY_FOR_UNIT);
            if (indexOfEndOfUnit != -1) {
                unit = unit.substring(0, indexOfEndOfUnit);
            } 
        }
    }

    private void findDataIdentityIndex(String data) {
        if (data == null) return;
        if (isMatchedDataIdentity(data)) {
            dataIdentityIndex = dataColumns.size();
            isGotDataIdentityIndex = true;
        }
    }

    private boolean isLastData(String data) {
        return Arrays.stream(LAST_DATA_IDENTITIES).parallel().anyMatch(data::contains);
    }
    
    private void createExportableData() {
        exportableData = new String[]{name, number, origin, quantity, unit, unitPrice, totalPrice};
    }

    private void startProcessingData() {
        setName();
        setNumber();
        setOrigin();
        setPrice();
        createExportableData();
    }

    @Override
    public void printData() {
        System.out.println("Nmae: " + name);
        System.out.println("Number: " + number);
        System.out.println("Origin: " + origin);
        System.out.println("Quantity: " + quantity);
        System.out.println("UnitPrice: " + unitPrice);
        System.out.println("TotalPrice: " + totalPrice);
        System.out.println("Unit: " + unit);
    }

    @Override
    public void addData(String data) {
        if (!isGotDataIdentityIndex) findDataIdentityIndex(data);
        if (isLastData(data)) {
            isDataAddingFinished = true;
            startProcessingData();
            return;
        }
        dataColumns.add(data);
    }
    
    @Override
    public boolean isDataAddingFinished() {
        return isDataAddingFinished;
    }

    @Override
    public String[] getExportableData() {
        return exportableData;
    }
}