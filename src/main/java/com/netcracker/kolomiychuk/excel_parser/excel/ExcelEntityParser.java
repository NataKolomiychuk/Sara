package com.netcracker.kolomiychuk.excel_parser.excel;

import com.netcracker.kolomiychuk.excel_parser.entities.Author;
import com.netcracker.kolomiychuk.excel_parser.entities.Book;
import com.netcracker.kolomiychuk.excel_parser.entities.BookShelf;
import com.netcracker.kolomiychuk.excel_parser.entities.Entity;
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
    private String ATTRIBUTES = "attributes";

    public ExcelEntityParser() throws JAXBException {
        jaxbContext = JAXBContext.newInstance(Book.class, Author.class, BookShelf.class);
        unmarshaller = jaxbContext.createUnmarshaller();
    }

    public Entity parseEntity(String xmlEntity) {
        try {
            return (Entity)unmarshaller.unmarshal(new StringReader(xmlEntity));
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Collection<Entity> readFromExcel(FileInputStream file) throws IOException {
        Collection<Entity> entities = new ArrayList<>();
        HSSFWorkbook myExcelBook = null;
        myExcelBook = new HSSFWorkbook(file);
        for (int n = 0; n < myExcelBook.getNumberOfSheets(); n++) {
            readSheet(myExcelBook.getSheetAt(n), entities);
        }
        myExcelBook.close();
        return entities;
    }

    private void readSheet(HSSFSheet myExcelSheet, Collection<Entity> entities)
    {
        HSSFRow firstRow = myExcelSheet.getRow(myExcelSheet.getFirstRowNum());
        if (firstRow == null ) {
            return ;
        }
        int attributesCellNumber = attributesCellNumber(firstRow);
        for ( int rowNumber = myExcelSheet.getFirstRowNum()+1; rowNumber<=myExcelSheet.getLastRowNum(); rowNumber++ ) {
            HSSFRow row = myExcelSheet.getRow(rowNumber);
            Entity object = parseEntity(row.getCell(attributesCellNumber).getStringCellValue());
            if (object!=null) {
                entities.add(object);
            }
        }
    }

    private int attributesCellNumber (HSSFRow row) {
        for (int columnNumber =row.getFirstCellNum(); columnNumber < row.getLastCellNum(); columnNumber++) {
            HSSFCell cell = row.getCell(columnNumber);
            if ( cell != null && cell.getCellType() == HSSFCell.CELL_TYPE_STRING && ATTRIBUTES.equals(cell.getStringCellValue())) {
                return columnNumber;
            }
        }
        return 0;
    }

}
