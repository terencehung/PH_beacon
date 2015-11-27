package com.terence.Demo.radar.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uk.co.alt236.btlescan.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


/**
 * @ClassName：RadarView
 * @Description：TODO<雷達掃描視圖>
 * @author：zihao
 * @date：2015年11月11日 上午12:26:11
 * @version：v1.1
 */
@SuppressLint("DrawAllocation")
public class RadarView extends View {

    private Context mContext;
    private boolean isSearching = false;// 標識是否處於掃描狀態,默認為不在掃描狀態
    private Paint mPaint;// 畫筆
    private Bitmap mScanBmp;// 執行掃描運動的圖片
    private int mOffsetArgs = 0;// 掃描運動偏移量參數
    private Bitmap mDefaultPointBmp;// 標識設備的圓點-默認
    private Bitmap mLightPointBmp;// 標識設備的圓點-高亮
    private int mPointCount = 0;// 圓點總數
    private List<String> mPointArray = new ArrayList<String>();// 存放偏移量的map
    private Random mRandom = new Random();
    private int mWidth, mHeight;// 寬高
    int mOutWidth;// 外圓寬度(w/4/5*2=w/10)
    int mCx, mCy;// x、y軸中心點
    int mOutsideRadius, mInsideRadius;// 外、內圓半徑
    private final static String TAG="PHCOM";

