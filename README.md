# Rapport d'avancement 

La semaine dernière je venais de terminer la première partie sur la création de la liste d'items avec `RecyclerView` (oui je suis un peu en retard), et j'ai commencé la partie 2 jusqu'à la création complète de la classe `Joke`.

Toute la partie 1 est à jour sur la branche **master** dans le dossier [**2ndApp_ChuckNorris**](https://github.com/TiphLamy/Android/tree/master/2ndApp_ChuckNorris).
La partie 2 étant toujours en cours de développement, tous les commits de la partie 2 sont dans la branche **Fetch_Jokes** toujours dans le dossier [**2ndApp_ChuckNorris**](https://github.com/TiphLamy/Android/tree/Fetch_Jokes/2ndApp_ChuckNorris). 

Une fois cette partie terminée et fonctionnelle, je vais mettre à jour la branche master.

# Jeudi 26/03/2020

## Transformation de String en Joke

J'ai tout d'abord modifié le paramètre que prend notre `JokeAdapter` afin qu'il prenne une liste d'objets de type `Joke` et changé légèrement la fonction `onBindViewHolder()`.

Le problème qui se posait maintenant était comment faire en sorte que dans notre **MainActivity.kt**, notre liste de `String` se transforme en liste de `Joke`. J'ai effectué plusieurs approches :

- Premièrement, j'ai juste convertis le type `String` en `Joke` avec la fonction `map`. Cependant, ça ne pouvait pas marcher puisque la classe `Joke` possède plusieurs paramètres et je ne lui en donnais qu'un.
- J'ai créé une `extension function` qui créé un objet `Joke` en lui passant en paramètres des chaînes vides et dans le paramètre `value` la citation. Ainsi, j'ai pu afficher la liste de `Joke`

Ces changements sont visibles dans le commit [Part 2.1 changed String to Joke working well](https://github.com/TiphLamy/Android/commit/8442c5cfa1de3d8ffa9f58813d7297c008cc3718#diff-a38d722e9c0d985c0110c1c53ea18eda)

## Retrofit

Après avoir fait les différents imports, j'ai créé l'interface `JokeApiService` sans trop de difficulté. Je ne savais juste pas quoi mettre comme URL, j'y ai donc juste mis `"Joke"`.

En suivant les étapes, j'ai réussi à implémenter `JokeApiServiceFactory` également. Un des problèmes que j'ai eu était que le compilateur ne reconnaissait pas `Json.asConverterFactory` et ce malgré tous les imports que j'avais. Après avoir cherché sur internet, j'ai remarqué qu'il me manquait juste une implémentation dans le gradle.

L'autre problème était lors de la création de notre `JokeApiService`. J'avais au départ uniquement écrit 
>return builder.create(JokeApiService)

Cependant j'obtenais une erreur disant que la classe `JokeApiService` ne possédait pas d'objet compagnon et il fallait donc utiliser une méthode Kotlin similaire au `getClass()`. J'ai ensuite trouvé comment l'écrire sur internet et je n'ai maintenant plus d'erreur 

Ces changements sont visibles dans le commit [Part 2.3 implement JokeApiFactory but didn't tested](https://github.com/TiphLamy/Android/commit/3b8e3237aae15996f92ffe6c09ceab76a71b3fa5#diff-a38d722e9c0d985c0110c1c53ea18eda)

## Appel de l'api pour générer une citation

J'ai créé une variable `jokeService` qui va recevoir le `Single<Joke>` dans le MainActivity.kt. 

Je me suis arrêtée au début de l'implémentation de la fonction `subscribeBy()`, n'ayant pas encore bien compris comment l'utiliser et y faire appel. 
