package kusix.myflowers.fragment;

import java.util.HashMap;
import java.util.Map;

import kusix.myflowers.R.drawable;
import kusix.myflowers.R.string;
import kusix.myflowers.chart.LineChartBuilder;
import android.content.Context;
import android.view.View;

public class SoilMoistureInfoFragment extends BaseInfoFragment {

	@Override
	protected String getDataUnit() {
		return this.getString(string.temp_unit);
	}

	@Override
	protected int getInfoIconID() {
		return drawable.temp_on;
	}

	@Override
	protected String getTipsText() {
		return  "秋冬季的浇水量应根据室温严格控制。供暖之前，温度较低，植株的土壤蒸发较慢，要减少浇水，水量应控制在原来的1/4～1/2之间。即使供暖之后，浇水也不可过勤，浇水要少向盆中浇，应由棕丝渗水。另外，还应向棕柱的气生根生长处喷水，以减少因蒸发过快引起根部吸水不足。冬季浇的水以晾晒过一天后的水比较好，水过凉容易损伤根部。";
		}

	@Override
	protected View getChartView(Context context) {
		String title = "一周土壤湿度";
		double[] xValues = new double[] { 1, 2, 3, 4, 5, 6, 7 };
		double[] yValues = new double[] { 68, 65, 78, 75, 67, 56, 75 };
		Map<Double,String> xTextLabels = new HashMap<Double,String>();
		xTextLabels.put(1d, "7/3");
		xTextLabels.put(2d, "7/4");
		xTextLabels.put(3d, "7/5");
		xTextLabels.put(4d, "7/6");
		xTextLabels.put(5d, "7/7");
		xTextLabels.put(6d, "7/8");
		xTextLabels.put(7d, "7/9");
		return LineChartBuilder.build(context, title, xValues, yValues, xTextLabels);
	}

}
