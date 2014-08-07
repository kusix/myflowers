package kusix.myflowers.chart;

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

public class LineChartBuilder {
	
	
	
	public static View build(Context context, String title,
			double[] xValues, double[] yValues,Map<Double, String> xTextLabels) {
		int max = (int)ChartUtils.getMax(yValues);
		int min = (int)ChartUtils.getMin(yValues);
		int offset = (max - min)/4;
		
		return ChartFactory.getLineChartView(context,
				buildLineDataset(title, xValues, yValues), buildSeriesRenderer(title,0,xValues.length+1,min - offset,
						max + offset,xTextLabels));
	}
	
	private static XYMultipleSeriesRenderer buildSeriesRenderer(String title,
			int xMin, int xMax, int yMin, int yMax,
			Map<Double, String> xTextLabels) {
		int[] colors = new int[] { Color.DKGRAY };
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
	
	protected static XYMultipleSeriesDataset buildLineDataset(
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
