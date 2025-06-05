package com.br.teste.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FileProcessor {

    public void process(String inputPath, String outputPath) throws IOException {
        List<String> linhas = Files.readAllLines(Paths.get(inputPath));
        List<String> linhasFiltradas = linhas.stream()
                .filter(linha -> !linha.trim( ).isEmpty()).collect(Collectors.toList());

        Files.write(Paths.get(outputPath), linhasFiltradas);
    }
}
