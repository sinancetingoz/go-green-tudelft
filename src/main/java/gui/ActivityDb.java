package gui;

import client.ConnectActivity;
import pojos.Category;
import pojos.DefaultValue;
import services.TemperatureService;

import java.util.ArrayList;

public class ActivityDb implements Runnable {


    public static class Energy {
        static ArrayList<String> descriptions = new ArrayList<>();
        static ArrayList<Integer> points = new ArrayList<>();
    }
    
    public static class Food {
        static ArrayList<String> descriptions = new ArrayList<>();
        static ArrayList<Integer> points = new ArrayList<>();
    }
    
    public  static class  Transportation {
        static ArrayList<String> descriptions = new ArrayList<>();
        static ArrayList<Integer> points = new ArrayList<>();
    }

    /**
     * Initializing sample points for meals.
     */
    public static void initialize() {
        ArrayList<DefaultValue> desc = ConnectActivity.getFood(Category.food);
        DefaultValue.initPts();
        TemperatureService.init();
        for (DefaultValue description : desc) {
            Food.descriptions.add(description.getDescription());
            //DefaultValue dv = ConnectActivity.getConsumption(description);
            Food.points.add(description.getPoints());
        }
        Thread energy = new Thread(new ActivityDb());
        energy.start();
        desc = ConnectActivity.getFood(Category.energy);
        for (DefaultValue description : desc) {
            Energy.descriptions.add(description.getDescription());
            //DefaultValue dv = ConnectActivity.getConsumption(description.getConsumption());
            Energy.points.add(description.getPoints());
        }
    }
    
    /**
     * Runs threads.
     */
    public void run() {
        ArrayList<DefaultValue> desc = ConnectActivity.getFood(Category.transportation);
        for (DefaultValue description : desc) {
            Transportation.descriptions.add(description.getDescription());
            //DefaultValue dv = ConnectActivity.getConsumption(description);
            Transportation.points.add(description.getPoints());
        }
    }
}
