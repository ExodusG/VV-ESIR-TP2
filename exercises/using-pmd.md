# Using PMD

Pick a Java project from Github (see the [instructions](../sujet.md) for suggestions). Run PMD on its source code using any ruleset. Describe below an issue found by PMD that you think should be solved (true positive) and include below the changes you would add to the source code. Describe below an issue found by PMD that is not worth solving (false positive). Explain why you would not solve this issue.

## Answer

Avec PMD lorsqu'on utilise le ruleset de base sur le projet commons-collection avec la commande :
```
./run.sh pmd  -d ../../commons-collections-master  -R rulesets/java/quickstart.xml -f text
 ```
 On se retrouve avec beaucoup de lignes en sortie, notamment des vrais positifs et des faux positifs.

 Un exemple de ligne à prendre en compte :
 ```
 commons-collections-master/src/main/java/org/apache/commons/collections4/SetUtils.java:620:    ReturnEmptyCollectionRatherThanNull:    Return an empty collection rather than null.
 ```

 En effet faire un *return null* n'est jamais bon, il faut donc modifier le code, ce qui donne :

```java
	public static <E> Set<E> unmodifiableSet(final E... items) {
    	if (items == null) {
        	return null;
    	}
    	//return UnmodifiableSet.unmodifiableSet(hashSet(items));  -> oldcode
    	return new HashSet <E> ();
	}
```
Prenons un faux positif maintenant :
```
commons-collections-master/src/test/java/org/apache/commons/collections4/MapUtilsTest.java:317:    LocalVariableNamingConventions:    The final local variable name 'LABEL' doesn't match '[a-z][a-zA-Z0-9]*'
```
	Celui-ci n'est pas à prendre en compte car les variable on les nommes comme on veut, cela dépend de la convention de codage d'un projet. Le ruleset de base n'est pas forcément adapté à ce projet.
