# Informationen
Ein simpler noch unvollständiger Chat mit Oauth2 Authorisierung. Geschrieben in Java und Spring Boot mit MVC Pattern.
Zur Entwicklung ist eine H2 Datenbank eingebunden, diese kann jederzeit gegen eine andere ausgetauscht werden.
Für eine Github Oauth2 Authorisierung muss in Github eine Anwendung unter *settings*
angelegt werden und die clientId  & das clientSecret dieser Anwendung in der application.yml hinterlegt werden. Bei Facebook und Auth0 oder und anderen Plattformen, die Oauth2 anbieten, sollte es ähnlich funktionieren. In der application.yml müssen dann die clientID's und clientSecret's der Applikationen auf den jeweiligen Plattformen sowie bei Auth0 noch zusätzlich die Url der Anwendungsdomäne eingetragen werden. 