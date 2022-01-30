package ru.akirakozov.sd.refactoring.servlet.add;

import org.junit.Test;
import ru.akirakozov.sd.refactoring.servlet.Product;
import ru.akirakozov.sd.refactoring.servlet.ServletTest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddProductServletTest extends ServletTest {
    private final AddProductServlet addProductServlet = new AddProductServlet(dataBaseUrl);

    @Test
    public void addInEmptyDatabase() throws SQLException {
        Product product = new Product("name", 100);
        when(request.getParameter("name")).thenReturn(product.getName());
        when(request.getParameter("price")).thenReturn(String.valueOf(product.getPrice()));

        try {
            addProductServlet.doGet(request, response);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        List<Product> products = productDBEnvironment.getProductsFromTable();
        assertThat(products.size()).isEqualTo(1);
        assertThat(products.get(0)).isEqualTo(product);
        assertThat(htmlResponse.toString().equals("OK\n"));

        verify(request).getParameter("name");
        verify(request).getParameter("price");
        verify(response).setContentType("text/html");
        verify(response).setStatus(HttpServletResponse.SC_OK);

    }

    @Test
    public void addInNonEmptyDatabase() throws SQLException {
        productDBEnvironment.addProductInTable("name1", 101);
        productDBEnvironment.addProductInTable("name2", 102);
        productDBEnvironment.addProductInTable("name3", 103);

        Product product = new Product("name", 100);
        when(request.getParameter("name")).thenReturn(product.getName());
        when(request.getParameter("price")).thenReturn(product.getPrice() + "");


        try {
            addProductServlet.doGet(request, response);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        List<Product> products = productDBEnvironment.getProductsFromTable();

        assertThat(products.size()).isEqualTo(4);
        assertThat(product).isIn(products);
        assertThat(htmlResponse.toString()).isEqualTo("OK\n");

        verify(request).getParameter("name");
        verify(request).getParameter("price");
        verify(response).setContentType("text/html");
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void nonExistentProductTable() throws SQLException {
        Product product = new Product("name", 100);
        when(request.getParameter("name")).thenReturn(product.getName());
        when(request.getParameter("price")).thenReturn(String.valueOf(product.getPrice()));

        productDBEnvironment.dropProductTable();
        assertThatThrownBy(() -> addProductServlet.doGet(request, response))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void nonNumberPrice() {
        when(request.getParameter("name")).thenReturn("name");
        when(request.getParameter("price")).thenReturn("price");
        assertThatThrownBy(() -> addProductServlet.doGet(request, response))
                .isInstanceOf(NumberFormatException.class);
    }
}