package com.netcracker.kolomiychuk.excel_parser;

import com.netcracker.kolomiychuk.excel_parser.Run.ExcelParserTask;
import com.netcracker.kolomiychuk.excel_parser.manager.EntityRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExcelParserApplication {


    public static void main(String[] args) throws IOException, JAXBException {
        ApplicationContext ctx =
                new FileSystemXmlApplicationContext("src/main/webapp/WEB-INF/applicationContext.xml");
        EntityRepository entityRepository = ctx.getBean(EntityRepository.class);
        ExecutorService executorService = Executors.newFixedThreadPool(args.length);
        Collection<Callable<ExcelParserTask>> tasks = new ArrayList<>();
        for (String filePath : args) {
            tasks.add(new ExcelParserTask(filePath, entityRepository));
        }
        try {
            executorService.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            executorService.shutdown();
        }
    }




}
