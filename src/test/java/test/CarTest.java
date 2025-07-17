import java.test;
import main.Car;
import main.Vehicle;
import org.junit.Test;
import static org.junit.Assert.*;

public class CarTest {
    @Test
    public void testCarCreation() {
        String licensePlate = "ABC123";
        Car car = new Car(licensePlate);
        assertNotNull(car);
        assertEquals(licensePlate, car.getLicensePlate());
        assertEquals("Medium", car.getSize());
    }

    @Test
    public void testCarIsVehicle() {
        Car car = new Car("XYZ789");
        assertTrue(car instanceof Vehicle);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCarWithNullLicensePlate() {
        new Car(null);
    }
}