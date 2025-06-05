package com.br.teste.file;

import org.springframework.boot.CommandLineRunner;

import java.nio.file.Paths;

public class FileRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        FileProcessor fileProcessor = new FileProcessor();

        String inputPath = Paths.get("src/main/resources/input.txt").toAbsolutePath().toString();
        String outputPath = Paths.get("src/main/resources/output.txt").toAbsolutePath().toString();

        fileProcessor.process(inputPath, outputPath);

        System.out.println("Arquivo processado com sucesso!");
    }
}
