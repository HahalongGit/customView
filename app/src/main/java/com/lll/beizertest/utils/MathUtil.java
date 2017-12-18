package com.lll.beizertest.utils;

public class MathUtil {
	/**
	 * 两个点的距离
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public static double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.abs(x1 - x2) * Math.abs(x1 - x2)
				+ Math.abs(y1 - y2) * Math.abs(y1 - y2));
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static double pointTotoDegrees(double x, double y) {
		return Math.toDegrees(Math.atan2(x, y));
	}

	/**
	 * 点是否在圆内
	 * @param sx 点的x
	 * @param sy 点的y
	 * @param r 半径
	 * @param x 圆心x
	 * @param y 圆心y
	 * @return
	 */
	public static boolean checkInRound(float sx, float sy, float r, float x,
			float y) {
		// x的平方 + y的平方 开根号 < 半径
		return Math.sqrt((sx - x) * (sx - x) + (sy - y) * (sy - y)) < r;
	}
}