package com.example.canvastest;

import android.app.Activity;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;

public class FunctionInput extends Activity  implements KeyboardView.OnKeyboardActionListener{


	private EditText editText;
	private String editString="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//配置をここで指定(xmlで書くべき?)
		LinearLayout linearLayout = new LinearLayout(this);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		//setContentView(R.layout.activity_function_input);
		setContentView(linearLayout);
		editText = new EditText(this);
		linearLayout.addView(editText);

		//前Activityから関数の受け取り
		Intent i = getIntent();
		String func = i.getStringExtra("Input Func");
		editString = func;
		editText.setText(func);

		
		Keyboard keyboard = new Keyboard(this, R.xml.sample_keyboard);
		KeyboardView keyboardView = new KeyboardView(this,null);
		keyboardView.setKeyboard(keyboard);
		keyboardView.setOnKeyboardActionListener((OnKeyboardActionListener) this);
		linearLayout.addView(keyboardView);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.function_input, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onKey(int arg0, int[] arg1) {
		// TODO Auto-generated method stub
		
	}

	//キーボード入力の制御
	@Override
	public void onPress(int inputKey) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRelease(int inputKey) {
		Log.d("KeyLoger", Integer.toString(inputKey));
		byte[] ascii = new byte[]{(byte) inputKey};
		//削除キー処理
		if(inputKey==Keyboard.KEYCODE_DELETE ){
			if(editString.length()>0){
				editString= editString.substring(0, editString.length()-1);
				editText.setText(editString);
				editText.setSelection(editString.length());
			}
			return;
		}else if(inputKey==KeyEvent.KEYCODE_ENTER){
			//グラフ描画のActivityへ処理を戻す

			Intent intent = new Intent();
			intent.putExtra("function", editString);
			this.setResult(Activity.RESULT_OK,intent);
			this.finish();
			return;
		}
		try {
			editString =  editString + new String(ascii , "US-ASCII");
            editText.setText(editString);
            editText.setSelection(editString.length());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
	}

	@Override
	public void onText(CharSequence arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void swipeDown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void swipeLeft() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void swipeRight() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void swipeUp() {
		// TODO Auto-generated method stub
		
	}
}
