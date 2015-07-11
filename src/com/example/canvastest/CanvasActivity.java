package com.example.canvastest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.canvastest.FunctionCalc.LexicalAnalysis;
import com.example.canvastest.FunctionCalc.ReversePolishNotationOld;
import com.example.canvastest.FunctionCalc.ShuntingYard;
import com.example.canvastest.R.id;

import java.util.List;


public class CanvasActivity extends Activity {

    String functionChar="3^2";

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
        intent.putExtra("Input Func",functionChar);
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

                //数式を空白で単位分割した文字列を取得
                String formula = LexicalAnalysis.FormulaToInfix(functionChar);
                //操車場アルゴリズムで数式を逆ポーランド記法に変換
                List<String> formulaList = ShuntingYard.ListDivision(formula);
                List<String> shuntingYardList = ShuntingYard.ShuntingYardAlg(formulaList);
                //逆ポーランド記法の計算
                Float[] resultNum = ReversePolishNotationOld.ReversePolishNotationOld(shuntingYardList, 1, 30);
                Log.d("TAG","RESULT_OK");
                Log.d("shuntingYardList",String.valueOf(shuntingYardList));
                Log.d("ReversePolishResult",String.valueOf(resultNum[0]));
                break;
            case Activity.RESULT_CANCELED:
                Log.d("TAG","RESULT_CANCELED");
                break;
        }
    }

}
