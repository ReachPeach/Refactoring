package ru.akirakozov.sd.refactoring.servlet.query;

import org.junit.Before;
import org.junit.Test;
import ru.akirakozov.sd.refactoring.servlet.ServletTest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class QueryServletTest extends ServletTest {
    private final QueryServlet queryServlet = new QueryServlet(dataBaseUrl);

    @Before
    public void setup() throws IOException, SQLException {
        super.setup();
        productDBEnvironment.addProductInTable("name1", 101);
        productDBEnvironment.addProductInTable("name2", 102);
        productDBEnvironment.addProductInTable("name3", 103);
        productDBEnvironment.addProductInTable("name4", 104);
        productDBEnvironment.addProductInTable("name5", 105);
    }

    @Test
    public void maxQuery() {
        when(request.getParameter("command")).thenReturn("max");
        try {
            queryServlet.doGet(request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertThat(htmlResponse.toString()).isEqualTo(
                "<html><body>\n" +
                        "<h1>Product with max price: </h1>\n" +
                        "name5	105</br>\n" +
                        "</body></html>\n"
        );
        verify(request).getParameter("command");
        verify(response).setContentType("text/html");
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void minQuery() {
        when(request.getParameter("command")).thenReturn("min");
        try {
            queryServlet.doGet(request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertThat(htmlResponse.toString()).isEqualTo(
                "<html><body>\n" +
                        "<h1>Product with min price: </h1>\n" +
                        "name1	101</br>\n" +
                        "</body></html>\n"
        );
        verify(request).getParameter("command");
        verify(response).setContentType("text/html");
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void sumQuery() {
        when(request.getParameter("command")).thenReturn("sum");
        try {
            queryServlet.doGet(request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertThat(htmlResponse.toString()).isEqualTo(
                "<html><body>\n" +
                        "Summary price: \n" +
                        "515\n" +
                        "</body></html>\n"
        );
        verify(request).getParameter("command");
        verify(response).setContentType("text/html");
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void countQuery() {
        when(request.getParameter("command")).thenReturn("count");
        try {
            queryServlet.doGet(request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertThat(htmlResponse.toString()).isEqualTo(
                "<html><body>\n" +
                        "Number of products: \n" +
                        "5\n" +
                        "</body></html>\n"
        );
        verify(request).getParameter("command");
        verify(response).setContentType("text/html");
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void unknownQuery() {
        when(request.getParameter("command")).thenReturn("unknown");
        try {
            queryServlet.doGet(request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertThat(htmlResponse.toString()).isEqualTo(
                "Unknown command: unknown\n"
        );
        verify(request).getParameter("command");
        verify(response).setContentType("text/html");
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void nonExistentProductTable() throws SQLException {
        when(request.getParameter("command")).thenReturn("max");
        productDBEnvironment.dropProductTable();
        assertThatThrownBy(() -> queryServlet.doGet(request, response))
                .isInstanceOf(RuntimeException.class);
    }

}