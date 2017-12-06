package com.lll.beizertest.view;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * Created by longlong on 2017/11/21.
 *
 * @ClassName: LoveTypeEvalautor
 * @Description:
 * @Date 2017/11/21
 */

public class LoveTypeEvalautor implements TypeEvaluator<PointF> {

    private PointF p1,p2;

    public LoveTypeEvalautor(PointF p1, PointF p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public PointF evaluate(float t, PointF p0, PointF p3) {
        PointF point = new PointF();
        point.x = p0.x*(1-t)*(1-t)*(1-t) + 3*p1.x*t*(1-t)*(1-t)+3*p2.x*t*t*(1-t)+p3.x*t*t*t;
        point.y = p0.y*(1-t)*(1-t)*(1-t) + 3*p1.y*t*(1-t)*(1-t)+3*p2.y*t*t*(1-t)+p3.y*t*t*t;
        //t [0,1]
        //套公式写曲线 ：
        return point;
    }
}
