package com.xavierlnc.testandroid;

import android.util.Log;

import java.util.ArrayList;

public class FibonacciNumber {

    private static final String TAG = "FibonacciNumber";
    private ArrayList<Integer> number;

    public FibonacciNumber(int n) {
        number = new ArrayList<>();
        number.add(n);
    }

    public FibonacciNumber() {
        number = new ArrayList<>();
    }

    // Calcul la somme de 2 objets "FibonacciNumber"
    public static FibonacciNumber sum(FibonacciNumber alpha, FibonacciNumber beta) {
        FibonacciNumber result = new FibonacciNumber();
        long maxSize = Math.max(alpha.number.size(),beta.number.size());
        int retenue = 0;

        //Pour chaque dizaine on calcul la somme des deux chiffres
        for (int i=0; i<maxSize;i++) {
            int sumOfDigit = 0;
            try {
                sumOfDigit = alpha.get(i) + beta.get(i) + retenue;

            // Si un tableau est plus grand que l'autre, prendre uniquement le chiffre du tableau le plus grand
            // Sans oublier a retenue si elle existe
            } catch (IndexOutOfBoundsException e) {
                Log.w(TAG,"IndexOutOfBoundsException");
                int tempRes;
                if (alpha.number.size() > beta.number.size()) {
                    sumOfDigit = alpha.get(i) + retenue;
                } else {
                    sumOfDigit = beta.get(i) + retenue;
                }
            } finally {
                //Si la somme est supérieur à 10, il faut retenir 1 pour la prochaine dizaine
                if (sumOfDigit >= 10) {
                    result.add(sumOfDigit%10);
                    retenue = 1;
                } else {
                    result.add(sumOfDigit);
                    retenue = 0;
                }

            }
        }

        // S'il reste une retenue, l'ajouté
        if (retenue != 0) {
            result.add(retenue);
        }
        return result;
    }

    public int get(int n) {
        return this.number.get(n);
    }

    public void add(int n) {
        this.number.add(n);
    }

    // Permet d'afficher le nombre
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i=0; i<this.number.size();i++) {
            result.append(String.valueOf(number.get(i)));
        }
        return result.reverse().toString();
    }

}
