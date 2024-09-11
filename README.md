# STARK GUI

Progetto di tesi con alla base il _tool_ [STARK](https://github.com/quasylab/jspear).



## Download

Per scaricare il progetto di tesi sarà necessario clonare il _repository_:

``` Shell
git clone https://github.com/Exploudont/STARKGUI.git
```

La cartella di destinazione sarà quella cui è stato richiamato il terminale. Si consiglia dunque di recarsi all'interno della cartella dove si vuole clonare il _repository_ prima di lanciare il comando.



## Compilazione

Per la realizzazione del progetto si è fatto uso di:
- Java (OpenJDK 17).
- _build_ _tool_ Maven;
- IDE Eclipse;

Il progetto presenta delle dipendenze esterne e di pacchetti _jar_ di terze parti.

Per la compilazione sarà necessario recersi all'interno della cartella di progetto e lanciare il seguente comando da terminale:

``` Shell
mvn validate
mvn clean install
```

Il file generato sarà un flat `jar`: un `jar` con al proprio interno tutte le dipendenze necessarie al funzionamento.


### Non-flat JAR

Se non si volessero includere le dipendenze di terze parti per la compilazione sarà necessario recersi all'interno della cartella di progetto e lanciare il seguente comando da terminale:
``` Shell
mvn package
```

Affinché possa funzionare è richiesto che le dipendenze siano presenti all'interno del _classpath_.


### Errori con JAR di terze parti

Al fine completare il processo di compilazione è necessario comunicare a Maven dove trovare i JAR di terze parti utilizzati per il progetto.  
Se dovessero insorgere problemi si cosiglia di seguire la [documentazione ufficiale](https://maven.apache.org/guides/mini/guide-3rd-party-jars-local.html).



## JavaDoc

Per la creazione della documentazione relativa alle classi è possibile consultare la JavaDoc.  
Per creare la JavaDoc sarà necessario recarsi all'interno della cartella di progetto e lanciare il seguente comando da terminare:

``` Shell
mvn javadoc:javadoc
```

La javadoc verrà generata all'interno della cartella `target\reports\apidocs`.



## Esecuzione

Per eseguire il programma sarà necessario recarsi all'interno della cartella di progetto, spostarsi all'intenro della cartella `target` e lanciare il seguente comando da terminare:

``` Shell
java -jar tesi-0.0.1-SNAPSHOT.jar
```



## Licenza
Il progetto è rilasciato sotto la licenza [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.txt) (la stessa di STARK).



## Autore
**nome**: Daniele  
**cognome**: Longobardi  
**numero matricola**: 737547  
**sede**: Varese  