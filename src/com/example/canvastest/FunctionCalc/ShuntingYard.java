package com.example.canvastest.FunctionCalc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tomoki on 2015/07/12.
 */

public class ShuntingYard {
    /**
     * @param division_formula the command line arguments
     */
//    public static void main(String[] args) {
//        String divide_formula = "( 3 ^ ( 1 / 2 ) ) * ( x ^ 2 ) + -6 * x + ( 3 ^ ( 1 / 2 ) )";
//        //result 3 1 2 / ^ x 2 ^ 6 * x * 3 1 2 / ^ + -
//        //String divide_formula = "3 + 4 ^ 2 + 8";
//        //String divide_formula = "( 3 ^ ( 1 / 2 ) ) * ( x ^ 2 ) - 6 * x";
//        List<String> listFormula = new ArrayList<String>();
//        List<String> outFormula = new ArrayList<String>();
//
//        listFormula = ListDivision(divide_formula);
//        //System.out.println(listFormula);
//        outFormula = ShuntingYardAlg(listFormula);
//        System.out.println(outFormula);
//    }

    public static List<String> ListDivision(String division_formula){
        List<String> objectFormula = new ArrayList<String>();
        String[] array;

        array = division_formula.split(" ");
        System.out.println(array[0]);
        for(int i=0; i<array.length;i++){
            objectFormula.add(array[i]);
        }

        return objectFormula;
    }

    public static List<String> ShuntingYardAlg(List<String> divide_formula){

        List<String> rpn_formula = new ArrayList<String>();    //結果である逆ポーランド記法数式の格納
        Stack stack = new Stack();
        String tmp = "";
        String operand =  "";                   //最終ループのオペランド格納用変数
        Boolean exist;
        //オペランドごとの優先度を設定
        HashMap<String,Integer> priority = new HashMap<String,Integer>();
        priority.put("*", 3);
        priority.put("/", 3);
        priority.put("+", 2);
        priority.put("-", 2);
        priority.put("^", 4);

        //数値(+-符号含む)のパターン
        Pattern number_pattern = Pattern.compile("\\d+|\\+d+|\\-d+|[a-z]");
        Matcher num_matcher;

        for(String now_input:divide_formula){
            num_matcher = number_pattern.matcher(now_input);
            if(num_matcher.find()){             //数値ならば
                rpn_formula.add(now_input);
            }else if("(".equals(now_input)){      //左括弧ならば
                stack.push(now_input);
            }else if(")".equals(now_input)){     //右括弧ならば
                exist = false;
                while(!stack.isEmpty()){
                    tmp = stack.pop().toString();
                    if("(".equals(tmp)){
                        exist = true;
                        break;
                    }else{
                        rpn_formula.add(tmp);
                    }
                }
                if(!exist){
                    System.out.println("カッコエラーだよ");
                }
            }else{      //演算子の場合
                if(!stack.empty() && stack.lastElement() != "(" ){
                    if(priority.get(stack.lastElement().toString()) != null){
                        System.out.println(LeftAssociate(now_input));
                        if((LeftAssociate(now_input) &&  priority.get(now_input) <= priority.get(stack.lastElement().toString()) ) ||
                                (!(LeftAssociate(now_input)) && (priority.get(now_input) < priority.get(stack.lastElement().toString())) ) ){
                            rpn_formula.add(stack.pop().toString());
                        }
                    }
                }
                stack.push(now_input);
            }

        }
        System.out.println("途中経過: \n"+ rpn_formula);
        //トークン読み込み完了
        //残った演算子トークンを末尾に追加する
        if(!stack.empty()){
            while(!stack.empty()){
                operand = stack.pop().toString();
                if( !("(".equals(operand) || ")".equals(operand)) ){
                    rpn_formula.add(operand);
                }
            }
        }

        return rpn_formula;
    }

    private static Boolean LeftAssociate(String operator){
        if("^".equals(operator)){
            return false;
        }
        return true;
    }

    private static Stack StackReverse(Stack input_stack){
        Stack rev_stack = new Stack();
        while(!input_stack.empty()){
            rev_stack.push(input_stack.pop());
        }
        return rev_stack;
    }

}
