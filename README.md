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
*AuctionBean* <- Sessione
*searchBean* <- Sessione
*AuthBean* <- Sessione
*AdminBean* <- Sessione
*SubscriptionBean* <- Richiesta
*InsertAuctionBean* <- Richiesta



**Scheduler** fornita da jsf
SchdulerBean


**Database** fornita da jdbc pool di Glassfish
DbmanagerBean

**Mail**
MailBean



