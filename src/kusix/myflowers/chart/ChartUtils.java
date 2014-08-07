package kusix.myflowers.chart;

public class ChartUtils {
	
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
	
	

}
