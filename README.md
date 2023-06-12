# Application Geofencing pour Android

## Introduction

## Guide d'utilisation
### Configuration requise
Cette application requiert que le système sur lequel elle s'installe dispose de la géolocalisation.
### Les permissions
Lors de l'ouverture de la page contenant Google Maps pour la première fois, une demande de permission pour l'accès à la position exacte ou approximative est demandée et propose trois choix :
*  Lorsque vous utilisez l'appli
*  Uniquement cette fois-ci
*  Ne pas autoriser
Pour que l'application fonctionne, il est nécessaire d'appliquer une permission non proposée ici. Celle-ci doit être ajoutée en allant dans les paramètres de l'application, avec un long clique sur l'icone de l'application depuis la page d'application, puis en cliquant sur information. Ensuite sous **autorisations**, sélectionner Position et **Toujours autoriser**.
Pour que l'application fonctionne avec précision, il est fortement conseillé de permettre un accès **exacte**. 
Si l'utilisateur choisi uniquement **Lorsque vous utiliser l'appli**, il n'y aura pas de notification.


## Détail d'implémentation
Le rayon dans lequel est déclenché la notification est de 100 mètres.
Le temps de validité d'une notification et d'une semaine, après ce délai le système notre requête qui avait pour but de nous indiquer l'entrée dans la zone géographique.
Le temps de réaction au plus court pour être notifié par le système est fixé à 1 minute.

## Suite de l'implémentation à envisagier 
L'application proposée se concentre sur une base simple qui propose deux layouts et les fonctionnalités nécessaires au geofencing. De nombreuses fonctionnalités pourraient être ajoutées afin d'avoir une application plus complète et agréable à l'emploi.  
Quelques fonctionnalités à ajouter:  
* Permettre la modification d'un geofence à partir de la liste en page d'acceuil.
* Permettre la sélection d'un ou plusieurs geofence présent dans la liste en page d'acceuil et pouvoir y apporter des actions comme supprimer la sélection.
* Offrir un layout dédié à un geofence qui présente ses informations tels que son titre, l'adresse fixée, sa durée de validité, etc.
* Lors de la définition du point sur la carte géographique, ajouter un cercle qui montre la zone dans laquelle la notification va être déclenchée.
* Préciser une plage horaire durant laquelle on souhaite que le geofence lance une notification. Ceci permettrait d'éviter de notifier l'utilisateur plusieurs fois dans une journée s'il amené à faire des aller-retour dans la zone définie alors que la notification peut avoir une importance à un moment précis de la journée.  
* Cette fonctionnalité amèneront la notion de tri de la liste des geofences dans l'ordre des alertes de la plus proche à la plus éloignée en temps.  
* Offrir à l'utilisateur le choix du rayon du cercle dans lequel la notification va être déclenchée.
* Ajouter des fonctionnalité à la map comme la recherche d'un lieu.
