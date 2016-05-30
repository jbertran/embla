# PSTL - Notes sur ELM
## **FRP**
### **Intérêt:**
* Séparer "Comment" et "Quoi" : l'utilisateur d'une lib FRP déclare les comportements attendus ("Quoi") et le framework se charge d'afficher le comportement attendu sans se soucier de comment il est affiché.
   ~= le niveau d'abstraction attendu
* Aider à gérer des soucis de concurrence -- plusieurs évènements attendent le traitement dans le gameloop, un évènement long à traiter est en cours, dont *tous* les évènements attendent (*global delay*).
* Embla/ELM: supprimer les calculs superflus (aka ne pas renvoyer toute la scène dans la CG quand on n'en change qu'une partie)
* Embla: en clojure, donc fonctionnel pur simple à appréhender, et tourne sur JVM, donc ultraportable.

### **3 classes de FRP:**
Pour les 3 classes: le **temps** s'incrémente de manière monotone.

* Classical FRP
* Real Time/Event-Driven FRP
* Arrowized FRP.

Différences:

* mode de calcul de la réaction aux entrées
* modélisation des entrées (signaux/comportements/évènements)
#### *Classical FRP*
À l'origine pour l'animation, pas GUI.

Deux types de valeurs d'entrée :

* **Behavior:** les fonctions qui varient en fonction du temps (couleurs qui pulsent, mouvements, etc...)
```
Behavior alpha = Time -> alpha
```
* **Event:** les fonctions qui représentent un évènement au temps t.
```
Event alpha = (Time, alpha)
```
Problèmes:

* *Lazy eval*
* *Space leaks* - grosse utilisation mémoire voire stack overflow si on accumule les calculs
* *Time leaks* - accumulation -> global delay

#### *Real Time/Event-Driven FRP*
Event ~ Behavior : les signaux.
```
Signal alpha = Time -> alpha
```
#### *Arrowized FRP*
Fonctions de signaux:
```
SF alpha beta = Signal alpha -> Signal beta
```
### **Transformations de signaux**

* **Lift: ** *(a->b)-> Signal a -> Signal b* -- Transformer une fonction en un signal / synchroniser une fonction et un signal.
* **Lift2:** *(a->b->c) -> Signal a -> Signal b -> Signal c* -- Joindre 2 signaux avec une fonction
* **Foldp:** *(a->b->b) -> b -> Signal b -> Signal a* - Valeurs du signal en entrée combinées avec un accumulateur
