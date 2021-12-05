package services;

import database.DefaultValueDao;
import pojos.Category;
import pojos.DefaultValue;

import java.sql.SQLException;
import java.util.ArrayList;

public class DefaultValueService {
    private DefaultValueDao db = new DefaultValueDao();

    public void setDb(DefaultValueDao db) {
        this.db = db;
    }

    /**
     * Gets all the products from a certain category.
     * @param cat the category to search for
     * @return an array list containing all default values from this category
     */
    public ArrayList<DefaultValue> getValuesFromCategory(Category cat) {
        try {
            return db.getDefaultsByCategory(cat);
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    /**
     * Gets the default value for a certain product.
     * @param desc the description of the product
     * @return the default values of the product
     */
    public DefaultValue getDefaultValue(String desc) {
        try {
            return db.getDefaultValue(desc);
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Deletes a certain default value from the database.
     * @param dv the default value to be deleted
     */
    public void deleteDefaultValue(String dv) {
        try {
            db.deleteDefaultValue(dv);
        } catch (SQLException e) {
            return;
        }
    }

    /**
     * Creates a new default value.
     * @param dv the default value to be created
     * @return true if the default value was created successfully, false otherwise
     */
    public boolean createDefaultValue(DefaultValue dv) {
        try {
            return db.createDefaultValue(dv);
        } catch (SQLException e) {
            return false;
        }
    }
}
