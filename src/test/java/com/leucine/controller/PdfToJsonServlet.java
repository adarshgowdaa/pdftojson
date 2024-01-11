package com.leucine.controller;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/convert")
public class PdfToJsonServlet extends HttpServlet {

	private static final long serialVersionUID = 220545829653111978L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle POST request if needed
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            // Replace "path/to/your/file.pdf" with the actual path to your PDF file
            String pdfFilePath = "C:/Users/adars/Downloads/dv.pdf";

            try (PDDocument document = PDDocument.load(new File(pdfFilePath))) {
                PDFTextStripper stripper = new PDFTextStripper();
                String rawText = stripper.getText(document);

                // Process the raw text and convert it to JSON
                String jsonResult = processRawText(rawText);

                out.print(jsonResult);
            } catch (IOException e) {
                e.printStackTrace();
                out.print("{\"error\": \"Error processing the PDF file.\"}");
            }
        }
    }

    private String processRawText(String rawText) {
        String[] lines = rawText.split("\\n");

        // Convert lines to JSON
        StringBuilder jsonBuilder = new StringBuilder("{\"data\": [");

        for (String line : lines) {
            // Customize the parsing logic based on your data structure
            String[] columns = line.split("\\s+");
            jsonBuilder.append("{");

            jsonBuilder.append("\"Location\": \"").append(columns[0]).append("\",");
            jsonBuilder.append("\"Counter\": \"").append(columns[1]).append("\",");
            jsonBuilder.append("\"Status\": \"").append(columns[2]).append("\",");
            jsonBuilder.append("\"TimeStamp\": \"").append(columns[3]).append(" ").append(columns[4]).append("\",");
            jsonBuilder.append("\"Period\": \"").append(columns[5]).append("\",");
            jsonBuilder.append("\"Count\": ").append(Double.parseDouble(columns[6])).append(",");
            jsonBuilder.append("\"Scale\": \"").append(columns[7]).append("\",");
            jsonBuilder.append("\"Volume\": ").append(Double.parseDouble(columns[8])).append(",");
            jsonBuilder.append("\"0.5\": ").append(Double.parseDouble(columns[9])).append(",");
            jsonBuilder.append("\"5.0\": ").append(Double.parseDouble(columns[10])).append(",");
            jsonBuilder.append("\"CAL\": ").append(Double.parseDouble(columns[11])).append(",");
            jsonBuilder.append("\"FLO\": \"").append(columns[12]).append("\",");
            jsonBuilder.append("\"User Name\": \"").append(columns[13]).append("\",");
            jsonBuilder.append("\"CRC\": \"").append(columns[14]).append("\"");

            jsonBuilder.append("},");
        }

        // Remove the trailing comma
        jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);

        jsonBuilder.append("]}");
        
        System.out.println(jsonBuilder);

        return jsonBuilder.toString();
    }
}