package com.project.quantifiedself.read;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class JsonFileParserTest {

    // valid path w/ valid number of rows
    // valid path w/ invalid number of rows (ex. first name, last name - Tiffany, {no last name provided})
    // invalid path

    private String fileNotFoundPath;
    private String validJSONPath;
    private String invalidCSV;

    private JsonFileParser jsonFileParser;
    private JsonFileParser jsonfileParserCopy;

    @Before
    public void setUp() throws Exception {
        // file not found
        fileNotFoundPath = "./src/main/resources/file";

        // valid Path
        validJSONPath = "./src/main/resources/storyline.json";

        // parser object/class to test
        jsonFileParser = new JsonFileParser(validJSONPath);

        // parser object/class to test
        jsonfileParserCopy = new JsonFileParser(validJSONPath);

    }

    // tests if file is readable
    @Test
    public void testFileIsReadable() throws IOException {
        JsonFileParser jfn = new JsonFileParser(validJSONPath);
        Path path1 = Paths.get(validJSONPath);
        assertTrue(Files.isReadable(path1));
    }


    // test if the file is no found
    @Test(expected = FileNotFoundException.class)
    public void testFileNotFoundPath() throws IOException {
        JsonFileParser jfn = new JsonFileParser(fileNotFoundPath);
    }

}