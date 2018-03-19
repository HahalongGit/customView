package com.lll.beizertest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.lll.beizertest.utils.MathUtil;

import java.util.ArrayList;

/**
 * Created by longlong on 2017/12/15.
 *
 * @ClassName: LuckPatternView
 * @Description: 九宫格解锁实现思路
 * 1.初始化画笔和格子，以及不同的颜色值，在屏幕中按照屏幕的宽在中心取出一个正方形，然后再这个区域内平分为9个格子，
 * 也就是创建9个点保存在一个3*3的集合中，分别计算出点的位置
 * 2.初始绘制9个格子的内外圆，圆心就是格子的中心，创建一个类保存点的状态，默认的，选中的，错误的以及点的位置数据
 * 这里去屏幕的宽度是正方形的边长
 * 3.处理onTouch 的时候，循环判断是不是点到了九宫格的圆，改变状态，MOVE的之后也判断，把选中的记录在一个选中的集合中。
 * 然后重新绘制
 * 4.绘制链接线，链接线不是重圆心开始的，是重内圆开始，计算出每一个内圆对应的那个点（三角函数），计算两个点的距离，然后绘制Line，起点和终点
 * 5.绘制三角形，通过Path 绘制，在指定的连线的位置。
 * 6.绘制错误的链接线。错误的时候改变点Point的状态为ERROR,然后重新绘制，持续一定时间，清空集合和页面效果。
 * 7.UP的时候判断错误，错误设置当前选中的状态为错误重新绘制点和线，然后清除选中的点，设置九个格子的状态为正常，回掉密码给用户,
 *
 * 8.绘制三角形 指示箭头，绘制一个正三角形 位置可以，绘制完成采用矩阵旋转的方式对三角形的位置进行处理。
 * 绘制三角形参考:https://github.com/sym900728/LockPattern  中部分代码
 * @Date 2017/12/15
 */

public class LuckPatternView extends View {

    /**
     * 初始化画笔
     */
    private Paint mNormalPaint, mPressedPaint, mErrorPint, mLinePaint, mArrowsPaint;

    /**
     * 正常的外圆颜色
     */
    private int NormalInnerCircleColor = Color.parseColor("#717172");
    /**
     * 正常的内圆颜色
     */
    private int NormalOuterCircleColor = Color.parseColor("#afafb0");

    /**
     * 按下的内圆颜色
     */
    private int PressedInnerCircleColor = Color.parseColor("#4db2c8");
    /**
     * 按下的外圆颜色
     */
    private int PressedOuterCircleColor = Color.parseColor("#66dfe8");

    /**
     * 错误的内圆颜色
     */
    private int ErrorInnerCircleColor = Color.parseColor("#ff0000");
    /**
     * 错误的外圆颜色
     */
    private int ErrorOuterCircleColor = Color.parseColor("#fb2c2c");

    /**
     * 正常的连接线颜色
     */
    private int NormalLineColor = Color.parseColor("#4db2c8");
    /**
     * 错误的连接线颜色
     */
    private int ErrorLineColor = Color.parseColor("#ff0000");

    /**
     * 屏幕宽度
     */
    private int mWidth;

    /**
     * 屏幕高度
     */
    private int mHeight;

    /**
     * 宫格宽度
     */
    private int mSquareWidth;

    private Point mPoints[][];

    /**
     * Y轴开始的位置
     */
    private int mOffsetY;

    /**
     * 外圆你的半径
     */
    private int mDotRadius;
    /**
     * 是否按下
     */
    private boolean isTouched;

    /**
     * 是否已经初始化Point
     */
    private boolean isInited;

    /**
     * 选中的点集合
     */
    private ArrayList<Point> mSelectedPoints = new ArrayList<>();

    /**
     * 当前手指的位置X
     */
    private float moveX = 0f;
    /**
     * 当前手指的位置Y
     */
    private float moveY = 0f;

    private OnDrawPasswordListener onDrawPasswordListener;

    private Path trianglePath;
    private Matrix triangleMatrix;

