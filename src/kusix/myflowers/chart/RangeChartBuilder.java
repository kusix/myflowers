package kusix.myflowers.chart;

import java.util.Map;
import java.util.Map.Entry;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.RangeCategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.view.View;

public class RangeChartBuilder {
	
	private static View buildRangeChartView(Context context, String title,
			double[] minValues, double[] maxValues, int xMin, int xMax,
			int yMin, int yMax, Map<Double, String> xTextLabels) {
		int[] colors = new int[] { Color.DKGRAY };
		XYMultipleSeriesDataset dataset = buildRangeDataSet(minValues,
				maxValues);
		XYMultipleSeriesRenderer renderer = buildSeriesRenderer(title, xMin,
				xMax, yMin, yMax, xTextLabels);
		return ChartFactory.getRangeBarChartView(context, dataset, renderer,
				Type.DEFAULT);
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
	
	private static XYMultipleSeriesRenderer buildSeriesRenderer(String title,
			int xMin, int xMax, int yMin, int yMax,
			Map<Double, String> xTextLabels) {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
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
		
		SimpleSeriesRenderer r = new SimpleSeriesRenderer();;
		r.setDisplayChartValues(true);
		r.setChartValuesTextSize(14);
		r.setChartValuesSpacing(3);
		r.setGradientEnabled(true);
		r.setGradientStart(10, Color.BLUE);
		r.setGradientStop(30, Color.rgb(255, 127, 0));
		renderer.addSeriesRenderer(r);
		return renderer;
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
	
	public static View build(Context context, String title,
			double[] minValues, double[] maxValues, Map<Double, String> xTextLabels) {
		int max = (int)ChartUtils.getMax(maxValues);
		int min = (int)ChartUtils.getMin(minValues);
		int offset = (max - min)/4;
		return buildRangeChartView(context,title,minValues,maxValues,0,minValues.length+1,min - offset,
				max + offset,xTextLabels);
	}
	
	

}
