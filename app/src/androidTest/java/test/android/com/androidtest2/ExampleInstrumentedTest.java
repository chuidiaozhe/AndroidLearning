package test.android.com.androidtest2;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import test.android.AesUtils;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        String pass = "123456";
        String ency = AesUtils.encrypt("android",pass);
        Log.e("testest  ency : ",ency);
        String decy = AesUtils.decrypt("android",ency);
        Log.e("testtest decy : ",decy);

        assertEquals("test.android.com.androidtest2", appContext.getPackageName());
     }
}
