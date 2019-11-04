package cn.stylefeng.guns.core.util;

/**
 * @author ThreeGlods
 * @date 2019/10/23
 */

public class TestUtil {
    public static void main(String[] args) {

        for (int i =0; i<10000;i++){
            String a = String.valueOf(i);
            char[] array = a.toCharArray();
            String temp= "";
            for (int j =0 ;j<a.length();j++){
                temp += String.valueOf(array[a.length()-j-1]);
            }
            if (Integer.valueOf(temp)-8802 ==i){
                System.out.println("输出结果为："+i);
            }
        }
    }
}
