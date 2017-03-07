# HorizontalPicker
DatePicker horizontal con selección smooth por día.

## Caracteristicas
* Smooth swipe para cambio de día.
* Selección de fecha clickando un dia.
* Vista de mes y año.
* Botón Today para seleccionar el día actual de manera rapida.
* Días de la semana y meses en el idioma por defecto.
* Configuración de día inicial.
* Configuración de cantidad de dias a generar (Default 120).
* Configuración de offset de dias a generar antes del dia de inicio (Default 7).

NOTA 1: De momento, solo es posible generar una cantidad finita de dias y este numero se genera en el hilo principal, por lo cual es recomendable no usar un numero mayor a 500.
NOTA 2: Este proyecto utiliza la libreria JodaTime para el manejo de las fechas.

## Ejemplo de uso
1. Declarar un HorizontalPicker en tu layout de la siguiente manera:

```xml
 <com.jhonnyx.horizontalpicker.HorizontalPicker
        android:id="@+id/datePicker"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
```

2. Setear un listener al HorizontalPicker para escuchar los cambios de fecha

```java

public class MainActivity extends AppCompatActivity implements DatePickerListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HorizontalPicker picker= (HorizontalPicker) findViewById(R.id.datePicker);
        picker.setListener(this);
        picker.setDate(new DateTime().plusMonths(1));
    }

    @Override
    public void onDateSelected(DateTime dateSelected) {
        Log.i("HorizontalPicker","Fecha seleccionada="+dateSelected.toString());
    }
}

```
3. Chupala!

