package ru.akirakozov.sd.refactoring.servlet.get;

import org.junit.Test;
import ru.akirakozov.sd.refactoring.servlet.ServletTest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;

public class GetProductsServletTest extends ServletTest {
    private final GetProductsServlet getProductServlet = new GetProductsServlet(dataBaseUrl);

    @Test
    public void getFromEmptyDatabase() {
        try {
            getProductServlet.doGet(request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertThat(htmlResponse.toString()).isEqualTo(
                "<html><body>\n" +
                        "</body></html>\n"
        );
        verify(response).setContentType("text/html");
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }


    @Test
    public void getFromNonEmptyDatabase() throws SQLException {
        productDBEnvironment.addProductInTable("name1", 101);
        productDBEnvironment.addProductInTable("name2", 102);
        productDBEnvironment.addProductInTable("name3", 103);

        try {
            getProductServlet.doGet(request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertThat(htmlResponse.toString()).isEqualTo(
                "<html><body>\n" +
                        "name1\t101</br>\n" +
                        "name2\t102</br>\n" +
                        "name3\t103</br>\n" +
                        "</body></html>\n");

        verify(response).setContentType("text/html");
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void nonExistentProductTable() throws SQLException {
        productDBEnvironment.dropProductTable();
        assertThatThrownBy(() -> getProductServlet.doGet(request, response))
                .isInstanceOf(RuntimeException.class);
    }
}