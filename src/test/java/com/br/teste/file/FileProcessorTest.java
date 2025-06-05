package com.br.teste.file;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileProcessorTest {

    @Test
    void testProcessRemoveEmptyLines() throws IOException {
        Path input = Files.createTempFile("input", ".txt");
        Files.write(input, List.of(
                "Line 1",
                "",
                "Line 2",
                " ",
                "Line 3"
        ));
        Path output = Files.createTempFile("output", ".txt");

        FileProcessor fileProcessor = new FileProcessor();
        fileProcessor.process(input.toString(), output.toString());

        List<String> resultado = Files.readAllLines(output);

        assertEquals(3, resultado.size());
        assertEquals("Line 1", resultado.get(0));
        assertEquals("Line 2", resultado.get(1));
        assertEquals("Line 3", resultado.get(2));

        Files.deleteIfExists(input);
        Files.deleteIfExists(output);
    }

}