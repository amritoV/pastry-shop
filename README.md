# pastry-shop
1) intro
attualmente pastryshop è configurato per essere eseguito sulla propria macchina locale.
le tecnologie principalmente usate sono: postgresql per la persistenza dei dati, spring boot per sviluppare il layer backend.
Come build automation tool è stato deciso di utilizzare Maven.
------------------------------------------------------------------------------------------------------------

2) struttura repository
-all'interno della cartella ddl-dummydata si trovano i files domains-tables.sql e data.sql.
domains-tables.sql contiene lo script di creazione dello schema relazionale.
data.sql contiene alcuni dati fantoccio per utili al fine di testare l'applicazione.

-all'interno della cartella pastryshop-be si trova il codice sorgente del layer backend.

-all'interno della cartella pastryshop-fe si trova il codice sorgente del layer frontend.

-all'interno della cartella docs si trova la specifica dei requisiti e tutta la documentazione
relativa alla fase di progettazione della base di dati ed alle varie funzionalità che il sistema deve offrire.
------------------------------------------------------------------------------------------------------------

3) prerequisiti
aver installato il dbms postgresql installato.
aver la jdk 17 installata.
aver una versione di maven abbastanza recente installata.

------------------------------------------------------------------------------------------------------------
4) configurazione ambiente(in locale)
	a) creazione dello schema relazionale:
	- tramite riga di comando eseguire il comando 'psql -U <utente>' per accedere al terminale interattivo di postgres.
	- creare il database tramite il comando 'create database pastryshopdb;'
	- creare un utente tramite il comando 'create user <nomeUtente> with password '<password>';'
	- assegnare all'utente privilegi completi sul database pastryshopdb, 'grant all privileges on database pastryshopdb to <nomeUtente>;'
	- utilizzare il database con il comando '\c pastryshopdb <nomeUtente>;'
	- eseguire lo script di creazione dello schema relazionale '\i /path/al/file/domains-tables.sql;'
	- eseguire lo script di inserimento dei dati fantoccio '\i /path/al/file/data.sql;'
	
	b)esecuzione del codice sorgente backend:
	- modificare il application.properties modificando le proprieta spring.datasource.url, spring.datasource.username
	e spring.datasource.password. inserendo la url dove il database è in ascolto, inserire l'utente e password creati nel passaggio a).
	- aprire il terminale e spostarsi sotto la cartella: 'cd /path/alla/cartella/pastryshop-be'
	- eseguire il comando 'mvn clean install'
	- eseguire il comando 'mvn spring-boot:run' 
	
	

