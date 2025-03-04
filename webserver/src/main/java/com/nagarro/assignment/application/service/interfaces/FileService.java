package com.nagarro.assignment.application.service.interfaces;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface FileService {
    void exportCSV(String fileName, HttpServletResponse response)
        throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;
}
