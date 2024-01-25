package medium;

import java.util.HashMap;

public class UndergroundSystem {

    private HashMap<Integer, CustomerMovementInfo> info;
    private HashMap<String, Duration> avgTimeBetweenStations;

    public UndergroundSystem() {
        this.info = new HashMap<>();
        this.avgTimeBetweenStations = new HashMap<>();
    }

    public void checkIn(int id, String stationName, int t) {
        if (info.containsKey(id)) {
            System.out.println("Can not check-in for a already checked in customer");
            return;
        }
        info.put(id, new CustomerMovementInfo(stationName, t));
    }

    public void checkOut(int id, String stationName, int t) {
        if (!info.containsKey(id)) {
            System.out.println("Can not check-out for a not checked in customer");
            return;
        }
        CustomerMovementInfo customerMovementInfo = info.get(id);
        info.remove(id);
        Duration duration = avgTimeBetweenStations.getOrDefault(customerMovementInfo.station + "-" + stationName,
                new Duration(0, 0));
        duration.totalTime += (t - customerMovementInfo.time);
        duration.totalObservations += 1;
        avgTimeBetweenStations.put(customerMovementInfo.station + "-" + stationName, duration);
    }

    public double getAverageTime(String startStation, String endStation) {
        Duration duration = avgTimeBetweenStations.get(startStation + "-" + endStation);
        return ((duration.totalTime * 1.0) / duration.totalObservations);

    }
}

class CustomerMovementInfo {
    String station;
    int time;

    public CustomerMovementInfo(String station, int time) {
        this.station = station;
        this.time = time;
    }

    @Override
    public String toString() {
        return "CustomerMovementInfo{" +
                "station='" + station + '\'' +
                ", time=" + time +
                '}';
    }
}

class Duration {
    int totalTime;
    int totalObservations;

    public Duration(int totalTime, int totalObservations) {
        this.totalTime = totalTime;
        this.totalObservations = totalObservations;
    }

    @Override
    public String toString() {
        return "Duration{" +
                "totalTime=" + totalTime +
                ", totalObservations=" + totalObservations +
                '}';
    }
}
