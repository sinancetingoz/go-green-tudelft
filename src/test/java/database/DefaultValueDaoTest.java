package database;

import org.junit.Before;
import org.junit.Test;
import pojos.Category;
import pojos.DefaultValue;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class DefaultValueDaoTest {

    DefaultValueDao db;
    DefaultValue defaultValue;

    @Before
    public void initialize() {
        db = new DefaultValueDao();
        db.changeDatabase("test");
        defaultValue = new DefaultValue("tomato", Category.food, "kg", 25, 0.5);
    }

    @Test
    public void testExistsFalse() throws SQLException {
        assertFalse(db.exists(defaultValue.getDescription()));
    }

    @Test
    public void testExistsTrue() throws SQLException {
        db.createDefaultValue(defaultValue);
        assertTrue(db.exists(defaultValue.getDescription()));
        db.deleteDefaultValue(defaultValue.getDescription());
    }

    @Test
    public void testGetDefaultValueFalse() throws SQLException {
        assertNull(db.getDefaultValue(defaultValue.getDescription()));
    }

    @Test
    public void testGetDefaultValueTrue() throws SQLException {
        db.createDefaultValue(defaultValue);
        assertEquals(defaultValue, db.getDefaultValue(defaultValue.getDescription()));
        db.deleteDefaultValue(defaultValue.getDescription());
    }

    @Test
    public void testCreateDefaultValue() throws SQLException {
        assertTrue(db.createDefaultValue(defaultValue));
        assertFalse(db.createDefaultValue(defaultValue));
        db.deleteDefaultValue(defaultValue.getDescription());
    }

    @Test
    public void testGetDefaultByCategory() throws SQLException {
        db.deleteDefaultValue(defaultValue.getDescription());
        db.createDefaultValue(defaultValue);
        ArrayList<DefaultValue> exp = new ArrayList<>();
        assertEquals(exp, db.getDefaultsByCategory(Category.energy));
        exp.add(defaultValue);
        assertEquals(exp, db.getDefaultsByCategory(Category.food));
        db.deleteDefaultValue(defaultValue.getDescription());
    }
}