    public RadarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
        init(context);
    }

    public RadarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init(context);
    }

    public RadarView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init(context);
    }

    /**
     * TODO<提前初始化好需要使用的對象,避免在繪製過程中多次初始化>
     * 
     * @return void
     */
    private void init(Context context) {
        mPaint = new Paint();
        this.mContext = context;
        this.mDefaultPointBmp = Bitmap.createBitmap(BitmapFactory
                .decodeResource(mContext.getResources(),
                        R.drawable.radar_default_point_ico));
        this.mLightPointBmp = Bitmap.createBitmap(BitmapFactory.decodeResource(
                mContext.getResources(), R.drawable.radar_light_point_ico));
    }

    /**
     * 測量視圖及其內容,以確定所測量的寬度和高度(測量獲取控件尺寸).
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 獲取控件區域寬高
        if (mWidth == 0 || mHeight == 0) {
            final int minimumWidth = getSuggestedMinimumWidth();
            final int minimumHeight = getSuggestedMinimumHeight();
            mWidth = resolveMeasured(widthMeasureSpec, minimumWidth);
            mHeight = resolveMeasured(heightMeasureSpec, minimumHeight);
            mScanBmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                    mContext.getResources(), R.drawable.radar_scan_img), mWidth
                    - mOutWidth, mWidth - mOutWidth, false);

            // 獲取x/y軸中心點
            mCx = mWidth / 2;
            mCy = mHeight / 2;

            // 獲取外圓寬度
            mOutWidth = mWidth / 10;

            // 計算內、外半徑
            mOutsideRadius = mWidth / 2;// 外圓的半徑
            mInsideRadius = (mWidth - mOutWidth) / 4 / 2;// 內圓的半徑,除最外層,其它圓的半徑=層數*insideRadius
            Log.d(TAG, " mInsideRadius :"+ mInsideRadius);
        }
    }

    /**
     * 繪製視圖--從外部向內部繪製
     */
    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        // 開始繪製最外層的圓
        mPaint.setAntiAlias(true);// 設置抗鋸齒
        mPaint.setStyle(Style.FILL);// 設置填充樣式
        mPaint.setColor(0xffB8DCFC);// 設置畫筆顏色
        // 1.開始繪製圓形
        canvas.drawCircle(mCx, mCy, mOutsideRadius, mPaint);

        // 開始繪製內4圓
        mPaint.setColor(0xff3278B4);
        canvas.drawCircle(mCx, mCy, mInsideRadius * 4, mPaint);

        // 開始繪製內3圓
        mPaint.setStyle(Style.STROKE);
        mPaint.setColor(0xff31C9F2);
        canvas.drawCircle(mCx, mCy, mInsideRadius * 3, mPaint);

        // 開始繪製內2圓
        canvas.drawCircle(mCx, mCy, mInsideRadius * 2, mPaint);

        // 開始繪製內1圓
        canvas.drawCircle(mCx, mCy, mInsideRadius * 1, mPaint);

        // 2.開始繪製對角線
        canvas.drawLine(mOutWidth / 2, mCy, mWidth - mOutWidth / 2, mCy, mPaint);// 繪製0°~180°對角線
        canvas.drawLine(mCx, mHeight - mOutWidth / 2, mCx, mOutWidth / 2,
                mPaint);// 繪製90°~270°對角線

        // 根據角度繪製對角線
        int startX, startY, endX, endY;
        double radian;

        // 繪製45°~225°對角線
        // 計算開始位置x/y坐標點
        radian = Math.toRadians((double) 45);// 將角度轉換為弧度
        startX = (int) (mCx + mInsideRadius * 4 * Math.cos(radian));// 通過圓心坐標、半徑和當前角度計算當前圓周的某點橫坐標
        startY = (int) (mCy + mInsideRadius * 4 * Math.sin(radian));// 通過圓心坐標、半徑和當前角度計算當前圓周的某點縱坐標
        // 計算結束位置x/y坐標點
        radian = Math.toRadians((double) 45 + 180);
        endX = (int) (mCx + mInsideRadius * 4 * Math.cos(radian));
        endY = (int) (mCy + mInsideRadius * 4 * Math.sin(radian));
        canvas.drawLine(startX, startY, endX, endY, mPaint);

        // 繪製135°~315°對角線
        // 計算開始位置x/y坐標點
        radian = Math.toRadians((double) 135);
        startX = (int) (mCx + mInsideRadius * 4 * Math.cos(radian));
        startY = (int) (mCy + mInsideRadius * 4 * Math.sin(radian));
        // 計算結束位置x/y坐標點
        radian = Math.toRadians((double) 135 + 180);
        endX = (int) (mCx + mInsideRadius * 4 * Math.cos(radian));
        endY = (int) (mCy + mInsideRadius * 4 * Math.sin(radian));
        canvas.drawLine(startX, startY, endX, endY, mPaint);

        // 3.繪製掃描扇形圖
        canvas.save();// 用來保存Canvas的狀態.save之後，可以調用Canvas的平移、放縮、旋轉、錯切、裁剪等操作.

        if (isSearching) {// 判斷是否處於掃描
            Log.d(TAG, "search....");
            canvas.rotate(mOffsetArgs, mCx, mCy);// 繪製旋轉角度,參數一：角度;參數二：x中心;參數三：y中心.
            canvas.drawBitmap(mScanBmp, mCx - mScanBmp.getWidth() / 2, mCy
                    - mScanBmp.getHeight() / 2, null);// 繪製Bitmap掃描圖片效果
            mOffsetArgs += 3;
       
        } else {
            canvas.drawBitmap(mScanBmp, mCx - mScanBmp.getWidth() / 2, mCy
                    - mScanBmp.getHeight() / 2, null);
        }

        // 4.開始繪製動態點
        canvas.restore();// 用來恢復Canvas之前保存的狀態.防止save後對Canvas執行的操作對後續的繪製有影響.

        if (mPointCount > 0) {// 當圓點總數>0時,進入下一層判斷

            if (mPointCount > mPointArray.size()) {// 當圓點總數大於存儲坐標點數目時,說明有增加,需要重新生成隨機坐標點
                int mx = mInsideRadius + mRandom.nextInt(mInsideRadius * 6);
                int my = mInsideRadius + mRandom.nextInt(mInsideRadius * 6);
                mPointArray.add(mx + "/" + my);
                Log.d(TAG, "現在座標 : "+mx + "/" + my);
            }

            // 開始繪製坐標點
            for (int i = 0; i < mPointArray.size(); i++) {
                String[] result = mPointArray.get(i).split("/");

                // 開始繪製動態點
                if (i < mPointArray.size() - 1){
                  
//                    canvas.drawBitmap(mDefaultPointBmp,
//                            Integer.parseInt(result[0]),
//                            Integer.parseInt(result[1]), null);
                Log.d(TAG, "現在座標 畫到黑點");}
                else{
                    Log.d(TAG, "現在座標 畫到輛點");
                    canvas.drawBitmap(mLightPointBmp,
                            Integer.parseInt(result[0]),
                            Integer.parseInt(result[1]), null);
            }}
        }

        if (isSearching)
            this.invalidate();
    }

    /**
     * TODO<設置掃描狀態>
     * 
     * @return void
     */
    public void setSearching(boolean status) {
        this.isSearching = status;
        this.invalidate();
    }

    /**
     * TODO<新增動態點>
     * 
     * @return void
     */
    public void addPoint() {
        mPointCount++;
        this.invalidate();
    }

    /**
     * TODO<解析獲取控件寬高>
     * 
     * @return int
     */
    private int resolveMeasured(int measureSpec, int desired) {
        int result = 0;
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (MeasureSpec.getMode(measureSpec)) {
        case MeasureSpec.UNSPECIFIED:
            result = desired;
            break;
        case MeasureSpec.AT_MOST:
            result = Math.min(specSize, desired);
            break;
        case MeasureSpec.EXACTLY:
        default:
            result = specSize;
        }
        return result;
    }
}