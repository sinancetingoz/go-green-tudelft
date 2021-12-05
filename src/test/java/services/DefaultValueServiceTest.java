package services;

import database.DefaultValueDao;
import org.junit.Before;
import org.junit.Test;
import pojos.Category;
import pojos.DefaultValue;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DefaultValueServiceTest {

    DefaultValueService dvs;
    DefaultValueDao db;

    @Before
    public void initialize() {
        dvs = new DefaultValueService();
        db = new DefaultValueDao();
        db.changeDatabase("test");
        dvs.setDb(db);
    }

    @Test
    public void testGetValuesFromCategory() {
        db.changeDatabase("test");
        ArrayList<DefaultValue> exp = new ArrayList<>();
        db.changeDatabase("test");
        DefaultValue dv = new DefaultValue("tomato", Category.food, "kg", 25, 0.5);
        assertEquals(exp, dvs.getValuesFromCategory(Category.energy));
        assertTrue(dvs.createDefaultValue(dv));
        exp.add(dv);
        assertEquals(exp, dvs.getValuesFromCategory(Category.food));
        dvs.deleteDefaultValue(dv.getDescription());
    }
}