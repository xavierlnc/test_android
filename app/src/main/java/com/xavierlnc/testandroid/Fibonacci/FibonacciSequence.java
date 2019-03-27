package com.xavierlnc.testandroid.Fibonacci;

public class FibonacciSequence {

    private FibonacciNumber alpha;
    private FibonacciNumber beta;

    public FibonacciSequence() {
        //Initialisation des 2 premiers termes indispensables au calcul des suivants
        alpha = new FibonacciNumber(0);
        beta = new FibonacciNumber(1);
    }

    // Avoir le prochain élément de la suite de fibonacci
    public FibonacciNumber getNext() {
        FibonacciNumber temp = FibonacciNumber.sum(alpha,beta);
        alpha = beta;
        beta = temp;
        return temp;
    }

}
