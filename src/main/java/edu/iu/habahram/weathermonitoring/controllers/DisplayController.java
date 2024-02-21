package edu.iu.habahram.weathermonitoring.controllers;

import edu.iu.habahram.weathermonitoring.model.CurrentConditionDisplay;
import edu.iu.habahram.weathermonitoring.model.Observer;
import edu.iu.habahram.weathermonitoring.model.StatisticsDisplay;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import edu.iu.habahram.weathermonitoring.model.ForecastDisplay;
import edu.iu.habahram.weathermonitoring.model.WeatherData;

@RestController
@RequestMapping("/displays")
public class DisplayController {
    private final CurrentConditionDisplay currentConditionDisplay;
    private final StatisticsDisplay statisticsDisplay;
    private final WeatherData weatherData;
    private final ForecastDisplay forecastDisplay;

    public DisplayController(CurrentConditionDisplay currentConditionDisplay, StatisticsDisplay statisticsDisplay, WeatherData weatherData, ForecastDisplay forecastDisplay) {
        this.currentConditionDisplay = currentConditionDisplay;
        this.statisticsDisplay = statisticsDisplay;
        this.weatherData = weatherData;
        this.forecastDisplay = forecastDisplay;
    }

    @GetMapping
    public ResponseEntity<String> index() {
        StringBuilder html = new StringBuilder("<h1>Available screens:</h1><ul>");
        html.append("<li>").append(String.format("<a href=\"/displays/%s\">%s</a>", currentConditionDisplay.id(), currentConditionDisplay.name())).append("</li>");
        html.append("<li>").append(String.format("<a href=\"/displays/%s\">%s</a>", statisticsDisplay.id(), statisticsDisplay.name())).append("</li>");
        html.append("</ul>");
        return ResponseEntity.ok(html.toString());
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> display(@PathVariable String id) {
        Observer display = null;
        if (id.equalsIgnoreCase(currentConditionDisplay.id())) {
            display = currentConditionDisplay;
        } else if (id.equalsIgnoreCase(statisticsDisplay.id())) {
            display = statisticsDisplay;
        }
        if (display != null) {
            return ResponseEntity.ok(display.display());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/subscribe")
    public ResponseEntity<String> subscribe(@PathVariable String id) {
        Observer display = getDisplayById(id);
        if (display != null) {
            display.subscribe();
            weatherData.registerObserver(display);
            return ResponseEntity.ok("Subscribed!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{id}/unsubscribe")
    public ResponseEntity<String> unsubscribe(@PathVariable String id) {
        Observer display = getDisplayById(id);
        if (display != null) {
            display.unsubscribe();
            weatherData.removeObserver(display);
            return ResponseEntity.ok("Unsubscribed!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    private Observer getDisplayById(String id) {
        switch (id) {
            case "current-condition":
                return currentConditionDisplay;
            case "statistics-display":
                return statisticsDisplay;
            case "forecast-display":
                return forecastDisplay;
            default:
                return null;
        }
    }
}
