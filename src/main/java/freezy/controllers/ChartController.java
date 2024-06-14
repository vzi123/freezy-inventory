package freezy.controllers;

import freezy.services.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/charts")
public class ChartController {

    @Autowired
    private ChartService chartService;

    @PostMapping("/chart")
    public ResponseEntity<byte[]> generateChart(@RequestBody Map<String, List<Double>> data) {
        try {
            byte[] chartBytes = chartService.generateChart(data);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "image/png");
            headers.set("Content-Disposition", "inline; filename=\"chart.png\"");
            return new ResponseEntity<>(chartBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/pie")
    public ResponseEntity<byte[]> generatePieChart(@RequestBody Map<String, Number> data) {
        try {
            byte[] chartBytes = chartService.generatePieChart(data);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "image/png");
            headers.set("Content-Disposition", "inline; filename=\"pie_chart.png\"");
            return new ResponseEntity<>(chartBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