    /**
     * 保存的密码
     */
    private String savePassword = "123456";
    /**
     * 设置密码回调
     * @param onDrawPasswordListener
     */
    public void setOnDrawPasswordListener(OnDrawPasswordListener onDrawPasswordListener) {
        this.onDrawPasswordListener = onDrawPasswordListener;
    }

    /**
     * 密码错误码
     */
    private final int MESSAGE_ERROR_CODE = 1001;

    private static final double CONSTANT_COS_30 = Math.cos(Math.toRadians(30));

    /**
     * 延时重置九宫格
     */
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_ERROR_CODE:{
                    mSelectedPoints.clear();
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            Point point = mPoints[i][j];
                            point.setCurrentStatus(Point.NORMAL_STATUS);
                        }
                    }
                    invalidate();
                    break;
                }
            }
        }
    };

    public LuckPatternView(Context context) {
        this(context, null);
    }

    public LuckPatternView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LuckPatternView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private void initPaint() {
        /**
         * 正常的
         */
        mNormalPaint = new Paint();
        mNormalPaint.setAntiAlias(true);
        mNormalPaint.setColor(NormalOuterCircleColor);
        mNormalPaint.setStyle(Paint.Style.STROKE);
        mNormalPaint.setStrokeWidth(mDotRadius / 9f);

        /**
         * 按下的画笔
         */
        mPressedPaint = new Paint();
        mPressedPaint.setAntiAlias(true);
        mPressedPaint.setColor(PressedOuterCircleColor);
        mPressedPaint.setStyle(Paint.Style.STROKE);
        mPressedPaint.setStrokeWidth(mDotRadius / 9f);

        //错误的画笔
        mErrorPint = new Paint();
        mErrorPint.setAntiAlias(true);
        mErrorPint.setColor(ErrorOuterCircleColor);
        mErrorPint.setStyle(Paint.Style.STROKE);
        mErrorPint.setStrokeWidth(mDotRadius / 6f);

        //连接线画笔
        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setColor(NormalLineColor);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(mDotRadius / 9f);

        //三角形画笔
        mArrowsPaint = new Paint();
        mArrowsPaint.setAntiAlias(true);
        mArrowsPaint.setColor(NormalLineColor);
        mArrowsPaint.setStyle(Paint.Style.FILL);
        mArrowsPaint.setStrokeWidth(mDotRadius / 9f);

        triangleMatrix = new Matrix();
        trianglePath = new Path();

    }


    /**
     * 初始化点
     */
    private void initPoint() {
        mWidth = getWidth();
        mHeight = getHeight();
        mDotRadius = mWidth / 12;
        mOffsetY = (mHeight - mWidth) / 2;
        mSquareWidth = mWidth / 3;

        mPoints = new Point[3][3];
        mPoints[0][0] = new Point(mSquareWidth / 2, mOffsetY + mSquareWidth / 2, 0);
        mPoints[0][1] = new Point(mSquareWidth / 2 * 3, mOffsetY + mSquareWidth / 2, 1);
        mPoints[0][2] = new Point(mSquareWidth / 2 * 5, mOffsetY + mSquareWidth / 2, 2);
        mPoints[1][0] = new Point(mSquareWidth / 2, mOffsetY + mSquareWidth / 2 * 3, 3);
        mPoints[1][1] = new Point(mSquareWidth / 2 * 3, mOffsetY + mSquareWidth / 2 * 3, 4);
        mPoints[1][2] = new Point(mSquareWidth / 2 * 5, mOffsetY + mSquareWidth / 2 * 3, 5);
        mPoints[2][0] = new Point(mSquareWidth / 2, mOffsetY + mSquareWidth / 2 * 5, 6);
        mPoints[2][1] = new Point(mSquareWidth / 2 * 3, mOffsetY + mSquareWidth / 2 * 5, 7);
        mPoints[2][2] = new Point(mSquareWidth / 2 * 5, mOffsetY + mSquareWidth / 2 * 5, 8);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e("TAG","onLayout-width:"+getWidth());
        Log.e("TAG","onLayout-height:"+getHeight());
        //View 的绘制流程中onLayout后获取宽高
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        moveX = event.getX();
        moveY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                checkCurrentPoint();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (isTouched) {
                    checkCurrentPoint();
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                isTouched = false;
                StringBuilder ss = new StringBuilder();
                for(int i=0;i<mSelectedPoints.size();i++){
                    ss.append(mSelectedPoints.get(i).getIndex());
                }
                Log.e("TAG","onPasswordDrawSuccess:"+ss.toString());
                if(!ss.toString().equals(savePassword)){
                    //设置选中的点状态为错误
                    for(Point point:mSelectedPoints){
                        point.setCurrentStatus(Point.ERROR_STATUS);
                    }
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            Point point = mPoints[i][j];
                            if(point.getCurrentStatus()== Point.PRESSED_STATUS){
                                point.setCurrentStatus(Point.ERROR_STATUS);
                            }
                        }
                    }
                    mHandler.sendEmptyMessageDelayed(MESSAGE_ERROR_CODE,1000);
                }else {
                    // TODO: 2017/12/18  验证密码成功

                }
                //设置所有的点中选中的为错误状态
                if(onDrawPasswordListener!=null){
                    onDrawPasswordListener.onPasswordDrawSuccess(ss.toString());
                }
                break;
            }
        }
        invalidate();
        return true;
    }

