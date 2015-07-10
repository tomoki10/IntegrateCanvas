package com.example.canvastest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.math.BigDecimal;

@SuppressLint("DrawAllocation") public class BaseLineView extends View {
	//関数の実際の画面上の座標間隔
	private final int space = 50;
	//点線の幅
    private final DashPathEffect dPathEffect = new DashPathEffect(new float[]{ 10.0f, 10.0f }, 0);
	//
    private Paint mPaint = new Paint();
    private Paint funcPaint = new Paint();
    private Path mPath = new Path();
    
//    private Path[] integralPath = { new Path(),
//    								new Path(),
//    								new Path(),
//    								new Path()  };
    private Path[] integralPath =  {new Path(), new Path()};
    private Paint[] integralPaint = {new Paint(), new Paint()};
    //private Path[] integralPath;
    //private Paint[] integralPaint;
    private BigDecimal bi;	//切り捨て用
    private float[] domainPoint = new float[2];  //積分表示時のTMPポイント
    
    //コンストラクタ
    public BaseLineView(Context context,AttributeSet attrs,int defStyle) {
        super(context,attrs,defStyle);
    }
    public BaseLineView(Context context) {
        super(context);
    }
    public BaseLineView(Context context,AttributeSet attrs){
    	super(context, attrs);
    	
    }
    
    //メモリは画面上のspaceで1単位
    //原点は(widthHalf,heightHalf)
    //幅と座標の間隔の数値が乖離しているので要修正
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    	//画面サイズの取得
    	int width = getWidth();
    	int widthHalf = width/2;
    	int height = getHeight();
    	int heightHalf = height/2;
    	//Canvasのスタイル設定
    	mPaint.setTextSize(20);
    	mPaint.setStyle(Style.STROKE);
    	//y軸
        canvas.drawLine(widthHalf, 0, widthHalf, height, mPaint);
        //x軸
        canvas.drawLine(0, heightHalf, width, heightHalf, mPaint);
        //y軸メモリ
        int yAxis=0;
        int memTextY=1;
        //メモリ上の0の表示
    	canvas.drawText("0", widthHalf-10, heightHalf-yAxis, mPaint);
    	yAxis=yAxis+space;
        while(yAxis<heightHalf){
        	//正
        	canvas.drawLine(widthHalf-40, heightHalf-yAxis, widthHalf+40, heightHalf-yAxis, mPaint);
        	//点線部分
        	dotsLine(0, heightHalf-yAxis, width,heightHalf-yAxis);
        	canvas.drawPath(mPath, mPaint);
        	//メモリの値
        	canvas.drawText(String.valueOf(memTextY), widthHalf-40, heightHalf-yAxis, mPaint);
        	//負
        	canvas.drawLine(widthHalf-40, heightHalf+yAxis, widthHalf+40, heightHalf+yAxis, mPaint);
        	dotsLine(0, heightHalf+yAxis, width,heightHalf+yAxis);
        	canvas.drawPath(mPath, mPaint);
        	canvas.drawText("-"+String.valueOf(memTextY), widthHalf-40, heightHalf+yAxis, mPaint);
           	memTextY++;
        	yAxis=yAxis+space;
        }
        //x軸メモリ
        int xAxis=space;
        int memTextX=1;
        while(xAxis<widthHalf){
        	//負
        	canvas.drawLine(widthHalf-xAxis, heightHalf-40, widthHalf-xAxis, heightHalf+40, mPaint);
        	//点線部分
        	dotsLine(widthHalf-xAxis, 0, widthHalf-xAxis,height);
        	canvas.drawPath(mPath, mPaint);
        	canvas.drawText("-"+String.valueOf(memTextX), widthHalf-xAxis-20, heightHalf-20, mPaint);
        	//正
        	canvas.drawLine(widthHalf+xAxis, heightHalf-40, widthHalf+xAxis, heightHalf+40, mPaint);
        	//点線部分
        	dotsLine(widthHalf+xAxis, 0, widthHalf+xAxis,height);
        	canvas.drawPath(mPath, mPaint);
        	canvas.drawText(String.valueOf(memTextX), widthHalf+xAxis-20, heightHalf-20, mPaint);
        	memTextX++;
        	xAxis=xAxis+space;
        }
        
        
        
        /*		関数の描画		*/
        //canvas.drawPoint(X座標, y座標, paint);
        funcPaint.setStyle(Style.STROKE);
        funcPaint.setStrokeWidth(3);
        funcPaint.setColor(Color.BLACK);
        //i=1,i*2=2
        float plotData[] = new float[2];
        //関数の表示テスト(y=ax)
        float tmpData[] = new float[2];
        float i = -widthHalf;
        
