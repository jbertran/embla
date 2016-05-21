
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

### Fonctionnement d'OpenGL

Le fonctionnement d'OpenGL est similaire à celui d'une machine à états. Pour 
interagir avec des données spécifiques sur la carte graphique, il faut mettre 
la machine à états OpenGL dans l'état correspondant. En particulier, en ce 
qui concerne l'optimisation des transferts CPU/GPU, il est nécessaire de 
lier les buffers de flottants correspondant à nos données à la machine OpenGL
avant de réaliser les opérations de dessin. Ceci nécéssite de conserver les 
identifiants.

Le dessin d'une forme simple se déroule comme ceci sur OpenGL:

```java
// Lier le shader program à la machine
GL20.glUseProgram(shader_progid);
// Lier l'ID du VAO enregistrant les buffers de la forme
GL30.glBindVertexArray(vao_shapeid);
// Lier l'ID du buffer positions à la machine
GL20.glEnableVertexAttribArray(0);
GL11.glDrawArrays(GL11.GL_TRIANGLE_FAN, 0, summit_count);
GL20.glDisableVertexAttribArray(0);
// Lier l'ID du buffer couleur à la machine
GL20.glEnableVertexAttribArray(1);
GL11.glDrawArrays(GL11.GL_COLOR_ARRAY, 0, 1);
GL20.glDisableVertexAttribArray(1);
// Délier le VAO de la machine
GL30.glBindVertexArray(0);
// Délier le shader program de la machine
GL20.glUseProgram(0);
```

### Gestion des formes

## Boucle de rendu

Comme décrit dans la partie <TODO: numéroter>, la boucle de rendu d'OpenGL 
est implémentée dans notre classe GameEngine. OpenGL requiert intrinsèquement 
de redessiner la scène à chaque tour de boucle, ce qui fait que notre approche
pour minimiser les transferts vers la carte graphique est de vérifier quels objets
ont changé dans la scène, et ne modifier que ceux-ci sur la carte graphique.

Son mode de fonctionnement est de vérifier la présence de changements fournis après
le parcours du modèle par les signaux, et répercuter ces modifications sur les 
buffers de la carte graphique. On peut ensuite afficher la scène correctement, en 
parcourant l'arbre du modèle. Le rendu au fil du parcours de l'arbre nous permet de 
garantir automatiquement les superpositions des formes en fonction de la profondeur 
des formes.

On vérifie au passage si notre liste d' objets OpenGL <TODO: continuer>

```java
// Propagate model changes to GL buffers
if (changes.isPresent()) {
	for (Model modelch : changes.get()) {
		GLShape s = glShapes.get(modelch.ID);
		if (s != null)
			s.propagate(modelch);
 		else
			throw new RuntimeException(
				"Attempted to propagate changes to GLShape unknown to the engine");
		}
}
// Redraw the scene
draw_model_item(world);
```
