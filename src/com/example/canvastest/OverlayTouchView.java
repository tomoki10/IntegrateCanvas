package com.example.canvastest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;

public class OverlayTouchView extends View {

	 //タッチイベント時の座標
    private int touchPlot = 0;
    private Paint touchPaint = new Paint();
    
	final Matrix mMatrix = new Matrix();
	
	//拡大倍率
    private float scaling = 1.0f;
    private ScaleGestureDetector _geScaleGestureDetector;
    
    //コンストラクタでジェスチャーインスタンスを生成する
	public OverlayTouchView(Context context) {
		super(context);
		_geScaleGestureDetector = new ScaleGestureDetector(context, _simpleListener);
	}
	public OverlayTouchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		_geScaleGestureDetector = new ScaleGestureDetector(context, _simpleListener);
	}
	public OverlayTouchView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		_geScaleGestureDetector = new ScaleGestureDetector(context, _simpleListener);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//タッチ時のライン描画
		int height = getHeight();
		touchPaint.setColor(Color.BLUE);
		touchPaint.setStrokeWidth(4);
        canvas.drawLine(touchPlot, 0, touchPlot, height, touchPaint);
	}
	
	//関数のメモリ間隔に合わせるために値を補正するメソッド
	private int RoundingError50(int num){
		int lastTwoDigits=0;
		//線の位置を補正する
    	if(!(num%50==0)){
    		//下2桁を取り出す
    		lastTwoDigits = num%100;
    		if(lastTwoDigits>=75){
    			//繰り上げる
    			num=num-lastTwoDigits+100;
    		}else if(lastTwoDigits<25){
    			//切り捨てる
    			num=num-lastTwoDigits;
    		}else if((lastTwoDigits>=25 && lastTwoDigits<50)||(lastTwoDigits>50 && lastTwoDigits<75)){
    			//50単位に揃える
    			num=num-lastTwoDigits+50;
    		}
    	}
		return num;
	}
	
	
	
	@Override
    public boolean onTouchEvent(MotionEvent event){
		
		if(event.getPointerCount()==2){
			_geScaleGestureDetector.onTouchEvent(event);
			invalidate();
			Log.d("TAGDGDGDHDJD", ""+scaling);
		}else{
	    	switch (event.getAction()) {
	    	//タッチ押下
	        case MotionEvent.ACTION_DOWN:
	        	this.touchPlot=(int)event.getX();
	        	//線の位置を補正する
	        	touchPlot=RoundingError50(touchPlot);
	        	this.postInvalidate();
	            Log.d("TouchEvent", "getAction()" + "ACTION_DOWN");
	            break;
	        //指を離したとき
	        case MotionEvent.ACTION_UP:
	            Log.d("TouchEvent", "getAction()" + "ACTION_UP");
	            break;
	        //指をスライド
	        case MotionEvent.ACTION_MOVE:
	        	//this.touchPlot=(int)event.getX();
	        	//this.postInvalidate();
	            Log.d("TouchEvent", "getAction()" + "ACTION_MOVE");
	            break;
	        //up+downの同時発生cancel
	        case MotionEvent.ACTION_CANCEL:
	            Log.d("TouchEvent", "getAction()" + "ACTION_CANCEL");
	            break;
	        }
		}
		return false;
    }
	

    //ジェスチャーリスナークラス
    private SimpleOnScaleGestureListener _simpleListener
    = new ScaleGestureDetector.SimpleOnScaleGestureListener() {
    	
        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            invalidate();
            return super.onScaleBegin(detector);
        }
 
        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            invalidate();
            super.onScaleEnd(detector);
        }
        //画像の拡大縮小をピンチイン・アウトで制御
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
        	//Log.d("OREO",Float.toString(detector.getScaleFactor()));
        	scaling *= detector.getScaleFactor();
        	//Log.d("OREO2",Float.toString(scaling));
            invalidate();
            return true;
        };
    };

}
