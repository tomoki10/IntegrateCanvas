package com.example.canvastest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.canvastest.R.id;


public class CanvasActivity extends Activity {

	String functionChar="";

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        //setContentView(new CanvasBasicView(this));
        setContentView(R.layout.activity_canvas);
        // テキストビューのテキストを設定します
        Button btn = (Button)findViewById(id.button1);
    }
    
    //ここに関数がテキストボックスに入力された際のデータ受け渡しを記述する
    public void doAction(View view){
        Log.d("TAAAAAG", "doACTION!!!");
        Intent intent = new Intent(CanvasActivity.this, FunctionInput.class);
        intent.putExtra("Input Func","3x^2");
        startActivityForResult(intent, 1);
    }


    //関数入力画面から関数値を受け取る
    @Override
    protected void onActivityResult(int requestCode, int resCode, Intent backIntent) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resCode, backIntent);
		Log.d("TAG", "Activity Result");
		switch(resCode){
			case Activity.RESULT_OK:
				functionChar = backIntent.getCharSequenceExtra("function").toString();
				BaseLineView.setFunction(functionChar);
				Log.d("TAG","RESULT_OK");
				Log.d("TAG",functionChar);
				break;
			case Activity.RESULT_CANCELED:
				Log.d("TAG","RESULT_CANCELED");
				break;
		}
	}

}
