package kusix.myflowers.fragment;

import kusix.myflowers.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public abstract class BaseInfoFragment extends Fragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_info, container,
				false);
		setDataIcon(rootView);
		setDataUnit(rootView);
		setChartView(container, rootView);
		setTips(rootView);
		return rootView;
	}

	private void setDataUnit(View rootView) {
		TextView dataUnitView = (TextView)rootView.findViewById(R.id.unit_text);
		dataUnitView.setText(getDataUnit());
	}

	private void setTips(View rootView) {
		TextView textView = (TextView)rootView.findViewById(R.id.tips);
		textView.setText(getTipsText());
	}

	private void setChartView(ViewGroup container, View rootView) {
		LinearLayout cl = (LinearLayout)rootView.findViewById(R.id.chart_layout);
		cl.addView(getChartView(container.getContext()));
	}

	private void setDataIcon(View rootView) {
		ImageView infoIconView = (ImageView)rootView.findViewById(R.id.data_icon);
		Drawable infoIcon = getResources().getDrawable(getInfoIconID());
		infoIconView.setImageDrawable(infoIcon);
	}
	

	protected abstract String getDataUnit();

	protected abstract int getInfoIconID();

	protected abstract  String getTipsText();

	protected abstract  View getChartView(Context context);

}
