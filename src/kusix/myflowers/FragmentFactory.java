package kusix.myflowers;

import kusix.myflowers.fragment.AirHumidityInfoFragment;
import kusix.myflowers.fragment.LightInfoFragment;
import kusix.myflowers.fragment.MeFragment;
import kusix.myflowers.fragment.SoilMoistureInfoFragment;
import kusix.myflowers.fragment.TempInfoFragment;
import android.support.v4.app.Fragment;

public class FragmentFactory {
	
	private static FragmentFactory instance = new FragmentFactory();
	
	private TempInfoFragment tempInfoFragment;
	private AirHumidityInfoFragment airHumidityInfoFragment;
	private SoilMoistureInfoFragment soilMoistureInfoFragment;
	private LightInfoFragment lightInfoFragment;
	private MeFragment meFragment;
	
	
	private FragmentFactory(){
		tempInfoFragment = new TempInfoFragment();
		airHumidityInfoFragment = new AirHumidityInfoFragment();
		soilMoistureInfoFragment = new SoilMoistureInfoFragment();
		lightInfoFragment = new LightInfoFragment();
		meFragment = new MeFragment();
	}
	
	public static FragmentFactory getInstance(){
		return instance;
	}

	public Fragment getFragmentByID(int checkedId) {
		switch(checkedId){
			case R.id.btn_temp:
				return tempInfoFragment;
			case R.id.btn_airHumidity:
				return airHumidityInfoFragment;
			case R.id.btn_soilMoisture:
				return soilMoistureInfoFragment;
			case R.id.btn_light:
				return lightInfoFragment;
			case R.id.btn_me:
				return meFragment;
			default:
				return meFragment;	
		}
	}

}
