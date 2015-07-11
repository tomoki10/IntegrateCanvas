package com.example.canvastest.FunctionCalc;

/**
 * Created by tomoki on 2015/07/11.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//文字列の数式を分割して1文字ずつに分ける
public class LexicalAnalysis {

    /*
    //
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        long stop;
        String formula= "(3^(1/2))*(x^2)-65*x+(3^(1/2))";
        //String formula= "-2*(x+3)^2";
        String division_formula = FormulaToInfix(formula);
        stop = System.currentTimeMillis();

        List<String> listFormula = new ArrayList<>();
        listFormula = ListDivision(division_formula);
        System.out.println(listFormula);


        System.out.print(division_formula + "  " + (float)(stop-start)/1000);
    }
    */


    public static List<String> ListDivision(String division_formula){
        List<String> objectFormula = new ArrayList<String>();
        String[] array;

        array = division_formula.split(" ");
        System.out.println(array[0]);
        for(int i=1; i<array.length;i++){
            objectFormula.add(array[i]);
        }

        return objectFormula;
    }

    public static String FormulaToInfix(String formula){
        StringBuilder infix_array = new StringBuilder();

        String last_str = "";       //末尾の文字格納用変数
        Boolean may_sign = true;    //単項演算子の符号が現れる可能性があるときtrue
        String sign = "";           //単項演算子の符号を記憶する変数
        String now_formula ="";     //現在抽出している数式記号・文字

        //Pattern character_number_pattern = Pattern.compile("[0-9|a-z|=]");
        Pattern characterPattern = Pattern.compile("[a-z|=]");
        Pattern numberPattern = Pattern.compile("[0-9]");
        //Matcher matcher;
        Matcher matcherNum;
        Matcher matcherChar;

        while(formula.length() > 0){
            now_formula = String.valueOf(formula.charAt(0));       //数式の先頭文字を抽出
            if("^".equals(now_formula)){
                infix_array.append(" ").append(now_formula);
                may_sign = true;
            }else if("*".equals(now_formula) || "/".equals(now_formula) || "(".equals(now_formula)){
                infix_array.append(" ").append(now_formula);
                may_sign = true;
            }else if( ")".equals(now_formula)){                 //括弧閉じの後は2項演算子しかこないのでmay_signをtrueにしない
                infix_array.append(" ").append(now_formula);
            }else if("+".equals(now_formula) || "-".equals(now_formula)){
                if(may_sign){
                    sign = now_formula;
                }else{
                    infix_array.append(" ").append(now_formula);
                }
            }

            //数字か文字ならば
            matcherChar = characterPattern.matcher(now_formula);
            matcherNum = numberPattern.matcher(now_formula);

            if(matcherNum.find() || matcherChar.find()){
                //２桁以上の数値を判別するため直前の文字が数字の場合を判定
                //直前の数字を取得し、識別する
                if(infix_array.length()>0){
                    last_str = String.valueOf(infix_array.charAt(infix_array.length()-1));
                }
                //matcher = character_number_pattern.matcher(last_str);
                matcherNum = numberPattern.matcher(last_str);
                System.out.println(last_str  +" , "+  now_formula);
                System.out.println(matcherNum.find()  +" , "+  characterPattern.matcher(now_formula).find());
                //直前が数字でかつnow_formmulaが文字の場合
                if( numberPattern.matcher(last_str).find() && characterPattern.matcher(now_formula).find()){
                    System.out.println("number char");
                    infix_array.append(" * ").append(now_formula);
                }else if(numberPattern.matcher(last_str).find()){   //直前が数字の場合
                    System.out.println("Number 2 length");
                    infix_array.delete(infix_array.length()-1, infix_array.length());
                    infix_array.append(last_str+now_formula);
                }else{
                    infix_array.append(" ").append(sign).append(now_formula);
                }
                sign = "";
                may_sign = false;
            }
            formula = formula.substring(1,formula.length());
        }

        return String.valueOf(infix_array);
    }
}
