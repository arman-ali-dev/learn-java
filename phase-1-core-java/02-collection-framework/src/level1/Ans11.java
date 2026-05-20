package level1;

import java.util.HashMap;
import java.util.Map;

public class Ans11 {
    public static void main(String[] args) {
        Map<String, String> cities = new HashMap<>();

        cities.put("Jaipur", "Rajasthan");
        cities.put("Delhi", "Delhi");
        cities.put("Mumbai", "Maharashtra");
        cities.put("Kolkata", "West Bengal");
        cities.put("Chennai", "Tamil Nadu");

        for (Map.Entry<String, String> e : cities.entrySet()) {
            System.out.println("city - " + e.getKey() + " state - " + e.getValue());
        }
    }
}
