Progetto 2 di Programmazione WEB 2012/2013
==========================================

![ScreenShot](https://raw.github.com/sirbotta/looki/master/src/main/webapp/resources/img/logoS.png)



Introduzione
------------
Il progetto simula una semplice casa d'aste per prodotti agricoli con funzioni ispirati alla nota casa di aste E-bay.
L'utente quindi può creare e partecipare alle aste in corso sia come venditore che come acquirente.


Tecnologie
----------
*JavaEE 6*  
*Glassfish 3.1.2* 
*Apache Derby*
*Servlet-api 2.5*  
*jsp-api 2.1*  
*jstl 1.2*  
*JSF 2.1.7*  
*PrimeFaces 3.4.2*  
*Quartz scheduler 2.1.5*  
*Apache POI 3.9*  
*Apache Common 2.4*  
*Apache Fileupload 1.2*  
*Itext pdf 4.2.0*

Architettura
------------
**MVC** fornita da jsf  
*AuctionBean*  
Scope: Sessione  
Ruolo: gestione della presentation e logica della singola asta  
*searchBean*  
Scope: Sessione  
Ruolo: gestione della presentation e logica di tutti i risultati del sito  
*AuthBean*  
Scope: Sessione  
Ruolo: gestione della presentation, sicurezza e logica dell'utente  
*AdminBean*  
Scope: Sessione  
Ruolo: gestione della presentation e logica della sezione ADMIN  
*SubscriptionBean*  
Scope: Richiesta  
Ruolo: gestione della presentation e logica della pagina di iscrizione  
*InsertAuctionBean*  
Scope: Richiesta  
Ruolo: gestione della presentation e logica della pagina di inserimento asta  

**Scheduler** fornita da quartz  
*SchdulerBean*  
Scope: Applicazione  
Ruolo: gestione dei job e della schedulazione dei processi di chiusura asta  

**Database** fornita da jdbc pool di Glassfish  
*DbmanagerBean*  
Scope: Applicazione  
Ruolo: gestione di tutte le query a DB tramite Datasource   

**Mail**  fornita dal smtp di google  
*MailBean*  
Scope: Applicazione  
Ruolo: gestione invio mail tramite connessione diretta java.mail  

**Security** fornita da semplici filtri in java  
*AuthFilter*  
Ruolo: controllo atutenticazione parti private utenti  
*AdminFilter*  
Ruolo: controllo atutenticazione parti admin


Installazione
-------------
Il sistema necessita di preparare in anticipo il Database e il jdbc connection pool.  
Per il db sono presenti dentro it.malbot.greenbay.model il dbSchema e un seed con dei dati di prova 
già sotto forma di query sql.  
Per motivi tecnici le aste non scadranno correttamente visto che quartz non ha i job attivi di chiusura.

In ogni caso create un DB su derby chiamato greenbay con user *green* e password *bay*  
Create poi su glassfish tramite il pannello admin una connection pool al db usando i driver derby (non derby 40)  
I parametri necessari sono:
Servername: localhost  
databaseName: greenbay    
ConnectionAttribute: ;create=true  
User: green   
Password: bay  
PortNumber: 1527  

Una volta finito create una risorsa jdbc che punti al pool appena creato.  
Chiamatela jdbc/greenbay.  
Ora l'app può collegarsi liberamente al db tramite alle pool ottimizzate di glassfish.

Per comodità l'app è in formato MAVEN e dopo la prima compilazione scaricherà in modo automatico tutte le dipendenze 
necessarie per l'avvio della webapp.





