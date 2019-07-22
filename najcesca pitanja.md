Odgovori na najčešća pitanja

- Svi dijelovi (3) aplikacije moraju biti odvojeni. Radar i simulator mogu dijeliti zajedničku memoriju (mapa). Prilikom pokretanja ova dva dijela, u zajedničkom dijelu (klasi gdje je mapa i main) prave se odvojene instance za radar i simulator. Aplikacija za prikaz mora biti odvojena. Za lakše testiranje može se napraviti zajednička klasa u kojoj se pokreću sve aplikacije ili neka shell skripta
- Za rakete je potrebno predvidjeti (implementirati) logiku kretanja bez obzira što se ne koriste u simulaciji
- Neki atributi se mogu ponoviti u klasama ako ne postoji bolji način organizacije modela
- Matrica ima dimenzije 100x100, a pošto se svi parametri čuvaju u config fajlu to znači da je matrica dimenzija nxm. Za potrebe testiranja može se smanjiti broj polja, ali rješenje mora raditi i sa 100x100 (bez obzira što prikaz na malom ekranu može biti ograničen)
- Ako se strana letjelica pojavi uz rub mape, tada jedan domaći vojni avion ide pored, a drugi iza. Prilikom presretanja strane vojne letjelice, domaći avioni ne moraju letjeti istom brzinom, a može se desiti da strana letjelica pobjegne, tj. da domaće letjelice ne mogu da stignu stranu. Susret podrazumijeva dolazak na isti nivo, tj. istu liniju (polje pored ili iza ako je uz rub).
- Simulacija nema kraj, već se završava kada se ručno ugase aplikacije.
- Prilikom sudara ne sklanjaju se sve letjelice koje su u prostoru, već samo one koje učestvuju u sudaru.
- Na jednom polju može biti više letjelica na različitim visinama.
- Opseg visina može biti ograničen na nekoliko kako bi se mogao simulirati sudar.
- Nije potrebno praviti dodatnu aplikaciju za izmjenu properties fajlova. Parametri će se mijenjati kroz Notepad ili sličan program.
- Kretanje je pravolinijsko, polje za polje. Nije dozvoljeno da se letjelica okrene u suprotnom pravcu u jednom polju.
- Metode koje se ne koriste u simulaciji, a dio su modela trebaju biti implementirane. Dovoljno je da implementacija bude ispis teksta.


Sve što nije definisano u tekstu projektnog zadatka ili u ovim napomenama studenti treba sami da riješe. Namjerno su izostavljeni detalji nekih scenarija zbog toga što nisu bitni za rad kompletnog sistema.

Ne postoji samo jedno rješnje ili najbolja organizacija klasa. Studenti treba da procijene koja struktura aplikacije rješava postavljene probleme. Zadatak se može riješiti na više načina, pri čemu treba voditi računa da su performanse sistema dobre i da je obezbijeđena proširivost.

Studenti sami biraju razvojno okruženje u kojem će raditi projektni zadatak (Eclipse, NetBeans, IntelliJ, ...) i sami podešavaju okruženje. U slučaju problema prilikom podešavanja razvojnih okruženja dozvoljeno je, a i preporučeno, potražiti uputstva na internetu. Na ovom kursu objavljeni su linkovi sa uputstvima za podešavanje GUI projekata nekih razvojih okruženja.
