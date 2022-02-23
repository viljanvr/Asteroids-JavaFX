Beskrivelse av appen: Kort om spillet/appen, hva skal den gjøre? Hva er målet med spillet?

- Grunnklasser: Fortelle kort hva de to (minimum to, kan ha flere) grunnklassene skal inneholde, og hvilken klasse som skal ha noen form for kalkulasjoner eller annen logikk.
- Filbehandling: Kort setning om hvilken informasjon som skal leses fra og skrives til fil.
- Testing: Kort om hva som skal testes i appen. Merk: I den delen av prosjektet som går på testing, krever vi at den viktigste funksjonaliteten dekkes av tester, og ønsker derfor at dere tenker over dette allerede nå, slik at dere unngår å lage prosjekter som blir store og omfattende å lage tester til.

### Beskrivelse av Appen

Vi skal lage Asteroids spillet hvor du styrer et romskip med piltastene og skyter ned astroider for å overleve. For hver astroide du skyter så får du poeng, og scoren til spilleren lagres sammen med navnet til en fil.

### Grunnklasser:

- _Sprite_-klasse for alle synlige objekter, astroider, laser, og skip
- _Vector_-klasse som holder posisjon/fart til alle sprite-objekter
- _SpaceCraft_- og _Asteroid_- (og kanskje _Laser_-) klasse som er arver fra Sprite-klassen.

### Filbehandling:

Det skal være mulig å lagre en score sammen med navnet til spilleren til en fil. I spillet skal et scoreboard som leser fra fil være synlig.

### Testing

Vi skal teste alle metoder til objektene våre. Dette er noen eksempler på hva vi kan gjøre, men mye av funksjonaliteten kommer underveis.

- Vektor-objekter kan vi teste funksjoner for vinkler, størrelse og konstruktører.

- For Sprite-objekter tester vi at posisjon endrer seg riktig i forhold til fart.

- For Astroide-objekter kan vi teste kollisjon og at du får poeng av å ødelegge astroiden.

- For SpaceCraft-objektet tester vi at den skyter ut lasere i riktig retning i forhold til rotasjonen.

- Vi har også tester for filbehandling og leaderboard.

### delegation

- update rotate function -A
- remove acceleration vector and add constructor -A
- Controller: add even listeners for shooting and thrusting -V
- astroid class -V
- deaccelerate method for spaceship -V
- lazer class and shooting -A
-
