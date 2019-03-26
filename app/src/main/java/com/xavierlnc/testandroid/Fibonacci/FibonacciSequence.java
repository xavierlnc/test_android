package com.xavierlnc.testandroid.Fibonacci;

import java.util.LinkedList;


public class FibonacciSequence {

    // Liste qui contient tous les nombres
    //public LinkedList<FibonacciNumber> sequence;

    private FibonacciNumber alpha;
    private FibonacciNumber beta;

    public FibonacciSequence() {
        //sequence = new LinkedList<>();

        //Initialisation des 2 premiers termes indispensables au calcul des suivants
        alpha = new FibonacciNumber(0);
        //sequence.add(alpha);
        beta = new FibonacciNumber(1);
        //sequence.add(beta);
    }

    // Avoir le prochain élément de la suite de fibonacci
    public FibonacciNumber getNext() {
        FibonacciNumber temp = FibonacciNumber.sum(alpha,beta);
        alpha = beta;
        beta = temp;
        //sequence.add(temp);
        return temp;
    }

}
