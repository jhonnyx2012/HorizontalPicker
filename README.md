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
* Puedes customizar los colores o dejar que HorizontalPicker tome los colores de la paleta de tu proyecto.

## Customización de:
* Color del background de la fecha seleccionada.
* Color del texto de la fecha seleccionada.
* Color del texto del mes y año.
* Color del texto del botón "Hoy".
* Color del background del día de hoy.
* Color del texto del día de hoy.
* Color del background del día de hoy.
* Color del texto las fechas no seleccionadas.
* Color del texto de los días de la semana.
* Color de la fecha seleccionada.
* Color de la fecha seleccionada.
* Opción de mostrar o no el botón "Hoy".
* Color del background del calendario.

## Notas
* De momento, solo es posible generar una cantidad finita de dias y este numero se genera en el hilo principal, por lo cual es recomendable no usar un numero mayor a 500.
* Este proyecto utiliza la libreria [JodaTime](https://github.com/JodaOrg/joda-time) para el manejo de las fechas.

## Ejemplos de uso
1- Añade la dependencia a tu proyecto

```groovie
dependencies {
    compile 'com.github.jhonnyx2012:horizontal-picker:1.0.5'
}
```  

2- Declara un HorizontalPicker en tu layout de la siguiente manera:
```xml
 <com.github.jhonnyx2012.horizontalpicker.HorizontalPicker
        android:id="@+id/datePicker"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
```

3- Setea un listener al HorizontalPicker para escuchar los cambios de fecha e inicia el picker.

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

4- Tambien puedes configurar la cantidad de dias a generar, el offset, setear una fecha directamente al picker y mucho más.

```java
    picker
          .setListener(this)
          .setDays(20)
          .setOffset(10)
          .setDateSelectedColor(Color.DKGRAY)
          .setDateSelectedTextColor(Color.WHITE)
          .setMonthAndYearTextColor(Color.DKGRAY)
          .setTodayButtonTextColor(getResources().getColor(R.color.colorPrimary))
          .setTodayDateTextColor(getResources().getColor(R.color.colorPrimary))
          .setTodayDateBackgroundColor(Color.GRAY)
          .setUnselectedDayTextColor(Color.DKGRAY)
          .setDayOfWeekTextColor(Color.DKGRAY )
          .setUnselectedDayTextColor(getResources().getColor(R.color.primaryTextColor))
          .showTodayButton(false)
          .init();
    picker.setBackgroundColor(Color.LTGRAY);
    picker.setDate(new DateTime().plusDays(4));
```
## Screenshots

![Screenshot](https://raw.githubusercontent.com/jhonnyx2012/HorizontalPicker/master/Screenshot_custom.png)
![Screenshot](https://raw.githubusercontent.com/jhonnyx2012/HorizontalPicker/master/Screenshot_palette.png)

## License
```text
  Copyright 2017 Jhonny Barrios

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
