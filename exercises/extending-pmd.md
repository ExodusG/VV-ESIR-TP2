# Extending PMD

Use XPath to define a new rule for PMD to prevent complex code. The rule should detect the use of three or more nested `if` statements in Java programs so it can detect patterns like the following:

```Java
if (...) {
    ...
    if (...) {
        ...
        if (...) {
            ....
        }
    }

}
```
Notice that the nested `if`s may not be direct children of the outer `if`s. They may be written, for example, inside a `for` loop or any other statement.
Write below the XML definition of your rule.

You can find more information on extending PMD in the following link: https://pmd.github.io/latest/pmd_userdocs_extending_writing_rules_intro.html, as well as help for using `pmd-designer` [here](https://github.com/selabs-ur1/VV-ESIR-TP2/blob/master/exercises/designer-help.md).

Use your rule with different projects and describe you findings below. See the [instructions](../sujet.md) for suggestions on the projects to use.

## Answer


On utilise XPath pour définir une nouvelle règle qui est :
```
//IfStatement[ancestor::IfStatement[ancestor::IfStatement]]
```
Cela permets de savoir si à chaque if, on trouve bien un autre if dans les enfants de l'AST
Si on la mets dans un ruleSet cela devient :
```xml
<?xml version="1.0"?>

<ruleset name="Custom Rules"
    xmlns="http://pmd.sourceforge.net/ruleset/2.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://pmd.sourceforge.net/ruleset/2.0.0 https://pmd.sourceforge.io/ruleset_2_0_0.xsd">

    <description>
        My custom rules
    </description>


    <rule name="firstrule"
      language="java"
      message="3 if statement"
      class="net.sourceforge.pmd.lang.rule.XPathRule">
   <description>

   </description>
   <priority>3</priority>
   <properties>
      <property name="version" value="2.0"/>
      <property name="xpath">
         <value>
<![CDATA[
//IfStatement[ancestor::IfStatement[ancestor::IfStatement]]
]]>
         </value>
      </property>
   </properties>
</rule>


</ruleset>
```

On obtient alors quelques sorties :
```
/home/exodus/Documents/esir/vv/tp2/commons-collections-master/src/main/java/org/apache/commons/collections4/CollectionUtils.java:1507:	firstrule:	3 if statement
/home/exodus/Documents/esir/vv/tp2/commons-collections-master/src/main/java/org/apache/commons/collections4/CollectionUtils.java:1509:	firstrule:	3 if statement
/home/exodus/Documents/esir/vv/tp2/commons-collections-master/src/main/java/org/apache/commons/collections4/CollectionUtils.java:1511:	firstrule:	3 if statement
/home/exodus/Documents/esir/vv/tp2/commons-collections-master/src/main/java/org/apache/commons/collections4/CollectionUtils.java:1513:	firstrule:	3 if statement
/home/exodus/Documents/esir/vv/tp2/commons-collections-master/src/main/java/org/apache/commons/collections4/MapUtils.java:229:	firstrule:	3 if statement
/home/exodus/Documents/esir/vv/tp2/commons-collections-master/src/main/java/org/apache/commons/collections4/MapUtils.java:232:	firstrule:	3 if statement
/home/exodus/Documents/esir/vv/tp2/commons-collections-master/src/main/java/org/apache/commons/collections4/MapUtils.java:235:	firstrule:	3 if statement
/home/exodus/Documents/esir/vv/tp2/commons-collections-master/src/main/java/org/apache/commons/collections4/MapUtils.java:929:	firstrule:	3 if statement
/home/exodus/Documents/esir/vv/tp2/commons-collections-master/src/main/java/org/apache/commons/collections4/MapUtils.java:932:	firstrule:	3 if statement
....
```
