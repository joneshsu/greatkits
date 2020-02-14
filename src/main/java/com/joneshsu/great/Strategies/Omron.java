package com.joneshsu.great.Strategies;

import java.io.File;
import java.util.ArrayList;

import com.joneshsu.great.utilites.ExcelHelper;
import com.joneshsu.great.utilites.Goods;
import com.joneshsu.great.utilites.OmronGoods;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class Omron {
    static final String FIRST_PAGE_OF_INVOICE_IDENTITY = "OTE(TWD)";
    static final String FIRST_PAGE_STARTING_IDENTITY = "MADE IN";
    static final String SATRING_SPLIT_SYMBOL = "\n";

    private boolean isFirstPageOfInvoice(String context) {
        return context.startsWith(FIRST_PAGE_OF_INVOICE_IDENTITY);
    }

    private boolean isHitStartingParseingPoint(String context) {
        return context.startsWith(FIRST_PAGE_STARTING_IDENTITY);
    }

    public String PDFToExcel(String filePath) { 
        try {
            File importedFile = new File(filePath);
            PDDocument document = PDDocument.load(importedFile);
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            int numberOfPages = document.getNumberOfPages();
            ArrayList<Goods> goodsList = new ArrayList<Goods>();

            System.out.println("Start loading data for Omron");
            int currentInvoicePage = 1;
            for (int currentPageNumber = 1; currentPageNumber <= numberOfPages; currentPageNumber++) {
                pdfTextStripper.setStartPage(currentPageNumber);
                pdfTextStripper.setEndPage(currentPageNumber);

                String currentPageText = pdfTextStripper.getText(document);
                String[] splitText = currentPageText.split(SATRING_SPLIT_SYMBOL);

                boolean isFirstPageOfInvoice = isFirstPageOfInvoice(splitText[0]);
                boolean canStartParsingText = true;

                if (isFirstPageOfInvoice) {
                    currentInvoicePage = 1;
                    canStartParsingText = false;
                } else {
                    currentInvoicePage++;
                }

                String pageNumberIdentity = String.valueOf(currentInvoicePage);

                Goods goods = new OmronGoods();
                for (int i = 0, length = splitText.length; i < length; i++) {
                    String text = splitText[i].trim();
                    if (text.equals(pageNumberIdentity)) break;
                    if (isFirstPageOfInvoice && !canStartParsingText) {
                        canStartParsingText = isHitStartingParseingPoint(text);
                        continue;
                    } 
                    goods.addData(text);
                    if (goods.isDataAddingFinished()) {
                        goodsList.add(goods);
                        goods = new OmronGoods();
                    }
                }
            }
            document.close();
            System.out.println("Finish loading data for Omron");

            String outputFilePath = filePath.replaceAll(".pdf", ".xlsx");

            ExcelHelper excelHelper = new ExcelHelper(outputFilePath);
            excelHelper.createFile(goodsList);

            return outputFilePath;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}