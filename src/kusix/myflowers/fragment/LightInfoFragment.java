package kusix.myflowers.fragment;

import java.util.HashMap;
import java.util.Map;

import kusix.myflowers.R.drawable;
import kusix.myflowers.R.string;
import kusix.myflowers.chart.LineChartBuilder;
import android.content.Context;
import android.view.View;

public class LightInfoFragment extends BaseInfoFragment {

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
		return  "绿萝的原始生长条件是参天大树遮蔽的树林中，向阳性并不强。但在秋冬季的北方，为补充温度及光合作用的不足，却应增大它的光照度。方法是把绿萝摆放到室内光照最好的地方，或在正午时搬到密封的阳台上晒太阳。";
		}

	@Override
	protected View getChartView(Context context) {
		String title = "一周光照";
		double[] xValues = new double[] { 1, 2, 3, 4, 5, 6, 7 };
		double[] yValues = new double[] { 80, 65, 85, 78, 67, 56, 75 };
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
