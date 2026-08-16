# Campus Application
Eindproject voor Backend 1

## Beschrijving
Een Spring Boot-applicatie voor het beheren van campussen, lokalen, gebruikers en reservaties.

## Technologie
- Java 21
- Maven
- Spring Boot 4.1.0

## Database
De applicatie gebruikt een MySQL-database met de naam `campus_application`.

De databaseverbinding wordt lokaal ingesteld via de volgende omgevingsvariabelen:
- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`

Bij een nieuwe MySQL-database moet eerst schema.sql worden uitgevoerd.
Daarna kan eventueel data.sql worden uitgevoerd om de initiële testdata toe te voegen.

## Swagger

Wanneer `campus-api` actief is, kan de automatisch gegenereerde API-documentatie
bekeken worden via Swagger UI:

http://localhost:8080/swagger-ui/index.html

## H2-profiel

Naast MySQL kan de API ook gestart worden met een H2 in-memory database via het profiel `h2`.

Bij gebruik van H2 worden `schema.sql` en `data.sql` bij elke start opnieuw uitgevoerd,
omdat de in-memory database verdwijnt wanneer de applicatie stopt.

## Command-line applicatie

De map `campus-cli` bevat een aparte command-line frontend.

Start eerst `campus-api` op poort 8080 en start daarna `campus-cli`.

De CLI heeft geen rechtstreekse toegang tot de database.
Alle communicatie verloopt via de REST API met WebClient.

Via de CLI kan een gebruiker worden aangemaakt en kan een reservatie worden gemaakt.
Voor het aanmaken van een reservatie worden eerst de beschikbare lokalen via de API opgevraagd.