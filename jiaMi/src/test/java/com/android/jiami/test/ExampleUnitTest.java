package com.android.jiami.test;

import org.junit.Test;

import java.security.Key;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        String key = "12398765";
        String sou = "djlkfjdlkfjdlkjfkadf";
        String des = DesJiMiUtil.descryTo(sou.getBytes(),key).toString();
        System.out.println("  des 加密：" + des);
        String  enc = DesJiMiUtil.decrypt(des.getBytes(),key).toString();
        System.out.println("  des 解密：" + enc);

    }
}