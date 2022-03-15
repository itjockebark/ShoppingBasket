import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ShoppingBasketTestMockito {

    private ShoppingBasket shoppingBasket;
    private ProductDAO productDAO;

    @BeforeEach
    void setUp() {
        shoppingBasket = new ShoppingBasket();
        productDAO = mock(ProductDAO.class);
        shoppingBasket.setProductDAO(productDAO);
    }

    @Test
    void testAddByName() {
        when(productDAO.findByName("Potatis")).thenReturn(new Product(1,"Potatis",25));
        shoppingBasket.addByName(2,"Potatis");

        assertEquals("[[2*Potatis]]",shoppingBasket.toString());
    }

    @Test
    void testAddById() {
        when(productDAO.findById(1)).thenReturn(new Product(2,"Tomat",30));
        shoppingBasket.addById(2,1);

        assertEquals("[[2*Tomat]]",shoppingBasket.toString());
    }

    @Test
    void testExceptionWrongNameInput() {
        when(productDAO.findByName("Potatis")).thenReturn(new Product(1,"Potatis",25));

        assertThrows(IllegalArgumentException.class, () -> shoppingBasket.addByName(2,"Wrong input"));
    }



    @Test
    void testExceptionWrongIdInput() {
        when(productDAO.findById(1)).thenReturn(new Product(2,"Tomat",30));

        assertThrows(IllegalArgumentException.class, () -> shoppingBasket.addById(2,96));
    }

    @Test
    void testGetTotalCost() {
        when(productDAO.findByName("Potatis")).thenReturn(new Product(1,"Potatis",25));
        when(productDAO.findById(1)).thenReturn(new Product(2,"Tomat",30));

        shoppingBasket.addByName(2,"Potatis");
        shoppingBasket.addById(5,1);
        assertEquals(200,shoppingBasket.getTotalCost());
    }

    @Test
    void testClear() {
        when(productDAO.findByName("Potatis")).thenReturn(new Product(1,"Potatis",25));
        when(productDAO.findById(1)).thenReturn(new Product(2,"Tomat",30));

        shoppingBasket.addByName(2,"Potatis");
        shoppingBasket.addById(5,1);
        assertEquals("[[2*Potatis], [5*Tomat]]", shoppingBasket.toString());

        shoppingBasket.clear();
        assertEquals("[]", shoppingBasket.toString());
    }

    @Test
    void testToString() {
        when(productDAO.findByName("Potatis")).thenReturn(new Product(1,"Potatis",25));
        when(productDAO.findById(1)).thenReturn(new Product(2,"Tomat",30));

        shoppingBasket.addByName(2,"Potatis");
        shoppingBasket.addById(5,1);
        assertEquals("[[2*Potatis], [5*Tomat]]", shoppingBasket.toString());
    }
}
