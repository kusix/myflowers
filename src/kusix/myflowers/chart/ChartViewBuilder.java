package kusix.myflowers.chart;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.RangeCategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.view.View;

public class ChartViewBuilder {

	public static View build(Context context) {

		return null;

		// return buildRangeChartView(context, title, minValues, maxValues,
		// xMin,
		// xMax, yMin, yMax, xTextLabels);
	}


	public static View buildLineChartView(Context context, String title,
			double[] xValues, double[] yValues,Map<Double, String> xTextLabels) {
		int[] colors = new int[] { Color.DKGRAY };
		return ChartFactory.getLineChartView(context,
				buildDataset(title, xValues, yValues), buildSeriesRenderer(title,0,xValues.length+1,(int)getMin(yValues),
						(int)getMax(yValues),xTextLabels,buildLineRenderer(colors)));
	}

	private static View buildRangeChartView(Context context, String title,
			double[] minValues, double[] maxValues, int xMin, int xMax,
			int yMin, int yMax, Map<Double, String> xTextLabels) {
		XYMultipleSeriesDataset dataset = buildRangeDataSet(minValues,
				maxValues);
		getMin(minValues);
		int[] colors = new int[] { Color.DKGRAY };
		XYMultipleSeriesRenderer renderer = buildSeriesRenderer(title, xMin,
				xMax, yMin, yMax, xTextLabels,buildRangeRenderer(colors));
		setRangeSeriesRenderer(renderer);
		return ChartFactory.getRangeBarChartView(context, dataset, renderer,
				Type.DEFAULT);
	}
	
	public static View buildSimpleRangeChartView(Context context, String title,
			double[] minValues, double[] maxValues, Map<Double, String> xTextLabels) {
		return buildRangeChartView(context,title,minValues,maxValues,0,minValues.length+1,(int)getMin(minValues),
				(int)getMax(maxValues),xTextLabels);
	}


	public static double getMin(double[] minValues) {
		double r = minValues[0];
		for(double value:minValues){
			if(value < r){
				r = value;
			}
		}
		return r;
	}
	
	public static double getMax(double[] minValues) {
		double r = minValues[0];
		for(double value:minValues){
			if(value > r){
				r = value;
			}
		}
		return r;
	}

	private static XYMultipleSeriesRenderer buildSeriesRenderer(String title,
			int xMin, int xMax, int yMin, int yMax,
			Map<Double, String> xTextLabels,XYMultipleSeriesRenderer renderer) {
		
		setChartSettings(renderer, title, null, null, xMin, xMax, yMin, yMax,
				Color.TRANSPARENT, Color.DKGRAY);
		renderer.setBarSpacing(0.5);
		renderer.setPanEnabled(false, false);
		renderer.setXLabels(1);
		renderer.setYLabels(10);
		for (Entry<Double, String> e : xTextLabels.entrySet()) {
			renderer.addXTextLabel(e.getKey(), e.getValue());
		}
		renderer.setMargins(new int[] { 20, 30, 15, 0 });
		renderer.setMarginsColor(Color.argb(0, 0xff, 0, 0));
		renderer.setYLabelsAlign(Align.CENTER);
		renderer.setBackgroundColor(Color.rgb(248, 248, 248));
		renderer.setApplyBackgroundColor(true);
		return renderer;
	}

	private static void setRangeSeriesRenderer(XYMultipleSeriesRenderer renderer) {
		SimpleSeriesRenderer r = new SimpleSeriesRenderer();;
		r.setDisplayChartValues(true);
		r.setChartValuesTextSize(14);
		r.setChartValuesSpacing(3);
		r.setGradientEnabled(true);
		r.setGradientStart(10, Color.BLUE);
		r.setGradientStop(30, Color.rgb(255, 127, 0));
		renderer.addSeriesRenderer(r);
	}

	private static XYMultipleSeriesDataset buildRangeDataSet(
			double[] minValues, double[] maxValues) {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		RangeCategorySeries series = new RangeCategorySeries("");
		int length = minValues.length;
		for (int k = 0; k < length; k++) {
			series.add(minValues[k], maxValues[k]);
		}
		dataset.addSeries(series.toXYSeries());
		return dataset;
	}

	private static XYMultipleSeriesRenderer buildRangeRenderer(int[] colors) {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.setAxisTitleTextSize(16);
		renderer.setChartTitleTextSize(20);
		renderer.setLabelsTextSize(15);
		renderer.setLegendTextSize(15);
		int length = colors.length;
		for (int i = 0; i < length; i++) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(colors[i]);
			renderer.addSeriesRenderer(r);
		}
		return renderer;
	}
	
	private static XYMultipleSeriesRenderer buildLineRenderer(int[] colors) {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.setAxisTitleTextSize(16);
		renderer.setChartTitleTextSize(20);
		renderer.setLabelsTextSize(15);
		renderer.setLegendTextSize(15);
		int length = colors.length;
	    for (int i = 0; i < length; i++) {
	      XYSeriesRenderer r = new XYSeriesRenderer();
	      r.setColor(colors[i]);
	      r.setPointStyle(PointStyle.CIRCLE);
	      renderer.addSeriesRenderer(r);
	    }
		return renderer;
	}
	
	

	protected static XYMultipleSeriesDataset buildDataset(
			String title, double[] xValues, double[] yValues) {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		addXYSeries(dataset, title, xValues, yValues, 0);
		return dataset;
	}

	public static void addXYSeries(XYMultipleSeriesDataset dataset,
			String title, double[] xValues, double[] yValues,
			int scale) {
			XYSeries series = new XYSeries(title, scale);
			int seriesLength = xValues.length;
			for (int k = 0; k < seriesLength; k++) {
				series.add(xValues[k], yValues[k]);
			}
			dataset.addSeries(series);
		
	}

	private static void setChartSettings(XYMultipleSeriesRenderer renderer,
			String title, String xTitle, String yTitle, double xMin,
			double xMax, double yMin, double yMax, int axesColor,
			int labelsColor) {
		renderer.setChartTitle(title);
		if (null != xTitle) {
			renderer.setXTitle(xTitle);
		}
		if (null != yTitle) {
			renderer.setYTitle(yTitle);
		}
		renderer.setXAxisMin(xMin);
		renderer.setXAxisMax(xMax);
		renderer.setYAxisMin(yMin);
		renderer.setYAxisMax(yMax);
		renderer.setAxesColor(axesColor);
		renderer.setLabelsColor(labelsColor);
		renderer.setShowLegend(false);
	}

}
