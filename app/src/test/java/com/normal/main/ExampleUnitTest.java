package com.normal.main;

import org.junit.Test;

import static org.junit.Assert.*;

import android.util.Log;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        new Z();
    }

    class  X{
        Y b = new Y();
        X(){
            Log.e("zxy","X");
        }
    }
    class Y{
        Y(){
            Log.e("zxy","Y");
        }
    }

    public class Z extends X{
        Y y = new Y();
        Z(){
            Log.e("zxy","Z");
        }
    }
}