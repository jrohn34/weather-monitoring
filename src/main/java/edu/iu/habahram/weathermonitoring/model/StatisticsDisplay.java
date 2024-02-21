package edu.iu.habahram.weathermonitoring.model;

import org.springframework.stereotype.Component;

@Component
public class StatisticsDisplay implements Observer, DisplayElement {
    private float maxTemp = Float.MIN_VALUE;
    private float minTemp = Float.MAX_VALUE;
    private float avgTemp = 0;
    private Subject weatherData;
    private int numReadings;

    @Override
    public String display() {
        StringBuilder display = new StringBuilder();
        display.append("Weather Stats\n");
        display.append("Average Temp: ").append(avgTemp).append("\n");
        display.append("Min. Temp: ").append(minTemp).append("\n");
        display.append("Max. Temp: ").append(maxTemp).append("\n");
        return display.toString();
    }

    @Override
    public void update(float temperature, float humidity, float pressure, float maxTemp, float minTemp, float avgTemp) {
        this.maxTemp = Math.max(this.maxTemp, maxTemp);
        this.minTemp = Math.min(this.minTemp, minTemp);
        this.avgTemp = (this.avgTemp * numReadings + avgTemp) / (numReadings + 1);
        numReadings++;
    }

    @Override
    public String name() {
        return "Statistics Display";
    }

    @Override
    public String id() {
        return "statistics-display";
    }

    @Override
    public void unsubscribe() {
        if (weatherData != null) {
            weatherData.removeObserver(this);
        }
    }
    @Override
    public void subscribe() {
        if (weatherData != null) {
            weatherData.registerObserver(this);
        }
    }
}
