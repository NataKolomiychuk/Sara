package com.netcracker.kolomiychuk.excel_parser.Run;

import com.netcracker.kolomiychuk.excel_parser.excel.ExcelEntityParser;
import com.netcracker.kolomiychuk.excel_parser.manager.EntityRepository;

import javax.xml.bind.JAXBException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;

public  class ExcelParserTask implements Runnable {
    private final String filePath;
    private final EntityRepository entityRepository;

    public ExcelParserTask(String filePath, EntityRepository entityRepository) {
        this.filePath = filePath;
        this.entityRepository = entityRepository;
    }

    @Override
    public void run() {

        ExcelEntityParser excelEntityParser = null;
        try {
            excelEntityParser = new ExcelEntityParser();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        Collection<? extends Object> entitiesByType = null;
        try {
            FileInputStream file = new FileInputStream(filePath);
            entitiesByType = excelEntityParser.readFromExcel(file);
            entityRepository.insertAll(entitiesByType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}