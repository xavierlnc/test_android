# Test Android

## Description Générale

Cette application android affiche la suite de Fibonacci à l'infini à l'aide d'un infinite Scroll.

## Comment est construite l'app ?

Étant donné que les nombres de la suite de Fibonacci augmentent très rapidement, les types primitifs de nombre ne suffisent plus pour les représenter. J'ai donc créé mes propres objets pour générer la suite.

### FibonacciNumber

Pour représenter les nombres, j'ai utilisé une *LinkedList* d'entier. Chaque entier de la liste est associé à une puissance de 10 du nombre, c'est à dire qu'à la position 0 le chiffre correspond à [chiffre]*10^0, à la position 1 [chiffre]*10^1... Les *LinkedList* n'ayant pas un nombre maximale d'élément, on peut donc **représenter des nombres de taille potentiellement infinie**.

La méthode *sum()* calcule la somme de 2 *FibonacciNumber*. Puisque la méthode *size()* des *LinkedList* renvoie un entier de type *int*, j'ai évité d'utiliser cette méthode pour éviter tout crash de l'algorithme en cas de somme de grands *FibonacciNumber*.

> Un entier de type *int* est compris entre -2 147 483 648 et 2 147 483 647

Certes ces *FibonacciNumber* doivent être supérieurs à 10^2.147.483.647 pour faire crasher l'algorithme ce qui est plutôt conséquent, mais dans l'idée de faire la suite de Fibonacci à l'infini ce n'est pas acceptable du fait qu'il persiste une limite. La seule fois où j'ai utilisé la méthode *size()* je me suis assuré que la valeur renvoyée ne puisse pas dépasser cetter limite (en l'occurence ici la valeur renvoyée est soit 0 soit 1).

C'est dans cette optique que j'ai implémenté la méthode *sum()*, c'est à dire qu'elle puisse **calculer la somme de 2 *FibonacciNumber* potentiellement infinis**.

### FibonacciSequence

Cette classe permet tout simplement de générer la suite de Fibonacci. Pour avoir le prochain terme de la suite il suffit d'appeler la méthode *getNext()*. Pour des questions de mémoire, aucune valeur n'est stockée définitivement.

### Main

C'est la classe qui gère l'Activity. La suite de Fibonacci est affiché à l'aide d'un *RecyclerView* sous forme de liste qui peut être scrollée indéfiniment. 

Dès que l'utilisateur a scrollé presque 1000 nombres, les 1000 prochains termes de la suite sont chargés via une *AsynTask*. Cela permet d'éviter à l'application de freezer en cas de long chargement (notamment quand les nombres deviennent de plus en plus grands). Si la génération des prochains éléments est longue, une *ProgressBar* indique que les données sont en train de charger. Un callback permet d'indiquer la fin du chargement et de renvoyer les données.

## Limite de l'Application pour générer la suite à l'infini

### 1) La méthode *size()*

Comme je l'ai dis plus haut lorsque j'expliquais la construction de la classe *FibonacciNumber*, la méthode *size()* d'une *LinkedList* renvoi un entier. Cependant il est nécessaire d'utiliser cette méthode lors de l'implémentation du *RecyclerView*, et celle-ci est susceptible de devoir renvoyer un entier plus grand que ce qu'elle peut. Si l'application s'arrête de générer des données, il est quasiment certains que ce n'est pas dû à cela (ou alors je veux votre téléphone).

### 2) La RAM

La principale limite reste la RAM. Les nombres de la suite de Fibonacci sont grand et les accumuler en mémoire prend beaucoup de place. Si l'Application ne peut plus charger de nombres, il est presque sûr que ce soit dû à la saturation de la mémoire.

## Conclusion

Bien qu'il soit impossible de générer la suite de Fibonacci à l'infini, l'application permet de les génerer et les afficher tant que la mémoire le permet. Personnellement, j'ai réussi à charger à peu près 40 000 termes de la suite avant que la mémoire de mon téléphone soit saturée.
