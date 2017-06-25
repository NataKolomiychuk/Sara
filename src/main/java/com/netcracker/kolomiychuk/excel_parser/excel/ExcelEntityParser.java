package com.netcracker.kolomiychuk.excel_parser.excel;

import com.netcracker.kolomiychuk.excel_parser.entities.Author;
import com.netcracker.kolomiychuk.excel_parser.entities.Book;
import com.netcracker.kolomiychuk.excel_parser.entities.BookShelf;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

public class ExcelEntityParser {
    private final JAXBContext jaxbContext;
    private final Unmarshaller unmarshaller;

    public ExcelEntityParser() throws JAXBException {
        jaxbContext = JAXBContext.newInstance(Book.class, Author.class, BookShelf.class);
        unmarshaller = jaxbContext.createUnmarshaller();
    }



    public Object parseEntity(String xmlEntity) {
        try {
            return unmarshaller.unmarshal(new StringReader(xmlEntity));
        } catch (JAXBException e) {
            // just skip this entity
            e.printStackTrace();
            return null;
        }
    }

    public Collection<? extends Object> readFromExcel(FileInputStream file) throws IOException {
        Collection<Object> collection = new ArrayList<Object>();
        HSSFWorkbook myExcelBook = new HSSFWorkbook(file);
        for (int n =0; n<myExcelBook.getNumberOfSheets(); n++) {
            readSheet(myExcelBook.getSheetAt(n),collection);
        }
        myExcelBook.close();
        return collection;
    }

    private void readSheet(HSSFSheet myExcelSheet, Collection<Object> collection)
    {
        HSSFRow firstRow = myExcelSheet.getRow(myExcelSheet.getFirstRowNum());
        if (firstRow == null ){
            return ;
        }
        int  nameCellNumber = nameCellNumber(firstRow);
        int  typeCellNumber = typeCellNumber(firstRow);
        int  attributesCellNumber = attributesCellNumber(firstRow);

        for(int rowNumber = myExcelSheet.getFirstRowNum()+1; rowNumber<=myExcelSheet.getLastRowNum(); rowNumber++) {
            HSSFRow row = myExcelSheet.getRow(rowNumber);
            Object object = parseEntity(row.getCell(attributesCellNumber).getStringCellValue());
            collection.add(object);
        }
    }

    private int  nameCellNumber (HSSFRow row) {
        for(int columnNumber =row.getFirstCellNum(); columnNumber < row.getLastCellNum(); columnNumber++) {
                HSSFCell cell = row.getCell(columnNumber);
                if (cell != null) {
                    if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                        if (cell.getStringCellValue().equals("name"))
                        {
                            return columnNumber;
                        }
                    }
                }
        }
        return 0;
    }

    private int  typeCellNumber (HSSFRow row) {
        for(int columnNumber =row.getFirstCellNum(); columnNumber < row.getLastCellNum(); columnNumber++) {
            HSSFCell cell = row.getCell(columnNumber);
            if (cell != null) {
                if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                    if (cell.getStringCellValue().equals("type"))
                    {
                        return columnNumber;
                    }
                }
            }
        }
        return 0;
    }

    private int  attributesCellNumber (HSSFRow row) {
        for(int columnNumber =row.getFirstCellNum(); columnNumber < row.getLastCellNum(); columnNumber++) {
            HSSFCell cell = row.getCell(columnNumber);
            if (cell != null) {
                if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                    if (cell.getStringCellValue().equals("attributes"))
                    {
                        return columnNumber;
                    }
                }
            }
        }
        return 0;
    }

}
