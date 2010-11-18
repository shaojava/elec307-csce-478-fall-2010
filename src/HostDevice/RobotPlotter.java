package HostDevice;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class RobotPlotter extends ApplicationFrame {

	private XYSeries series;
	
	private double previousX;
	
	private double previousY;
	
	private double previousHeading;
	
	public final long PLOT_DISTANCE_INTERVAL = 50;


	public RobotPlotter(final String title) {

		super(title);
		
		this.previousX = 0;
		this.previousY = 0;
		this.previousHeading = 0;
		
		this.series = new XYSeries("Posistion Data");
		final XYSeriesCollection dataset = new XYSeriesCollection(this.series);
		final JFreeChart chart = createChart(dataset);

		final ChartPanel chartPanel = new ChartPanel(chart);

		final JPanel content = new JPanel(new BorderLayout());
		content.add(chartPanel);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		setContentPane(content);

	}


	private JFreeChart createChart(final XYDataset dataset) {
		final JFreeChart result = ChartFactory.createScatterPlot(
				"Dynamic Data Demo", 
				"Time", 
				"Value",
				dataset, 
				PlotOrientation.VERTICAL, 
				false, 
				false, 
				false
		);
		final XYPlot plot = result.getXYPlot();
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);

		return result;
	}
	
	public void updatePosistion(double heading, double distance) {
		
		double deltaX = distance*Math.cos(Math.toRadians(this.previousHeading));
		
		double deltaY = distance*Math.sin(Math.toRadians(this.previousHeading));
		System.out.println(deltaX + ", " + deltaY);
		
		this.previousX += deltaX;
		this.previousY += deltaY;
		
		this.series.add(this.previousX, this.previousY);
		
		this.previousHeading = heading;
	}

}