//    /**
//     * draw new triangle
//     * @param preCell
//     * @param nextCell
//     * @param canvas
//     * @param paint
//     */
//    private void drawNewTriangle(Point preCell, Point nextCell, Canvas canvas, Paint paint) {
//        float innerRadius = (int) (mDotRadius/3f);
//        float distance = (float) MathUtil.distance(preCell.getPointX(), preCell.getPointY(), nextCell.getPointX(), nextCell.getPointY());
//        float x = preCell.getPointX();
////        float y = preCell.getPointY() - this.cellInnerRadius * 2;
//        float y = preCell.getPointY() - mDotRadius*2;
//
//        float x1 = x - innerRadius / 2;
//        float y1 = y + (float)(innerRadius * CONSTANT_COS_30);
//        float x2 = x + innerRadius / 2 ;
//        float y2 = y1;
//
//        float angleX = getAngleLineIntersectX(
//                preCell.getPointX(), preCell.getPointY(), nextCell.getPointX(), nextCell.getPointY(), distance);
//        float angleY = getAngleLineIntersectY(
//                preCell.getPointX(), preCell.getPointY(), nextCell.getPointX(), nextCell.getPointY(), distance);
//
//        trianglePath.reset();
//        trianglePath.moveTo(x, y);
//        trianglePath.lineTo(x1, y1);
//        trianglePath.lineTo(x2, y2);
//        trianglePath.close();
//        //slide right down and right up
//        if (angleX >= 0 && angleX <= 90 ) {
//            triangleMatrix.setRotate(180 - angleY, preCell.getPointX(), preCell.getPointY());
//        }
//        //slide left up and left down
//        else {
//            triangleMatrix.setRotate(angleY - 180, preCell.getPointX(), preCell.getPointY());
//        }
//        trianglePath.transform(triangleMatrix);
//        canvas.drawPath(trianglePath, paint);
//    }

    /**
     * draw new triangle
     * @param preCell
     * @param nextCell
     * @param canvas
     * @param paint
     */
    private void drawNewTriangle(Point preCell, Point nextCell, Canvas canvas, Paint paint) {
        float innerRadius = (int) (mDotRadius/3f);
        float distance = (float) MathUtil.distance(preCell.getPointX(), preCell.getPointY(), nextCell.getPointX(), nextCell.getPointY());
        float x = preCell.getPointX();
        float y = preCell.getPointY()-2*innerRadius;//移动开始位置（最后会旋转）

        float x1 = x - innerRadius/2;//除以2 就是sin30°
        float y1 = y + (float)(innerRadius * CONSTANT_COS_30); //三角形的边取值mRadius，两个点的Y值增大边的cos30°
        float x2 = x + innerRadius/2;
        float y2 = y1;
        //一个以第一个点为起始点，以mRadius 为三角形的边的正三角形，（Android中的X轴向右，Y轴向下为正）
        float angleX = getAngleLineIntersectX(
                preCell.getPointX(), preCell.getPointY(), nextCell.getPointX(), nextCell.getPointY(), distance);
        float angleY = getAngleLineIntersectY(
                preCell.getPointX(), preCell.getPointY(), nextCell.getPointX(), nextCell.getPointY(), distance);

        trianglePath.reset();
        trianglePath.moveTo(x, y);
        trianglePath.lineTo(x1, y1);
        trianglePath.lineTo(x2, y2);
        trianglePath.close();
        //slide right down and right up
        if (angleX >= 0 && angleX <= 90 ) {//矩阵 顺时针旋转
            triangleMatrix.setRotate(180 - angleY, preCell.getPointX(), preCell.getPointY());
        }
        //slide left up and left down
        else {//矩阵 逆时针旋转
            triangleMatrix.setRotate(angleY - 180, preCell.getPointX(), preCell.getPointY());
        }
        trianglePath.transform(triangleMatrix);//变换Path 上 矩阵的点
        canvas.drawPath(trianglePath, paint);
    }

    /**
     * get the angle which the line intersect x axis
     *
     * @param fpX
     * @param fpY
     * @param spX
     * @param spY
     * @param distance
     * @return degrees
     */
    public float getAngleLineIntersectX(float fpX, float fpY, float spX, float spY, float distance) {
        return (float) Math.toDegrees(Math.acos((spX - fpX) / distance));
    }

    public static float getAngleLineIntersectY(float fpX, float fpY, float spX, float spY, float distance) {
        return (float) Math.toDegrees(Math.acos((spY - fpY) / distance));
    }

    /**
     * onTouch 时检测设置点的状态
     */
    private void checkCurrentPoint() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Point point = mPoints[i][j];
                if (MathUtil.checkInRound(moveX, moveY, mDotRadius, point.pointX, point.pointY)) {
                    point.setCurrentStatus(Point.PRESSED_STATUS);
                    isTouched = true;
                    //添加过的Point不在添加
                    if (!mSelectedPoints.contains(point)) {
                        mSelectedPoints.add(point);
                    }
                }
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        if (!isInited) {
            isInited = true;
            initPoint();
            initPaint();
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Point point = mPoints[i][j];
                Log.e("TAG", "onDraw -point-stasus:" + point.getCurrentStatus());
                drawCirlce(canvas, point);
            }
        }

        drawLine(canvas);
        //drawArrows(canvas);
        drawNewTriangle(canvas);
    }

    /**
     * 绘制方向箭头
     * @param canvas
     */
    private void drawArrows(Canvas canvas) {
        float height = mDotRadius/5f;//三角形高
        // TODO: 2017/12/18 计算三个点
        Path path = new Path();
//        path.moveTo();
//        path.lineTo();
//        path.lineTo();
        path.close();
        canvas.drawPath(path,mArrowsPaint);

    }


    /**
     * 绘制所有的线
     * @param canvas
     */
    private void drawLine(Canvas canvas) {
        if (mSelectedPoints.size() >=1) {
            Point lastPoint = mSelectedPoints.get(0);
            for (int i = 1; i < mSelectedPoints.size(); i++) {
                Point point = mSelectedPoints.get(i);
                drawLine(canvas, lastPoint, point);
                lastPoint = point;
            }
            if (isTouched) {
                drawLine(canvas, lastPoint, new Point(moveX, moveY, -1));
            }
        }

    }

    /**
     * 绘制三角
     * @param canvas
     */
    private void drawNewTriangle(Canvas canvas) {
        if (mSelectedPoints.size() >=1) {
            Point lastPoint = mSelectedPoints.get(0);
            Log.e("TAG","drawNewTriangle-status:"+lastPoint.getCurrentStatus());
            for (int i = 1; i < mSelectedPoints.size(); i++) {
                Point point = mSelectedPoints.get(i);
                if(lastPoint.getCurrentStatus()==Point.PRESSED_STATUS){
                    mArrowsPaint.setColor(NormalLineColor);
                }else if(lastPoint.getCurrentStatus()==Point.ERROR_STATUS){
                    mArrowsPaint.setColor(ErrorLineColor);
                }
                drawNewTriangle(lastPoint,point,canvas,mArrowsPaint);
                lastPoint = point;
            }
        }

    }

    /**
     * 根据两个点画线
     * @param canvas
     * @param lastPoint
     * @param point
     */
    private void drawLine(Canvas canvas, Point lastPoint, Point point) {
        float distance = (float) MathUtil.distance(lastPoint.pointX, lastPoint.pointY, point.pointX, point.pointY);
        float offsetPointX = (point.pointX - lastPoint.pointX) / distance * (mDotRadius / 6f);//角的余弦值乘半径
        float offsetPointY = (point.pointY - lastPoint.pointY) / distance * (mDotRadius / 6f);//角的正弦值乘半径
        if(point.getCurrentStatus()==Point.NORMAL_STATUS){
            mLinePaint.setColor(NormalLineColor);
        }
        if(point.getCurrentStatus()==Point.ERROR_STATUS){
            mLinePaint.setColor(ErrorLineColor);
        }
        canvas.drawLine(lastPoint.pointX + offsetPointX, lastPoint.pointY + offsetPointY, point.pointX - offsetPointX, point.pointY - offsetPointY, mLinePaint);
    }

    /**
     * 根据点的状态画圆
     * @param canvas
     * @param point
     */
    private void drawCirlce(Canvas canvas, Point point) {
        if (point != null) {
            if (point.getCurrentStatus() == Point.NORMAL_STATUS) {
                //画外圆
                mNormalPaint.setColor(NormalOuterCircleColor);
                canvas.drawCircle(point.pointX, point.pointY, mDotRadius, mNormalPaint);
                //画内圆
                mNormalPaint.setColor(NormalInnerCircleColor);
                canvas.drawCircle(point.pointX, point.pointY, mDotRadius / 6f, mNormalPaint);
            }
            if (point.getCurrentStatus() == Point.PRESSED_STATUS) {
                //画外圆
                mNormalPaint.setColor(PressedOuterCircleColor);
                canvas.drawCircle(point.pointX, point.pointY, mDotRadius, mNormalPaint);
                //画内圆
                mNormalPaint.setColor(PressedInnerCircleColor);
                canvas.drawCircle(point.pointX, point.pointY, mDotRadius / 6f, mNormalPaint);
            }
            if (point.getCurrentStatus() == Point.ERROR_STATUS) {
                //画外圆
                mNormalPaint.setColor(ErrorOuterCircleColor);
                canvas.drawCircle(point.pointX, point.pointY, mDotRadius, mNormalPaint);
                //画内圆
                mNormalPaint.setColor(ErrorInnerCircleColor);
                canvas.drawCircle(point.pointX, point.pointY, mDotRadius / 6f, mNormalPaint);
            }
        }

    }

    /**
     *Point 类
     */
    class Point {
        public static final int NORMAL_STATUS = 1;
        public static final int PRESSED_STATUS = 2;
        public static final int ERROR_STATUS = 3;

        private int currentStatus = NORMAL_STATUS;

        public int getCurrentStatus() {
            return currentStatus;
        }

        public void setCurrentStatus(int status) {
            currentStatus = status;
        }

        private float pointX;
        private float pointY;

        public float getPointX() {
            return pointX;
        }

        public void setPointX(float pointX) {
            this.pointX = pointX;
        }

        public float getPointY() {
            return pointY;
        }

        public void setPointY(float pointY) {
            this.pointY = pointY;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        /**
         * index 作为密码
         */
        private int index;

        public Point(float pointX, float pointY, int index) {
            this.pointX = pointX;
            this.pointY = pointY;
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

    }

    /**
     * 绘制密码结果回调
     */
    public interface OnDrawPasswordListener{
        void onPasswordDrawSuccess(String password);
    }

}
