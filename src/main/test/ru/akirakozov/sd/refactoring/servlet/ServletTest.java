package ru.akirakozov.sd.refactoring.servlet;

import org.junit.After;
import org.junit.Before;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public abstract class ServletTest {
    protected final ProductDBEnvironment productDBEnvironment = new ProductDBEnvironment();
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected StringBuilder htmlResponse;

    @Before
    public void setup() throws SQLException, IOException {
        productDBEnvironment.setup();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        htmlResponse = new StringBuilder();
        final PrintWriter writer = mock(PrintWriter.class);
        doAnswer(invocation -> {
            String text = invocation.getArgumentAt(0, String.class);
            htmlResponse.append(text).append("\n");
            return null;
        }).when(writer).println(anyString());
        doAnswer(invocation -> {
            String text = String.valueOf(invocation.getArgumentAt(0, Integer.class));
            htmlResponse.append(text).append("\n");
            return null;
        }).when(writer).println(anyInt());

        doAnswer(invocation -> {
            String text = String.valueOf(invocation.getArgumentAt(0, String.class));
            htmlResponse.append(text);
            return null;
        }).when(writer).print(anyString());
        when(response.getWriter()).thenReturn(writer);
    }

    @After
    public void clear() throws SQLException, IOException {
        productDBEnvironment.clear();
    }
}