        //範囲の決定
        float domainFirst=-20;
        float domainLast=20.01f;
        
        i=domainFirst;
        
        //canvas.drawPoint(domainFirst, heightHalf, funcPaint);
        
        //関数値の保持
        float funcI,inputI;
        int currentIntegral=0;
        //初期位置
        tmpData = plotTranslation(width, height, i * space, (float) Math.sin(i) * space); //(float)(Math.pow(i, 2))

        while(i<=domainLast){
        	//関数値の設定
        	inputI=i;
        	funcI = (float)Math.sin(i);
        	
        	plotData = plotTranslation(width, height, inputI * space, funcI * space);
        	funcPaint.setAntiAlias(true);
        	canvas.drawLine(tmpData[0], tmpData[1], plotData[0], plotData[1], funcPaint);
        	i=i+00.1f;
        	
        	//描画する色の決定
        	if(funcI>0){
        		currentIntegral=0;
        		integralPaint[currentIntegral].setColor(Color.argb(1, 0, 0, 255));
        	}else if(funcI<0){
        		currentIntegral=1;
        		integralPaint[currentIntegral].setColor(Color.argb(1, 255, 0, 0));
        	}

    		integralPath[currentIntegral].moveTo(tmpData[0],tmpData[1]);
        	integralPath[currentIntegral].lineTo(tmpData[0],heightHalf);
        	integralPath[currentIntegral].lineTo(plotData[0],heightHalf);
        	integralPath[currentIntegral].lineTo(plotData[0],plotData[1]);
        	integralPath[currentIntegral].lineTo(tmpData[0],tmpData[1]);
        	
    		canvas.drawPath(integralPath[currentIntegral],integralPaint[currentIntegral]);
        	
    		//次のPathを描画するための値の保管
        	tmpData=plotData;
        	//integralPath[currentIntegral].lineTo(tmpData[0], tmpData[1]);
        	
        }
        //積分部分
        integralPaint[0].setStyle(Paint.Style.FILL);
        integralPaint[1].setStyle(Paint.Style.FILL);
        Log.d("TAGcurrentIntegral",""+currentIntegral);
    }
    
    /*
     * 中心を原点とした座標系から左上を原点とした座標系への変換
     * (幅,高さ,x座標,y座標)
     */
    private float[] plotTranslation(int width, int height, float x, float y){
    	float plotData[] = new float[2];
    	//1次関数の場合
		plotData[0]=width/2+x;
		plotData[1]=height/2-y;
		//2次関数の場合
		
		
    	return plotData;
    }
    
    
    /*
     * 点線用メソッド
     */
    private void dotsLine(int startX, int startY, int endX, int endY){
    	mPaint.setPathEffect(dPathEffect);
    	mPath.moveTo(startX, startY);
    	mPath.lineTo(endX,endY);
    }

	public static void setFunction(String funcStr){
		Log.d("FuncStr",funcStr);
	}

	private float outFunctionPlot(float input){

		return input;
	}

    
}
/*
//積分色の分岐
bi = new BigDecimal(String.valueOf(funcI));
if( bi.setScale(2,BigDecimal.ROUND_DOWN).floatValue()==0){		//コレではだめ
	Log.d("TAG","aaaaa");
	//積分色の決定(前の座標との差分で決定)
	//関数を閉める
	if(plotData[1] - tmpData[1] < 0){		//正の積分
		integralPaint[currentIntegral].setColor(Color.argb(31, 255, 0, 0));
		integralPath[currentIntegral].lineTo(domainPoint[0], heightHalf);
		integralPath[currentIntegral].lineTo(domainPoint[0], domainPoint[1]);
		Log.d("TAG","正 i="+i);
		
	}else if(plotData[1] - tmpData[1] > 0){ //負の積分
		integralPaint[currentIntegral].setColor(Color.argb(31, 0, 0, 255));
		integralPath[currentIntegral].lineTo(plotData[0], heightHalf);
		integralPath[currentIntegral].lineTo(domainPoint[0], domainPoint[1]);
		Log.d("TAG","負");
	}
	//境界点の更新
	domainPoint=plotData;
	currentIntegral++;
	//新しい線での描画の開始
	integralPath[currentIntegral].lineTo(plotData[0], plotData[1]);
}

*/
