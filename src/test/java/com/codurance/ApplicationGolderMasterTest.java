package com.codurance;

import com.codurance.solid.Application;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ApplicationGolderMasterTest {

    private PrintStream originalSysOut;
    private ByteArrayOutputStream consoleStream;

    @Before
    public void setUp() {
        originalSysOut = System.out;
        consoleStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(consoleStream);
        System.setOut(printStream);
    }

    @Test
    public void goldenMaster() {
        Application.main(null);
        assertThat(consoleStream.toString(), equalTo("Book(name='The Hobbit', type='FANTASY', price='20.0}\r\n" +
                "Book(name='Game of Thrones', type='FANTASY', price='15.0}\r\n" +
                "Book(name='Software Craftsmanship', type='IT', price='18.0}\r\n" +
                "Book(name='GOOS', type='IT', price='25.0}\r\n" +
                "Book(name='Clean Code', type='IT', price='28.0}\r\n" +
                "Book(name='Notes from a Small Island', type='TRAVEL', price='10.0}\r\n" +
                "Book(name='Brazilian Flavous', type='COOKING', price='10.0}\r\n"));
    }

    @After
    public void tearDown() {
        System.setOut(originalSysOut);
    }
}
