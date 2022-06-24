# TètKole

## Français

### Outils utilisés

* JavaFx 17.0.2 (Azul community)
* Gradle 7.4.2
* Spotbugs 5.0.7
* Innosetup 6.2.0

### Branche master (défaut)

Cette branche contient la dernière version fonctionnel de TètKole.

### Branche develop

Cette branche permet de développer de nouvelle fonctionnalités ou corriger des bugs existant sans impacter la version fonctionnel.<br>
Une fois la branch Develop satisfaisante, on la merge dans la branche master.

### Branche website

Cette branche contient toute la partie site web de TètKole sur git.<br>
Le lien du site web est -> https://noars.github.io/TetKole/

### Workflow

Actuellement ce dépôt contient 3 workflows :

* 1 pour l'intégration continue -> .github/ci.yml
* 1 pour automatiser les releases -> .github/release.yml
* 1 pour mettre a jour la page web si il y a eu des modification (gérer par git lui même)

### Fonctionnement de la release de TètKole

Lorsque l'on push dans la branche master, une release est automatiquement généré grâce au workflow release.yml qui se trouve dans le dossier ".github".<br>
Le numéro de la release est toujours la dernière version + 1. <br>
C'est à dire, si la dernière version est "TètKole 1.0.7", alors la prochaine release sera "TètKole 1.0.8".<br>
Pour changer les chiffres supérieurs il faut le faire a la main en éditant la dernière release.

### Fonctionnement des recherches de bugs via Spotbugs



## English

### Used tools

* JavaFx 17.0.2 (Azul community)
* Gradle 7.4.2
* Spotbugs 5.0.7
* Innosetup 6.2.0

### Master branch (default)

This branch contains the last working version of TètKole.


### Develop branch

This branch allows you to develop new features or fix existing bugs without impacting the functional version.<br>
Once the Develop branch is satisfactory, we merge it into the master branch.


### Website branch

This branch contains all the website part of TètKole on git.<br>
The website link is -> https://noars.github.io/TetKole/

### Workflow

Currently this repository contains 3 workflows:

* 1 for continuous integration -> .github/ci.yml
* 1 to automate releases -> .github/release.yml
* 1 to update the web page if there have been changes (managed by git itself)

### Operation of the TètKole release

When you push in the master branch, a release is automatically generated thanks to the release.yml workflow which is in the ".github" folder.<br>
The release number is always the latest version + 1. <br>
That is, if the latest version is "TètKole 1.0.7", then the next release will be "TètKole 1.0.8".<br>
To change the upper numbers it must be done by hand by editing the latest release.