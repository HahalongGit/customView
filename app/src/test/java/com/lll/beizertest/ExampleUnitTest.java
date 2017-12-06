package com.lll.beizertest;

import android.util.Log;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test(){
        System.out.println("ExampleUnitTest-Sin 90°="+Math.sin(Math.PI/2));
        System.out.println("ExampleUnitTest-30°-1="+Math.toDegrees(Math.PI/6));
        System.out.println("ExampleUnitTest-30°-2="+Math.toDegrees(Math.asin(Math.sin(Math.PI/6))));
        System.out.println("ExampleUnitTest-sin30°="+Math.sin(Math.PI/6));
        System.out.println("ExampleUnitTest-asin 0.5="+Math.toDegrees(Math.asin(0.5)));//求一个正弦值的弧度对应角度
        System.out.println("ExampleUnitTest-cos 60°=="+Math.cos(Math.PI/3));
        System.out.println("ExampleUnitTest-tan 45°=="+Math.tan(Math.PI/4));
        System.out.println("ExampleUnitTest-atan=="+Math.atan(Math.tan(Math.PI/4)));//求孤独
        System.out.println("ExampleUnitTest-atan1=="+Math.atan(1));//求斜率对应的孤独
        System.out.println("ExampleUnitTest-atan1=="+Math.toDegrees(Math.atan(1)));//求斜率对应的孤独的对应角度
        try {
            URL url = new URL("");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}