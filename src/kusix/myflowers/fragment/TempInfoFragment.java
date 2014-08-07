package kusix.myflowers.fragment;

import java.util.HashMap;
import java.util.Map;

import kusix.myflowers.R.drawable;
import kusix.myflowers.R.string;
import kusix.myflowers.chart.RangeChartBuilder;
import android.content.Context;
import android.view.View;

public class TempInfoFragment extends BaseInfoFragment {

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
		return  "绿萝最适宜的生长温度为白天20℃ ～ 28℃，晚上15℃ ～ 18℃。冬季只要室内温度不低于10℃，绿萝即能安全越冬，如温度低于5℃，易造成落叶，影响生长。生长期适时喷施壮茎灵，可使植物杆茎粗壮、叶片肥厚、叶色鲜嫩、植株茂盛。";
	}

	@Override
	protected View getChartView(Context context) {
		String title = "一周气温";
		double[] minValues = new double[] { 15, 17, 18, 18, 20, 21, 11 };
		double[] maxValues = new double[] { 27, 28, 30, 31, 33, 35, 28 };
		int xMin = 0;
		int xMax = 8;
		int yMin = 10;
		int yMax = 40;
		Map<Double,String> xTextLabels = new HashMap<Double,String>();
		xTextLabels.put(1d, "7/3");
		xTextLabels.put(2d, "7/4");
		xTextLabels.put(3d, "7/5");
		xTextLabels.put(4d, "7/6");
		xTextLabels.put(5d, "7/7");
		xTextLabels.put(6d, "7/8");
		xTextLabels.put(7d, "7/9");
		
		return RangeChartBuilder.build(context, title, minValues, maxValues, xTextLabels);
	}

}
