package freezy.services;

import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class ChartService {

    public byte[] generateChart(Map<String, List<Double>> data) throws IOException {
        XYChart chart = new XYChartBuilder()
                .width(800)
                .height(600)
                .title("Sample Chart")
                .xAxisTitle("X")
                .yAxisTitle("Y")
                .build();

        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);

        data.forEach(chart::addSeries);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BitmapEncoder.saveBitmap(chart, baos, BitmapEncoder.BitmapFormat.PNG);
        return baos.toByteArray();
    }

    public byte[] generatePieChart(Map<String, Number> data) throws IOException {
        PieChart chart = new PieChartBuilder()
                .width(800)
                .height(600)
                .title("Sample Pie Chart")
                .build();

        Color[] sliceColors = new Color[]{
                new Color(155, 3, 90), // HotPink
                new Color(135, 206, 250), // LightSkyBlue
                new Color(144, 238, 144), // LightGreen
                new Color(255, 165, 0),   // Orange
                new Color(238, 130, 238)  // Violet
        };
        chart.getStyler().setSeriesColors(sliceColors);

        data.forEach((key, value) -> chart.addSeries(key, value));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BitmapEncoder.saveBitmap(chart, baos, BitmapEncoder.BitmapFormat.PNG);
        return baos.toByteArray();
    }
}

