package com.xavierlnc.testandroid.Fibonacci;

import java.util.LinkedList;

public class FibonacciNumber implements Cloneable {

    private static final String TAG = "FibonacciNumber";

    // LinkedList n'a pas de taille limite => utilisable pour représenter un nombre très grand
    private LinkedList<Integer> number;

    public FibonacciNumber(int n) {
        number = new LinkedList<>();
        number.add(n);
    }

    public FibonacciNumber() {
        number = new LinkedList<>();
    }

    public FibonacciNumber(LinkedList<Integer> list) {
        number = (LinkedList<Integer>) list.clone();
    }

    // Calcule la somme de 2 objets "FibonacciNumber"
    public static FibonacciNumber sum(FibonacciNumber a, FibonacciNumber b) {

        //Copie des 2 nombres à additioner
        FibonacciNumber alpha = null;
        FibonacciNumber beta = null;
        try {
            alpha = (FibonacciNumber) a.clone();
            beta = (FibonacciNumber) b.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        FibonacciNumber result = new FibonacciNumber();
        int retenue = 0;
        boolean isFinished = false; //est-ce que la somme est terminée

        //Pour chaque puissance de 10 on calcule la somme des deux chiffres
        while (true) {
            int sumOfDigit = 0;
            try {
                sumOfDigit = alpha.number.get(0) + beta.number.get(0) + retenue;
                //Supprimer les puissances déjà calculées
                alpha.number.remove(0);
                beta.number.remove(0);

            // Si un nombre est plus grand que l'autre ne pas oublier sa puissance de 10 restante
            // ainsi que la retenue si elle existe
            } catch (IndexOutOfBoundsException e) {
                //Est-ce qu'il reste un nombre avec une puissance de 10 ?
                if (alpha.number.size() != 0) {
                    sumOfDigit = alpha.number.get(0) + retenue;
                    alpha.number.remove(0);
                } else if (beta.number.size() != 0) {
                    sumOfDigit = beta.number.get(0) + retenue;
                    beta.number.remove(0);
                } else { // Plus aucune puissance de 10 pour les 2 nombres
                    isFinished = true;
                    break;
                }
            } finally {
                //Si la somme est supérieure à 10, il faut retenir 1 pour la prochaine puissance de 10
                if (sumOfDigit >= 10) {
                    result.number.add(sumOfDigit%10);
                    retenue = 1;

                // Si la somme est terminée, sumOfDigit=0
                // il ne faut donc pas ajouter le 0 au nombre
                } else if (!isFinished){
                    result.number.add(sumOfDigit);
                    retenue = 0;
                }
            }
        }

        // S'il reste une retenue, l'ajouter
        if (retenue != 0) {
            result.number.add(retenue);
        }
        return result;
    }

    // Permet de cloner un objet FibonacciNumber
    @Override
    protected FibonacciNumber clone() throws CloneNotSupportedException {
        return new FibonacciNumber(number);
    }

    // Permet d'afficher le nombre
    public String toString() {
        StringBuilder result = new StringBuilder();
        LinkedList<Integer> copy = (LinkedList<Integer>) number.clone();
        while (true){
            try {
                result.append(String.valueOf(copy.get(0)));
                copy.remove(0);
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }
        return result.reverse().toString();
    }

}
