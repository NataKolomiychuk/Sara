package com.netcracker.kolomiychuk.excel_parser.Run;

import com.netcracker.kolomiychuk.excel_parser.entities.Entity;
import com.netcracker.kolomiychuk.excel_parser.excel.ExcelEntityParser;
import com.netcracker.kolomiychuk.excel_parser.manager.EntityRepository;

import javax.xml.bind.JAXBException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.Callable;

public class ExcelParserTask implements Callable {
    private final String filePath;
    private final EntityRepository entityRepository;

    public ExcelParserTask(String filePath, EntityRepository entityRepository) {
        this.filePath = filePath;
        this.entityRepository = entityRepository;
    }

    @Override
    public Object call() throws Exception {
        ExcelEntityParser excelEntityParser = null;
        try {
            excelEntityParser = new ExcelEntityParser();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        try {
            FileInputStream file = new FileInputStream(filePath);
            Collection<Entity> entitiesByType = excelEntityParser.readFromExcel(file);
            entityRepository.insertAll(entitiesByType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}