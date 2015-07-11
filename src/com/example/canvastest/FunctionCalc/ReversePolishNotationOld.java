package com.example.canvastest.FunctionCalc;

import java.util.List;
import java.util.Stack;

/**
 * Created by tomoki on 2015/07/12.
 */

public class ReversePolishNotationOld {
    /**
     * @param stringList the command line arguments
     */
    //3 1 2 / ^ x 2 ^ * -6 x * 3 1 2 / ^ + +

//    public static void main(String args[]) throws IOException {
//        System.out.println("逆ポーランド記法の数式を入力してください");
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        String[] stringArray = br.readLine().split("\\s");
//        List<String> listStr = Arrays.asList(stringArray);
//
//        float[] tmp = ReversePolishNotationOld(listStr, 1,30);
//        System.out.println(tmp);
//    }

    //引数(リスト、値域の最初、値域の最後)
    public static Float[] ReversePolishNotationOld(List<String> stringList,int start,int end){
        long startTime = System.currentTimeMillis();
        long stopTime;
        int hensuu;
        String[] stringArray = (String[])stringList.toArray(new String[0]);;
        // LIFO
        Stack<Float> que = new Stack<Float>();
        float a = 0;
        float b = 0;
        for(hensuu=start;hensuu<end;hensuu++){
            for (int i = 0; i < stringArray.length; i++) {
                //if(!que.isEmpty())

                if (stringArray[i].equals("x")) {
                    que.push((float)hensuu);
                    continue;
                }
                if (stringArray[i].equals("+")) {
                    a = que.pop();
                    b = que.pop();
                    que.add(b + a);
                    System.out.println(b+" + "+a+"="+(b+a));
                    continue;
                }
                if (stringArray[i].equals("-")) {
                    a = que.pop();
                    b = que.pop();
                    que.add(b - a);
                    System.out.println(b+" - "+a+"="+(b-a));
                    continue;
                }
                if (stringArray[i].equals("*")) {
                    a = que.pop();
                    b = que.pop();
                    que.add(b * a);
                    System.out.println(b+" * "+a+"="+(b*a));
                    continue;
                }
                if (stringArray[i].equals("/")) {
                    a = que.pop();
                    b = que.pop();
                    que.add(b / a);
                    System.out.println(b+" / "+a+"="+(b/a));
                    continue;
                }
                if (stringArray[i].equals("^")) {
                    a = que.pop();
                    b = que.pop();
                    que.add((float)Math.pow(b,a));
                    System.out.println(b+" ^ "+a+"="+Math.pow(b,a));
                    continue;
                }
                // 演算子以外はstackに登録する
                System.out.println("AAA" + stringArray[i]);
                if(isNumber(stringArray[i])) {
                    que.push(Float.parseFloat(stringArray[i]));
                }
            }
        }
        //確認用
        Float resultArr[] = new Float[que.size()];
        int count=0;
        while(true){
            if(!que.isEmpty()){
                resultArr[count] = que.pop();
                System.out.println("最後:"+resultArr[count]);
            }else{
                break;
            }

        }
        stopTime = System.currentTimeMillis();
        System.out.print((double)(stopTime - startTime)+"ms");

        //return Float.valueOf(stringArray[0]);
        return resultArr;
    }

    private static boolean isNumber(String str){
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
