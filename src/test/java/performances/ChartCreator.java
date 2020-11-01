package performances;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.embed.swing.JFXPanel;
import javafx.application.Platform;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;

import javax.swing.SwingUtilities;

import io.qameta.allure.Attachment;

public class ChartCreator {

	@Attachment
	public static byte[] toLineChartPict(String title, String seriesName, Map<Integer, Long> data) throws InterruptedException {
		
		int upperBound = (data.values().stream().max(Long::compare).get().intValue() / 1000 + 1) * 1000;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		AtomicBoolean completed = new AtomicBoolean(false);
		AtomicBoolean successfull = new AtomicBoolean(false);
		
		SwingUtilities.invokeLater(() -> {
			new JFXPanel();
			Platform.runLater(() -> {
				final NumberAxis xAxis = new NumberAxis(1, data.size(), 1);
				final NumberAxis yAxis = new NumberAxis("Milliiseconds", 0, upperBound, 500);
				yAxis.setTickMarkVisible(true);
				final AreaChart<Number, Number> lineChart = new AreaChart<Number, Number>(xAxis, yAxis);
				
				lineChart.setTitle(title);
				lineChart.setAnimated(false);
				
				XYChart.Series<Number, Number> series = new XYChart.Series();
				series.setName(seriesName);
				
				List<Integer> keys = data.keySet().stream().sorted().collect(Collectors.toList());
				for (Integer key : keys)
					series.getData().add(new XYChart.Data<>(key + 1, data.get(key)));
				
				lineChart.getData().add(series);
				Scene scene = new Scene(lineChart, 1000, 600);
				
				WritableImage writableImage = scene.snapshot(null);
				
				try {
					ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", byteArrayOutputStream);
					
				} catch (Exception e) {
					completed.set(true);
				}
				
				
			});
		});
		
		while (!completed.get()) Thread.sleep(5000);
		
		return (successfull.get()) ? byteArrayOutputStream.toByteArray() : null;
		
	}
}
