# TCC *vs* LCC

Explain under which circumstances *Tight Class Cohesion* (TCC) and *Loose Class Cohesion* (LCC) metrics produce the same value for a given Java class. Build an example of such as class and include the code below or find one example in an open-source project from Github and include the link to the class below. Could LCC be lower than TCC for any given class? Explain.

## Answer

Si les 2 métriques TCC et LCC ont la même valeurs alors ça veut dire qu'il n'y a aucune méthode connecté indirectement.

Le LCC ne peut pas être inférieure au TCC, car le LCC est le nombre relatif de méthode connecté indirectement et directement or le TCC est le nombre relatif de méthode connecté directement.

Pour faire le calcul de la LCC et la TCC on utilise :
- nombre de paires des méthodes directement liées (NDC)
- le nombre de paires des méthodes indirectement liées (NIC)
- le nombre maximal possible de paires des méthodes connectées (NP)

Les calculs :
```
N P — N * ( N — l)/2
T C C = N D C / N P
L C C = ( N D C + N I C ) / N P
```
[Source](https://savoirs.usherbrooke.ca/bitstream/handle/11143/1606/MR83692.pdf)

Un exemple basique de classe ou la TCC et la LCC sont égaux :

```java
public class Test{

  private int attribute;


  public int getAttribute(){
    return this.attribute;
  }


  public void test(){
    int test=this.getAttribute();
  }
}
```
test et getAttribute sont connecté directement et il n'y a aucune méthode connecté directement, (par exemple une autre méthode qui aurait accès directement à attribute, alors test et cette autre méthode aurait été connecté indirectement).
