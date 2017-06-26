package com.vector.toolsdemo;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {

        double sqrt = Math.sqrt(0.0D);

        System.out.println(sqrt);
        assertEquals(4, 2 + 2);
    }
}