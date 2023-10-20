# Laboratorio di Programmazione di Sistemi Mobile

## Esercitazioni

### Esercitazione 3: layout e controlli grafici

**Obiettivo**: familiarizzare con diverse tipologie di layout (`ViewGroup`) e componenti grafici

![](imgs/exercise-layouts.png)

**Istruzioni**:

- Realizzare l'applicazione in figura. Lo si realizzi in 3 versioni:
    1. utilizzando `LinearLayout`
    2. (opzionale) utilizzando `RelativeLayout`
    3. utilizzando `ConstraintLayout`
- Si realizzi una variante del layout da utilizzare solo nella configurazione con orientamento dello schermo "portrait" (`port`), in cui 
    - l'icona prende una riga a parte ed è centrata orizzontalmente 
    - Titolo e sottotitolo si trovano sotto la figura e sono allineati a sinistra
- Si aggiunga un **menu contestuale** sull'icona che permetta all'utente di controllarne l'allineamento (a sinistra, al centro, a destra) 
- Si modifichi l'applicazione realizzata in Esercitazione 1/2 per chiedere conferma mediante una **`AlertDialog`** dopo il click di `buttonExplicit` prima di procedere con l'avvio della activity

### Esercitazione 2: risorse e localization (l10n)

**Obiettivo**: prendere confidenza con tipologie di risorse e con la localizzazione di applicazioni

**Istruzioni**:

- Partire dall'applicazione sviluppata in `Esercitazione 1`
- Sostituire stringhe "hardcoded" con risorse di tipo stringa, localizzate per il locale `en` (inglese) e `it` (italiano)
- Si aggiunga ad `ActivityA` diverse `TextView` ognuna dimensionata con una diversa unità (`px`, `dp`, `sp`) e si esplorino le differenze di visualizzazione su diversi dispositivi
- Si aggiunga uno stile (risorsa `style`) che specifichi colore e dimensione delle etichette create al punto precedente
- (Opzionale) Si aggiunga un'immagine bitmap per diverse tipologie di schermi e si esplorino le differenze di visualizzazione su diversi dipositivi
- (Opzionale) Si aggiunga un'immagine SVG come ulteriore `drawable` e si noti come l'immagine venga scalata senza problemi

### Esercitazione 1: activity e intent

**Obiettivo**: prendere confidenza con le `Activity` (e il loro ciclo di vita) e gli `Intent` (impliciti ed espliciti)

**Istruzioni**:

- Realizzare un'applicazione con due `Activity`: `ActivityA` (attività principale) e `ActivityB`
- L'`ActivityA` utilizza uno [`Spinner`](https://developer.android.com/develop/ui/views/components/spinner) per consentire all'utente di selezionare una lista di risorse `drawable`, e due `Button` (`buttonImplicit` e `buttonExplicit`) per svolgere un'azione
    - Al click di `buttonExplicit`, l'`ActivityA` avvia l`ActivityB` utilizzando un `Intent` esplicito, indicando la risorsa `drawable` da disegnare
    - Al click di `buttonImplicit`, l'`ActivityA` avvia l`ActivityB` utilizzando un `Intent` implicito, indicando la risorsa `drawable` da disegnare
- L'`ActivityB` recupera dall'intento la risorsa da visualizzare e la associa ad un componente `ImageView` che la "scala" in dimensioni
    - Si faccia riferimento all'attributo `android:scaleType` di `ImageView`
- Familiarizzare con il ciclo di vita delle activity implementando le callback `onCreate()`, `onDestroy()`, `onResume()`, e `onPause()`
- Familiarizzare con il salvataggio/ripristino dello stato transiente tenendo traccia del `drawable` selezionato