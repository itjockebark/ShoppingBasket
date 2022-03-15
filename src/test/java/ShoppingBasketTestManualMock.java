import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShoppingBasketTestManualMock {

    private ShoppingBasket shoppingBasket;

    @BeforeEach
    void setUp() {
        shoppingBasket = new ShoppingBasket();
        shoppingBasket.setProductDAO(new ManualMockProductDAO());
    }

    @Test
    void testAddByName() {
        shoppingBasket.addByName(2,"Potatis");
        assertEquals("[[2*Potatis]]",shoppingBasket.toString());
    }

    @Test
    void testAddById() {
        shoppingBasket.addById(2,2);
        assertEquals("[[2*Tomat]]",shoppingBasket.toString());
    }

    @Test
    void testExceptionWrongNameInput() {
        assertThrows(IllegalArgumentException.class, () -> shoppingBasket.addByName(2,"Wrong input"));
    }

    @Test
    void testExceptionWrongIdInput() {
        assertThrows(IllegalArgumentException.class, () -> shoppingBasket.addById(2,96));
    }

    @Test
    void testGetTotalCost() {
        shoppingBasket.addByName(2,"Potatis");
        shoppingBasket.addById(5,1);
        assertEquals(175,shoppingBasket.getTotalCost());
    }

    @Test
    void testClear() {
        shoppingBasket.addByName(2,"Potatis");
        shoppingBasket.addById(5,2);
        assertEquals("[[2*Potatis], [5*Tomat]]", shoppingBasket.toString());

        shoppingBasket.clear();
        assertEquals("[]", shoppingBasket.toString());
    }

    @Test
    void testToString() {
        shoppingBasket.addByName(2,"Potatis");
        shoppingBasket.addById(5,2);
        assertEquals("[[2*Potatis], [5*Tomat]]", shoppingBasket.toString());
    }

    class ManualMockProductDAO implements ProductDAO {

        Product potatis = new Product(1,"Potatis",25);
        Product tomat = new Product(2, "Tomat",30);

        ArrayList<Product> products = new ArrayList<>(Arrays.asList(potatis,tomat));

        @Override
        public Product findById(int id) {
            for(Product product : products) {
                if(product.getId() == id) {
                    return product;
                }
            }
            return null;
        }

        @Override
        public Product findByName(String name) {
            for(Product product : products) {
                if(product.getName().equals(name)) {
                    return product;
                }
            }
            return null;
        }

        @Override
        public List<Product> findAll() {
            return null;
        }

        @Override
        public List<Product> findCheaperThan(int lowprice) {
            return null;
        }

        @Override
        public boolean isInStock(int id) {
            return false;
        }

        @Override
        public boolean delete(int id) {
            return false;
        }

        @Override
        public void raiseAllPrices(double percent) {

        }
    }
}
