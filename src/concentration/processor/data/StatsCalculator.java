package concentration.processor.data;
public class StatsCalculator {
	
	public static double min(double[] arr) {
		if(arr.length > 0) {
			double min = arr[0];
			for (double i: arr) {
				if (i < min) {
					min = i;
				}
			}
			return min;
		} else {
			return 0;
		}
	}
	
	public static double max(double[] arr) {
		if(arr.length > 0) {
			double max = arr[0];
			for (double i: arr) {
				if (i > max) {
					max = i;
				}
			}
		return max;
		} else {
			return 0;
		}
	}
	
	public static double avg(double[] arr) {
		if(arr.length > 0) {
			double sum = 0;
			for (double i: arr) {
				sum += i;
			}
			return sum / (arr.length);
		} else {
			return 0;
		}
	}
}
