# Informationen
Ein simpler noch unvollständiger Chat mit Oauth2 Authorisierung. Geschrieben in Java und Spring Boot mit MVC Pattern.
Zur Entwicklung ist eine H2 Datenbank eingebunden, diese kann jederzeit gegen eine andere ausgetauscht werden.
Für eine Github Oauth2 Authorisierung muss in Github eine Anwendung unter *settings*
angelegt werden und die clientId  & das clientSecret dieser Anwendung in der application.yml hinterlegt werden. Bei Google Oauth2 und anderen Plattformen, die Oauth2 anbieten, sollte es ähnlich funktionieren.