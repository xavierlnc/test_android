package com.xavierlnc.testandroid;

import android.util.Log;

import java.util.LinkedList;


public class FibonacciSequence {

    private String TAG = "FibonacciSequence";

    public static LinkedList<FibonacciNumber> sequence;

    public FibonacciSequence() {
        sequence = new LinkedList<>();
    }

    /*
     * Store fibonacci number
     * Take two last to compute the next
     * n has to be fix
     */
    public void compute(int n) {
        Log.i(TAG,"compute : "+String.valueOf(n));
        FibonacciNumber alpha = new FibonacciNumber(0);
        FibonacciNumber beta = new FibonacciNumber(1);
        FibonacciNumber temp;
        for (int k = 0; k < n; k++) {
            Log.i(TAG,"compute | iter : "+String.valueOf(k));
            temp = FibonacciNumber.sum(alpha,beta);
            alpha = beta;
            beta = temp;
            sequence.add(alpha);
        }
    }

}
