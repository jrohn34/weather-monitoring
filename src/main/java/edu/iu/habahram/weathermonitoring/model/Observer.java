package edu.iu.habahram.weathermonitoring.model;

public interface Observer {
    void update(float temperature, float humidity, float pressure, float maxTemp, float minTemp, float avgTemp);
    String name();
    String id();
    String display();
    void subscribe();
    void unsubscribe();
}
