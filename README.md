# Skjelettprosjekt for TDT4100 prosjekt V2022

Dette repoet er et skjelettprosjekt for TDT4100 prosjektet våren 2022.

Vi har opprettet et eksempelprosjekt her, som ment for at dere skal kunne komme raskt igang med deres eget prosjekt.

## TL;DR

Lag en ny mappe i `src/main/java/` som er deres prosjekt. Opprett en startsfil for appen, slik som [ExampleProjectApp.java](src/main/java/exampleproject/ExampleProjectApp.java) og en kontroller som [ExampleProjectController.java](src/main/java/exampleproject/ExampleProjectController.java) i denne nye mappen. Lag så en mappe i `src/main/resources` med samme navn som prosjektet deres og et view som [App.fxml](src/main/resources/exampleproject/App.fxml) i denne nye mappen.

**Eventuelt**: Endre navn på filer og mapper fra "ExampleProject" til deres prosjektnavn.

## Litt rask info

Allerede nå er det mulig å kjøre filen [ExampleProjectApp.java](src/main/java/exampleproject/ExampleProjectApp.java) i VSCode for å få opp en liten kalkulator-app.

Denne filen er "startsfilen" til applikasjonen. Her settes tittel på appen, hvilken FXML-fil som skal brukes, og den er ansvarlig for å starte selve applikasjonen:

```java
primaryStage.setTitle("Example App"); // Setter tittel på vinduet
primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("App.fxml")))); // Sier at appen skal bruke "App.fxml"
primaryStage.show(); // Viser vinduet
```

Kontrolleren til applikasjonen er [ExampleProjectController.java](src/main/java/exampleproject/ExampleProjectController.java). Denne filen er "bindeleddet" mellom FXML-filen(e) og klassen(e) som skal brukes i applikasjonen. I dette eksempelprosjektet har den to metoder: `initCalculator` og `handleButtonClick`. I tillegg har den noen felter som er annotert med `@FXML`. Dette viser at de tilhører [FXML-filen](src/main/resources/exampleproject/App.fxml) vår. Her er navnet på variablene viktige. F.eks er `private Label result` på linje 12 bundet til `Label`-feltet på linje 15 i [FXML-filen](src/main/resources/exampleproject/App.fxml), siden denne har en `fx:id = "result"` og variabelen vår heter `result`:

```java
@FXML
private Label result; // Fra ExampleProjectApp.java

<Label fx:id="result" layoutX="257.0" layoutY="244.0" /> // Fra App.fxml
```

Noe liknende skjer med metoden `handleButtonClick`, som også er annotert med `@FXML`. Dette gjøres slik at vi "får tak i" denne metoden fra [FXML-filen](src/main/resources/exampleproject/App.fxml). `Button`-feltet i [FXML-filen](src/main/resources/exampleproject/App.fxml) har en `onAction="#handleButtonClick"`, som vil si at metoden `handleButtonClick`, som er annotert med `@FXML`, blir kjørt når vi trykker på knappen:

```xml
<Button layoutX="271.0" layoutY="188.0" mnemonicParsing="false" onAction="#handleButtonClick" text="Kalkuler" /> <!-- Fra App.fxml -->
```

Det som gjør at [kontrolleren](src/main/java/exampleproject/ExampleProjectController.java) og [FXML-filen](src/main/resources/exampleproject/App.fxml) er koblet sammen er attributten `fx:controller='exampleproject.ExampleProjectController'` på det aller ytterste elementet i [FXML-filen](src/main/resources/exampleproject/App.fxml).

```xml
<AnchorPane fx:id="background" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="400.0" prefWidth="600.0" xmlns="http://javafx.com/javafx/8.0.171" xmlns:fx="http://javafx.com/fxml/1" fx:controller="exampleproject.ExampleProjectController"> <!-- Fra App.fxml -->
```

Så, når vi trykker på knappen i appen blir som sagt metoden `handleButtonClick` kjørt. Det som skjer inne i denne metoden er først at vi oppretter en ny [kalkulator](src/main/java/exampleproject/Calculator.java). Ved opprettelse av en kalkulator trenger vi en `operator`. Denne henter vi ut fra hva en bruker av appen har skrevet inn i `TextField`-feltet med `fx:id="operator"`. Siden vi allerede har opprettet en variabel `private TextField operator`, som er annortert med `@FXML`, er denne allere linket til dette `TextField`-feltet, og vi kan hente ut teksten som er skrevet inn med `operator.getText()`.

