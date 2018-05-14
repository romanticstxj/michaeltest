package jsi;

public class Utils {
	
	public static float min(float[] arr){
		float min = Float.MAX_VALUE;
		for(float ele: arr){
			if(ele < min){
				min = ele;
			}
		}
		return min;
	}
	
	public static float max(float[] arr){
		float max = Float.MIN_VALUE;
		for(float ele: arr){
			if(ele > max){
				max = ele;
			}
		}
		return max;
	}
}
