package com.nagarro.assignment.application.service;

import com.nagarro.assignment.application.mapper.AuthorMapper;
import com.nagarro.assignment.application.mapper.BookMapper;
import com.nagarro.assignment.application.service.interfaces.AuthorService;
import com.nagarro.assignment.application.service.interfaces.BookService;
import com.nagarro.assignment.application.service.interfaces.FileService;
import com.nagarro.assignment.domain.constant.Item;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final BookService bookService;
    private final AuthorService authorService;

    private final BookMapper bookMapper;
    private final AuthorMapper authorMapper;

    @Override
    public void exportCSV(String fileName, HttpServletResponse response) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        var itemOptional = Item.getItemByValue(fileName);

        if (itemOptional.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid export type: " + fileName);
            return;
        }

        var item = itemOptional.get();

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition",
                "attachment; filename=" + item.getFileName() + "\"");

        switch (item) {
            case BOOK -> writeRecordsToCsv(
                    bookService.findAllBooks(),
                    response
            );
            case AUTHOR -> writeRecordsToCsv(
                    authorService.findAllAuthors(),
                    response
            );
        }
    }

    private <T> void writeRecordsToCsv(List<T> records, HttpServletResponse response) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException {
        StatefulBeanToCsv<T> writer = getWriter(response.getWriter());
        writer.write(records);
    }

    private static <T> StatefulBeanToCsv<T> getWriter(PrintWriter printWriter) {
        return new StatefulBeanToCsvBuilder<T>(printWriter)
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withOrderedResults(false)
                .build();
    }
}
