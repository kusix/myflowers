package kusix.myflowers.fragment;

import java.util.HashMap;
import java.util.Map;

import kusix.myflowers.R.drawable;
import kusix.myflowers.R.string;
import kusix.myflowers.chart.LineChartBuilder;
import android.content.Context;
import android.view.View;

public class AirHumidityInfoFragment extends BaseInfoFragment {

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
		return  "绿萝喜较大的空气湿度和湿润的环境。盛夏是绿萝的生长高峰，每天可向绿萝的气根和叶面喷雾数次，既可清洗叶片的尘埃，利于绿萝的呼吸，又能使叶色碧绿青翠，还能降低叶面温度，增加小环境的空气湿度，使叶片更好地生长";
				}

	@Override
	protected View getChartView(Context context) {
		String title = "一周空气湿度";
		double[] xValues = new double[] { 1, 2, 3, 4, 5, 6, 7 };
		double[] yValues = new double[] { 55, 65, 78, 56, 67, 56, 67 };
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
