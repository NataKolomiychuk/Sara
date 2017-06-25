package com.netcracker.kolomiychuk.excel_parser;

import com.netcracker.kolomiychuk.excel_parser.entities.Author;
import com.netcracker.kolomiychuk.excel_parser.entities.Book;
import com.netcracker.kolomiychuk.excel_parser.entities.BookShelf;
import com.netcracker.kolomiychuk.excel_parser.excel.ExcelEntityParser;
import com.netcracker.kolomiychuk.excel_parser.manager.Insert;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExcelParserApplication {
    private static ExcelEntityParser excelEntityParser;

    public static void main(String[] args) throws IOException, JAXBException {
        /*String filePathsProperty = System.getProperty("filePaths");
        List<String> filePaths = Arrays.asList(filePathsProperty.split(","));*/
        ExecutorService executorService = Executors.newFixedThreadPool(args.length);
        for (String filePath : args) {
            executorService.execute(new ExcelParserTask(filePath));
        }
    }

    public static class ExcelParserTask implements Runnable {
        private final String filePath;

        ExcelParserTask(String filePath) {
            this.filePath = filePath;
        }

        @Override
        public void run() {
            ApplicationContext ctx =
                    new FileSystemXmlApplicationContext("src/main/webapp/WEB-INF/applicationContext.xml");

            Insert insert = ctx.getBean(Insert.class);
            //здесь просто Collection
            Collection<? extends Object> entitiesByType = null;
            try {
                entitiesByType = excelEntityParser.readFromExcel(new FileInputStream(filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            insert.insertAll(entitiesByType);
        }
    }

}
