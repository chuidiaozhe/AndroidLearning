package financer.kuaishoudan.com.recyclerviewdemo;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
       test();
    }

   public  void test(){
           System.out.println("====== currentTime ======" +(System.currentTimeMillis()/1000 - 24*60*60));
           System.out.println("====== currentTime ======" +(System.currentTimeMillis()/1000 ));
        System.out.println("====== djfdf ======" + ((int)-2.1 -1));
    }
}