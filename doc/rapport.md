
% PSTL \\ FRP et voyage dans le temps
% Guillaume Hivert, Jordi Bertran de Balanda}
% 25 Mai 2016

# Introduction

Ce rapport présente le travail effectué pour l'UE projet PSTL (4I508), réalisé
sous la direction de Frédéric Peschanski.

## Sujet original

Le titre du sujet de départ du projet était "FRP et voyage dans le temps". Ce
projet avait pour but d'explorer l'approche de programmation Functional Reactive
Programming. Au fil du déroulement du projet, nous avons été amenés à
privilégier certains aspects du projet au profit d'autres.

Pour rappel, les tâches du projet étaient les suivantes:

* L'intégration de signaux de premier ordre, avec les combinateurs adéquats pour
fournir à l'utilisateur un large panel de possibilités quant à la description
des comportements
* La construction d'un modèle basé sur des structures immutables
* Les mises à jour construisant les vues à partir du modèle, gérées de façon à
émuler l'approche d'ELM et de React.

## Sujet final

# Ambitions

## Functional Reactive Programming

### Définitions

## Gestion du modèle

## Live Coding

# Embla

## Vue d'ensemble

### Architecture

Notre application se divise en trois parties distinctes.

* Du côté Clojure, la définition des macros offrant à l'utilisateur d'interagir
avec le modèle
* Du côté Java:
  * La définition du modèle structuré, qui est pour nous un arbre de formes
  (rectangles, triangles, sprites...)
  * Le pendant OpenGL du modèle, sous la forme d'un dictionnaire identifiant
  Embla / instance de classe forme OpenGL, qui ne sert qu'à retenir les
  identifiants nécessaires pour redessiner les formes géométriques à partir des
  données déjà présentes sur la carte graphique.

###

## Signaux

## Modèle

## Vue
