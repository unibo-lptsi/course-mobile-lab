# Laboratorio di Programmazione di Sistemi Mobile

## Esercitazioni

### Exercitazione 4: architettura efficace di applicazioni (ViewModel, View Binding, Repository, ...)

**Obiettivo**: familiarizzare con il pattern ViewModel in Android, che consente di mantenere lo stato da visualizzare sulla UI anche a fronte di cambiamenti di configurazione

1. Partire dal codice fornito in [/codelab/android-basics-kotlin-unscramble-app-starter/](/codelab/android-basics-kotlin-unscramble-app-starter/), che essenzialmente è lo stesso dello [starter](https://github.com/google-developer-training/android-basics-kotlin-unscramble-app/tree/starter) con minimi aggiustamenti (e.g., alla build)
2. Nota: il progetto fa anche uso del **View Binding** (abilitato nella build Gradle del modulo) per accedere in modo conveniente ai controlli UI del frammento
3. Introdurre un **Repository** per astrarre le possibili data source delle parole
    * Consultare il CodeLab [Repository Pattern](https://developer.android.com/codelabs/basic-android-kotlin-training-repository-pattern)
4. Seguire le istruzioni del CodeLab [Store data in ViewModel](https://developer.android.com/codelabs/basic-android-kotlin-training-viewmodel) per implementare un ViewModel
5. Provare a iniettare un repository nel viewmodel via dependency injection con Hilt
    * Consultare il CodeLab [Using Hilt in your Android app](https://developer.android.com/codelabs/android-hilt)
6. Provare ad applicare il **Data Binding** per collegare gli elementi del viewmodel direttamente nel layout
    * Consultare il CodeLab [Data Binding in Android](https://developer.android.com/codelabs/android-databinding)
7. Applicare la seguente modifica: quando l'utente "scrolla il cellulare", fare un riordino delle lettere della parola corrente. In altre parole, si chiede di implementare uno *shake detector* sfruttando l'accelerometro.
    * Consultare la doc relativa all'[accelerometro](https://developer.android.com/develop/sensors-and-location/sensors/sensors_motion#sensors-motion-accel)

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

Suggerimenti

- Gestione delle risorse drawable
    - Impostare un'immagine dato un drawable: `ImageView#setImageDrawable(Drawable)`
    - Recuperare un drawable (da un'activity): `getDrawable(R.id.<DRAWABLENAME>)`
    - Ottenere gli ID dei drawable
```kotlin
        val imageListId = ArrayList<Int>()
        val drawables: Array<java.lang.reflect.Field> = drawable::class.java.fields
        for (f in drawables) {
            imageListId.add(
                resources.getIdentifier(f.getName(), "drawable", packageName)
            )
        }
```
    
- Nota: un ulteriore approccio (preferibile) sarebbe quello basato su [AssetManager](http://developer.android.com/reference/android/content/res/AssetManager.html)

- Gestione degli [spinner](https://developer.android.com/develop/ui/views/components/spinner)
    - Gli spinner lavorano con oggetti [`adapter`](https://developer.android.com/reference/android/widget/Adapter)
```kotlin
// in an activity
        mySpinner = findViewById(R.id.my_spinner)
        val arrayAdapter = ArrayAdapter<String>(applicationContext, R.layout.text_view)
        arrayAdapter.addAll("abc", "foo", "bar")
        mySpinner.adapter = arrayAdapter

// res/layout/text_view.xml
<TextView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="@string/element_text">
</TextView>
```
