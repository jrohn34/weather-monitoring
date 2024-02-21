package edu.iu.habahram.weathermonitoring.model;

public class ForecastDisplay implements Observer, DisplayElement {
    private float heatIndex;
    private Subject weatherData;

    public ForecastDisplay(Subject weatherData) {
        this.weatherData = weatherData;
    }

    @Override
    public String display() {
        return String.format("Heat Index: %.2f", heatIndex);
    }

    @Override
    public void update(float temperature, float humidity, float pressure, float maxTemp, float minTemp, float avgTemp) {
        heatIndex = computeHeatIndex(temperature, humidity);
    }

    private float computeHeatIndex(float t, float rh) {
        return (float)((16.923 + (0.185212 * t) + (5.37941 * rh) - (0.100254 * t * rh) +
                (0.00941695 * (t * t)) + (0.00728898 * (rh * rh)) +
                (0.000345372 * (t * t * rh)) - (0.000814971 * (t * rh * rh)) +
                (0.0000102102 * (t * t * rh * rh)) - (0.000038646 * (t * t * t)) + (0.0000291583 *
                (rh * rh * rh)) + (0.00000142721 * (t * t * t * rh)) +
                (0.000000197483 * (t * rh * rh * rh)) - (0.0000000218429 * (t * t * t * rh * rh)) +
                0.000000000843296 * (t * t * rh * rh * rh)) -
                (0.0000000000481975 * (t * t * t * rh * rh * rh)));
    }

    @Override
    public String name() {
        return "Forecast Display";
    }

    @Override
    public String id() {
        return "forecast-display";
    }

    @Override
    public void subscribe() {
        weatherData.registerObserver(this);
    }

    @Override
    public void unsubscribe() {
        weatherData.removeObserver(this);
    }
}
