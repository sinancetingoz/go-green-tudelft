package pojos;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultValueTest {

    DefaultValue dv;

    @Before
    public void initialize() {
        dv = new DefaultValue("tomato", Category.food, "kg", 25, 0.5);
    }

    @Test
    public void testConstructor() {
        assertEquals("tomato", dv.getDescription());
    }

    @Test
    public void testGetDescription() {
        assertEquals("tomato", dv.getDescription());
    }

    @Test
    public void testSetDescription() {
        dv.setDescription("carrot");
        assertEquals("carrot", dv.getDescription());
        dv.setDescription("tomato");
    }

    @Test
    public void testGetCategory() {
        assertEquals(Category.food, dv.getCategory());
    }

    @Test
    public void testSetCategory() {
        dv.setCategory(Category.energy);
        assertEquals(Category.energy, dv.getCategory());
        dv.setCategory(Category.food);
    }

    @Test
    public void testGetUnit() {
        assertEquals("kg", dv.getUnit());
    }

    @Test
    public void testSetUnit() {
        dv.setUnit("g");
        assertEquals("g", dv.getUnit());
        dv.setUnit("kg");
    }

    @Test
    public void testGetPoints() {
        assertEquals(25, dv.getPoints());
    }

    @Test
    public void testSetPoints() {
        dv.setPoints(50);
        assertEquals(50, dv.getPoints());
        dv.setPoints(25);
    }

    @Test
    public void testGetConsumption() {
        assertEquals(0.5, dv.getConsumption(), 0.001);
    }

    @Test
    public void testSetConsumption() {
        dv.setConsumption(0.3);
        assertEquals(0.3, dv.getConsumption(), 0.001);
        dv.setConsumption(0.5);
    }

    @Test
    public void testEquals1() {
        DefaultValue dv1 = new DefaultValue("tomato", Category.food, "kg", 25, 0.5);
        assertEquals(dv, dv1);
    }

    @Test
    public void testEquals2() {
        int i = 2;
        assertNotEquals(dv, i);
    }

    @Test
    public void testEquals3() {
        DefaultValue dv1 = new DefaultValue("tomato", Category.food, "kg", 25, 0.5);
        dv1.setDescription("carrot");
        assertNotEquals(dv, dv1);
    }

    @Test
    public void testEquals4() {
        DefaultValue dv1 = new DefaultValue("tomato", Category.food, "kg", 25, 0.5);
        dv1.setCategory(Category.energy);
        assertNotEquals(dv, dv1);
    }

    @Test
    public void testEquals5() {
        DefaultValue dv1 = new DefaultValue("tomato", Category.food, "kg", 25, 0.5);
        dv1.setUnit("g");
        assertNotEquals(dv, dv1);
    }

    @Test
    public void testEquals6() {
        DefaultValue dv1 = new DefaultValue("tomato", Category.food, "kg", 25, 0.5);
        dv1.setPoints(50);
        assertNotEquals(dv, dv1);
    }

    @Test
    public void testEquals7() {
        DefaultValue dv1 = new DefaultValue("tomato", Category.food, "kg", 25, 0.5);
        dv1.setConsumption(0.3);
        assertNotEquals(dv, dv1);
    }

    @Test
    public void testDegreesToPoints() {
        DefaultValue.initPts();
        assertEquals(0, DefaultValue.degreesToPoints(24.0, 24));
        assertEquals(100, DefaultValue.degreesToPoints(19.0, 24));
        assertEquals(89, DefaultValue.degreesToPoints(19.111, 24));
    }

    @Test
    public void testKwhToPoints() {
        DefaultValue.initPts();
        assertEquals(14, DefaultValue.kwhToPoints(250));
        assertEquals(14, DefaultValue.kwhToPoints(249));
    }
}