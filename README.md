# HorizontalPicker
DatePicker horizontal con selección smooth por día.

## Características
* Selección de fecha con smooth swipe.
* Selección de fecha clickando un dia.
* Selección de fecha desde el objeto HorizontalPicker.
* Vista de mes y año.
* Botón Today para seleccionar el día actual de manera rapida.
* Días de la semana y meses en el idioma por defecto.
* Configuración de cantidad de dias a generar (Default 120).
* Configuración de offset de dias a generar antes del dia actual (Default 7).

## Notas:
* De momento, solo es posible generar una cantidad finita de dias y este numero se genera en el hilo principal, por lo cual es recomendable no usar un numero mayor a 500.
* Este proyecto utiliza la libreria [JodaTime](https://github.com/JodaOrg/joda-time) para el manejo de las fechas.

## Ejemplos de uso
1- Añade el repositorio a tu proyecto

```groovie
    repositories {
    maven {
        url  "http://dl.bintray.com/jhonnyx2012/HorizontalPicker" 
    }
}
```  

2- Añade la dependencia

```groovie
 dependencies {
    compile 'com.github.jhonnyx2012:horizontal-picker:1.0.0'
}
```  

3- Declara un HorizontalPicker en tu layout de la siguiente manera:
```xml
 <com.jhonnyx.horizontalpicker.HorizontalPicker
        android:id="@+id/datePicker"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
```

4- Setea un listener al HorizontalPicker para escuchar los cambios de fecha e inicia el picker.

```java
public class MainActivity extends AppCompatActivity implements DatePickerListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HorizontalPicker picker= (HorizontalPicker) findViewById(R.id.datePicker);
        picker
                .setListener(this)
                .init();
    }

    @Override
    public void onDateSelected(DateTime dateSelected) {
        Log.i("HorizontalPicker","Fecha seleccionada="+dateSelected.toString());
    }
}
```

5- Tambien puedes configurar la cantidad de dias a generar, el offset y setear una fecha directamente al picker.

```java
    picker
          .setListener(this)
          .setDays(20)
          .setOffset(10)
          .init();
    picker.setDate(new DateTime().plusDays(4));
```
## Screenshots

![Screenshot](https://raw.githubusercontent.com/jhonnyx2012/HorizontalPicker/master/Screenshot.jpeg)