```java
initCalculator(operator.getText()); // Kaller på initCalculator som oppretter en ny kalkulator. Operator.getText() henter ut teksten som er skrevet inn i `operator`-feltet.
```

Det samme gjelder nedover i metoden; vi henter ut verdier fra `firstNumber` og `secondNumber`. Det som er verdt å merke seg her er at de blir hentet ut som `String`s, men kalkulatoren vår krever `int`s. Derfor gjør vi de også om til integers. Her bør man og være litt forsiktige, da det ikke er gitt at brukere skriver inn gyldige tall. Derfor har vi wrappet dette inn i en `try/catch`, som sier ifra dersom tallet er ugyldig.

I tillegg til alt dette er det laget en liten [eksempel testfil](src/test/java/exampleproject/CalculatorTest.java). Ingenting spennende som skjer her, det er en test for konstruktøren til [kalkulator klassen vår](src/main/java/exampleproject/Calculator.java), samt en test for metoden `calculate` den har. Alle tester dere skriver til klassene deres legges altså inn i mappen `src/test/java/<deres_prosjekt>`.

## For å komme i gang med deres eget prosjekt

1. Inviter gruppemedlemmene dine til dette repoet, og gi de minst en `Developer`-rolle (helst `Maintainer`)
2. Klon dette prosjektet et sted på maskinen deres (ikke inne i Students-mappen, men gjerne i samme mappe denne ligger i).
    - Dersom du har aktivert 2FA på GitLab-kontoen din og blir bedt om innlogging ved kloning/pushing av/til repoet må du opprette en [personal access token](https://gitlab.stud.idi.ntnu.no/-/profile/personal_access_tokens) som har "read_repository" og "write_repository"-rettigheter. Deretter kan du logge inn med ditt feidebrukernavn som brukernavn og denne tokenen som blir laget til deg som passord. En guide for hvordan opprette personal access token finnes [her](https://docs.gitlab.com/ee/user/profile/personal_access_tokens.html#create-a-personal-access-token).
3. Lag en ny mappe i `src/main/java/` som er deres prosjekt.
4. Opprett en startsfil for appen deres, slik som [ExampleProjectApp.java](src/main/java/exampleproject/ExampleProjectApp.java) og en kontroller som [ExampleProjectController.java](src/main/java/exampleproject/ExampleProjectController.java) i deres nye prosjekt-mappe.
5. Opprett en ny mappe i `src/main/resources/` som er deres prosjekt.
6. Opprett en FXML-fil, slik som [App.fxml](src/main/resources/exampleproject/App.fxml) i deres nye prosjekt-mappe i `src/main/resources/`.
7. **HUSK** å legge inn `fx:controller='<deres_prosjekt>.<deres_kontroller>'` på det aller ytterste elementet i den nye FXML-filen deres, ellers vil ikke appen starte.

**Eventuelt**: Endre navn på filer og mapper fra "ExampleProject" til deres prosjektnavn.

## Reminder av nøkkelpunkter

| Nøkkelpunkt                              | Beskrivelse                             |
| ---------------------------------------- | --------------------------------------- |
| Andel av karakter                        | 35% (appen) + 15% (dokumentasjon/teori) |
| Frist for godkjenning av prosjektplan    | 25. februar                             |
| Innleveringsfrist                        | 29. april                               |
| Demonstrasjonsfrist hos læringsassistent | 03. mai                                 |
| Gruppestørrelse                          | 1 eller 2 personer                      |

### Anbefalte perioder å jobbe med prosjektet

| Uke   | Fra  | Til  | Beskrivelse                                 |
| ----- | ---- | ---- | ------------------------------------------- |
| 7-8   | 14/2 | 25/2 | Grunnklasser og brukergrensesnitt           |
| 11    | 14/3 | 18/3 | Lagring of filhåndtering                    |
| 14    | 04/4 | 08/4 | Testing                                     |
| 16-17 | 19/4 | 29/4 | Fullføre appen med tilhørende dokumentasjon |

**_LYKKE TIL_**
