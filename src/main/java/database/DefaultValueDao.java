package database;

import pojos.Category;
import pojos.DefaultValue;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DefaultValueDao extends Dao {

    public DefaultValueDao() {
        super();
    }


    /**
     * Checks whether a default value exists.
     * @param defaultValue the default value to be checked if existing
     * @return true if the default value exists, false otherwise
     * @throws SQLException if there was a problem with connecting to the database
     */
    public boolean exists(String defaultValue) throws SQLException {
        return getDefaultValue(defaultValue) != null;
    }

    /**
     * Returns the default value that matches the description.
     * @param desc the description of the default value to be searched
     * @return the default value if it is in the database, null otherwise
     * @throws SQLException when connecting to the database failed
     */
    public DefaultValue getDefaultValue(String desc) throws SQLException {
        String query = "SELECT description, category, unit, point_value, consumption "
                + "FROM default_values WHERE description = ?";

        PreparedStatement ps = this.conn.prepareStatement(query);
        ps.setString(1, desc);
        ResultSet rs = ps.executeQuery();

        DefaultValue dv = null;
        if (rs.next()) {
            String description = rs.getString("description");
            Category category = Category.valueOf(rs.getString("category"));
            String unit = rs.getString("unit");
            int points = rs.getInt("point_value");
            double consumption = rs.getDouble("consumption");

            dv = new DefaultValue(description, category, unit, points, consumption);
        }

        rs.close();
        ps.close();
        return dv;
    }

    /**
     * Creates a new default value in the database.
     * @param defaultValue the default value to be created
     * @return true if the default value was created successfully, false otherwise
     * @throws SQLException when connecting to the database failed
     */
    public boolean createDefaultValue(DefaultValue defaultValue) throws SQLException {
        if (exists(defaultValue.getDescription())) {
            return false;
        }


        String query = "INSERT INTO default_values "
            + "(description, category, unit, point_value, consumption)"
            + "VALUES (?, ?, ?, ?, ?)";

        PreparedStatement ps = this.conn.prepareStatement(query);
        ps.setString(1, defaultValue.getDescription());
        ps.setString(2, defaultValue.getCategory().name());
        ps.setString(3, defaultValue.getUnit());
        ps.setInt(4, defaultValue.getPoints());
        ps.setDouble(5, defaultValue.getConsumption());

        ps.execute();
        ps.close();
        return true;
    }


    /**
     * Deletes a default value from the database.
     * @param defaultValue the default value to be deleted
     * @throws SQLException when connecting to the database failed
     */
    public void deleteDefaultValue(String defaultValue) throws SQLException {
        if (!exists(defaultValue)) {
            return;
        }

        String query = "DELETE FROM default_values WHERE "
                + "description = ?";

        PreparedStatement ps = this.conn.prepareStatement(query);
        ps.setString(1, defaultValue);
        ps.execute();
        ps.close();
    }


    /**
     * Gets all default values from a certain category.
     * @param category the category we are searching for
     * @return an array list containing all default values of this category
     * @throws SQLException when connecting to the database failed
     */
    public ArrayList<DefaultValue> getDefaultsByCategory(Category category) throws SQLException {
        String query = "SELECT description, category, unit, point_value, consumption "
                + "FROM default_values WHERE category = ?";

        PreparedStatement ps = this.conn.prepareStatement(query);
        ps.setString(1, category.name());
        ResultSet rs = ps.executeQuery();

        ArrayList<DefaultValue> defaultValues = new ArrayList<>();

        while (rs.next()) {
            String desc = rs.getString("description");
            Category cat = Category.valueOf(rs.getString("category"));
            String unit = rs.getString("unit");
            int points = rs.getInt("point_value");
            double cons = rs.getDouble("consumption");

            DefaultValue dv = new DefaultValue(desc, cat, unit, points, cons);
            defaultValues.add(dv);
        }

        return defaultValues;
    }
}
