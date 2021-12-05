package pojos;

import client.ConnectActivity;

public class DefaultValue {
    private static int pts250Kwh;
    private static int pts1Degree;
    private String description;
    private Category category;
    private String unit;
    private int points;
    private double consumption;

    public DefaultValue() {

    }
    
    /**
     * Constructs a DefaultValue.
     * @param desc Description of the activity
     * @param cat  The category of the activity
     * @param unit The unit in which the value of the activity is measured
     * @param points The amount of points the activity gives
     * @param cons The amount that is consumed
     */
    public DefaultValue(String desc, Category cat, String unit, int points, double cons) {
        this.description = desc;
        this.category = cat;
        this.unit = unit;
        this.points = points;
        this.consumption = cons;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category cat) {
        this.category = cat;
    }


    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }


    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }


    public double getConsumption() {
        return consumption;
    }

    public void setConsumption(double cons) {
        this.consumption = cons;
    }


    public static void initPts() {
        pts250Kwh = ConnectActivity.getConsumption("Power saved by solar panels").getPoints();
        pts1Degree = ConnectActivity.getConsumption("Lowering home temperature").getPoints();
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DefaultValue) {
            DefaultValue that = (DefaultValue) obj;

            if (description.equals(that.description)
                && category == that.category
                && unit.equals(that.unit)
                && points == that.points
                && consumption == that.consumption) {
                return true;
            }
        }

        return false;
    }

    public static double converter(int points) {
        System.out.println(((double)points * 0.0001) / 5.0);
        return ((double)points * 0.0001) / 5.0;
    }

    /**
     * Converts Kwh to points.
     * @param kwh the amount of kwh to be converted
     * @return the amount of points.
     */
    public static int kwhToPoints(int kwh) {
        double points = ((double) kwh / 250.0) * pts250Kwh;
        int realPoints = (int) points;

        if (points - (double) realPoints >= 0.5) {
            ++ realPoints;
        }

        return realPoints;
    }


    /**
     * Converts degrees Celsius into points.
     * @param deg the degrees to be converted
     * @return the points to be added
     */
    public static int degreesToPoints(double deg, int hrs) {
        if (deg >= 20.0) {
            return 0;
        }
        double ans = (20.0 - deg) * (double) pts1Degree * ((double) hrs / 24.0);
        int realAns = (int) ans;

        if (ans - realAns >= 0.5) {
            ++ realAns;
        }

        return realAns;
    }
}
