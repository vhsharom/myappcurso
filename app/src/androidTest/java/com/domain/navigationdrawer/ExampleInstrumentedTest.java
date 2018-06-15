package com.domain.navigationdrawer;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

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
        assertEquals("com.domain.navigationdrawer", appContext.getPackageName());
    }

    @Test
    public void testHash() {

        MainActivity mainActivity = new MainActivity();
        try {
            assertEquals("4d186321c1a7f0f354b297e8914ab240", mainActivity.getHash("hola", "MD5"));
        }catch (Exception e){
            Log.d("App", "e: " + e);
        }
    }
}
