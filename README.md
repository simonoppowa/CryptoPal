## Softwareentwicklung Projekt
# CryptoPal

### Beschreibung
Ein Bezahldienst in Form von PayPal mit Unterstützung für Crypto-Währungen wie Bitcoin.

### Funktionen
* Bezahlen von Online Produkten/Services
* Rückerstattungen von Online Produkten/Services
* Senden von Geld an andere Nutzer
* Live Währungspreise (von Europäischer Zentralbank und CryptoCompare.com)
* Austausch von Währungen (EUR, USD, GBP, JPY, BTC, ETH, XRP)
* Einsehen von Beträgen und Transaktionen

### TODOs
* Fix remaining bugs
* Make Software threadsafe
* Implement different languages function
* Change account information / delete account function
* Make models @ConversationScoped
* Implement taxes and payment fee

### Partner Projekte
* Importiert PaymentService / RefundService: BlackCastle, BlueBox
* OrderService wird importiert von: BlackCastle (Zusätzliche Details in Payment wird angezeigt)


### Sources:
#### APIs:
* [Currency Prices API](https://exchangeratesapi.io/) made by [Madis Väin](https://github.com/madisvain), prices from [European Central Bank](https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_exchange_rates/html/index.en.html)
* [Crypto Currency Prices API](https://min-api.cryptocompare.com/documentation/) from [CryptoCompare](https://www.cryptocompare.com)


#### Images:
* [Bitcoin Icon](https://www.flaticon.com/free-icon/bitcoin-logo_25180) made by [Dave Gandy](https://www.flaticon.com/authors/dave-gandy) from www.flaticon.com
* [Ethereum Icon](https://www.flaticon.com/free-icon/ethereum_1346606) made by [Freepik](https://www.flaticon.com/authors/freepik) from www.flaticon.com
* [Ripple Icon](https://www.flaticon.com/free-icon/ripple_1289731) made by [Freepik](https://www.flaticon.com/authors/freepik) from www.flaticon.com
* [Norwegian Landscape](https://ccsearch.creativecommons.org/photos/86b8609a-2cbc-4a29-a79f-8543afb7e25f) made by [Anders L\xf6nnfeldt](https://ccsearch.creativecommons.org/photos/86b8609a-2cbc-4a29-a79f-8543afb7e25f) from ccsearch.creativecommons.org licensed under CC BY-NC-ND 4.0

#### Fonts:
* [Righteous](https://fonts.google.com/specimen/Righteous?selection.family=Righteous) made by [Astigmatic]() from fonts.google.com licensed under Open Font License
* [Roboto](https://fonts.google.com/specimen/Roboto) made by [Christian Robertson]() from fonts.google.com licensed under Open Font License

#### Content:
* Softwareentwicklung Vorlesung OTH Regensburg WiSe 18/19
* [Java EE 8 Application Development by David R. Heffelfinger from Packt](https://www.packtpub.com/application-development/java-ee-8-application-development)
* [Application - Petstore Java EE 7 by agoncal](https://github.com/agoncal/agoncal-application-petstore-ee7)

#### Software:
* Java 11
* Java EE 7
* Wildfly 14
* Hibernate
* SLF4J
* Jersey
* Primefaces
* JaxWS