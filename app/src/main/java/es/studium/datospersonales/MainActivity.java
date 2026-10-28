package es.studium.datospersonales;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

//Añadimos las siguientes importaciones para poder llevar a cabo la traduccion(te lo pide el programa para que funcione)

import android.widget.AdapterView;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;

import java.util.Locale;


/*
    * implements view... se refiere a que nuestra clase mainActivity va a implementar una interfaz que
    * permite que gestionemos click. Despues implementaremos el metodo onClick(). Sin este metodo,
    * nuestro programa no va a funcionar porque la interfaz se queda incompleta.
    *
    *
    */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    /*Antes de meterle mano al MainActivity, hemos tenido que construir toda la parte
    visual del formulario vertical. Ahora vamos a conectar todo eso con
    el MainActivity para que nos funcione.
    */
        /*
        * Primero declaramos las variables dentro de la clase MainActivity, pero fuera de onCreate()
        * Lo hacemos así porque necesitamos utilizar esas variables en varios métodos de la clase,
        * no solamente dentro de onCreate().
        * No se nos puede olvidar importar las clases necesarias, de lo contrario
        * no funcionara (EditText...)
        */

    EditText editNombre;
    EditText editApellidos;
    EditText editEdad;
    RadioGroup grupoGenero;
    Spinner spinnerEstado;
    Spinner spinnerIdioma;
    Switch switchHijos;
    TextView textoError;
    Button botonMostrar;
    Button botonLimpiar;

        /*Con esto solo hemos DECLARADO las variables,
        pero todavía NO las hemos CONECTADO con ningún componente XML

        */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
            /*Para conectar las variables con XML, dentro de onCreate pero después de setContentView escribiremos
            * el siguiente código: */

        editNombre=findViewById(R.id.editNombre);
        editApellidos=findViewById(R.id.editApellidos);
        editEdad=findViewById(R.id.editEdad);
        grupoGenero=findViewById(R.id.grupoGenero);
        spinnerEstado=findViewById(R.id.spinnerEstado);
        spinnerIdioma=findViewById(R.id.spinnerIdioma);
        switchHijos=findViewById(R.id.switchHijos);
        textoError=findViewById(R.id.textoError);
        botonMostrar=findViewById(R.id.botonMostrar);
        botonLimpiar=findViewById(R.id.botonLimpiar);

                /*Una vez hecho esto,podemos usar Java para leer o modificar esos elementos*/

                /*Lo colocamos en esta posicion exacta porque con el setContentView cargamos el diseño XML de la pantalla.
                 * Despues con el findViewById() podemos buscar los componentes de ese diseño y acceder a ellos desde java.
                 * Si intentamos buscar estos componentes antes de cargar el diseño en sí(es decir, escribimos
                 * el codigo anterior antes de setContentView), findViewById nos dará null (es decir, no encuentra el componente en cuestión en el diseño XML cargado,
                 * lo cual es normal porque no hemos cargado nada todavía)
                 * */

                /*
                Llamo al metodo configurarIdioma() despues de los findViewById() porque
                primero tengo que identificar el spinnerIdioma que se va a usar en el metodo (Si no lo defino, no va a funcionar nunca).
                A su vez, lo meto en onCreate() para que el cambio del idioma esté preparado desde el primer momento
                que el cliente abre mi aplicación (así evitamos posibles fallos).
                * */

        configurarIdioma();


            /*Asignamos los Listeners. Basicamente un listener es un mecanismo que detecta eventos del cliente (como pulsar el boton en este caso), y ejecuta una accion cuando esto ocurre.
            Con setOnClickListener indicamos quien se encarga de gestionar
            * la pulsacion de los botones. Al usar this, nos referimos al objeto actual (en este caso
            * en particular es el MainActivity). Por ende y siendo redundantes, estamos estableciendo que los clics de ambos botones
            * seran gestionados por el MainActivity*/

        botonMostrar.setOnClickListener(this);
        botonLimpiar.setOnClickListener(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
                /*Fuera de onCreate pero DENTRO de la clase mainActivity creamos el metodo onClick*/

            /*
            @Override lo tenemos que usar para indicar a java que estamos implementando un metodo declarado por una interfaz
            , o en su defecto, heredado de otra clase.
            onClick es el metodo que se ejecuta cuando pulsamos un boton TRAS haber DEFINIDO en nuestro codigo
            los LISTENERS correspondientes a tales botones.
            View es una clase que representa los componentes visuales (en este caso son los botones9
            view es una variable que representa el boton que vamos a pulsar (mostrarDatos/Reiniciar)
            view.getId nos devuelve el id del boton (o componente) que ha pulsado el cliente.
            mostrarDatos y limpiarDatos son 2 métodos que hemos creado
            Nota: Podemos aplicar el metodo onClick en esos botones porque antes hemos
            establecido los listeners correspondientes a los mismos
            * */
    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.botonMostrar) {
            mostrarDatos(); //Es decir, si pulsamos el botonMostrar se ejecuta el metodo mostrarDatos
        }
        else if (view.getId()==R.id.botonLimpiar) {
            limpiarDatos(); //es decir, si pulsamos el otro boton, se ejecuta el metodo limpiarDatos.
        }
    }

   private void mostrarDatos() {
        String nombre= editNombre.getText().toString().trim();
        String apellidos= editApellidos.getText().toString().trim();
        String edadTexto= editEdad.getText().toString().trim();
               /*En pocas palabras, de los campos nombre,apellidos y edad introducidos
               * por el usuario, queremos obtener lo que han introducido, lo transformamos
               * a string y finalmente quitamos los espacios anteriores y posteriores
               * a la expresion introducida (así evitamos errores)*/

                 //Ahora vamos a escribir el comportamiento en java cuando el cliente no rellene algun campo:

       String errores=""; //Esta es la variable donde guardamos los mensajes de error.

               /* Vamos a comprobar si los campos nombre,apellidos y edadTexto están vacíos*/

       if (nombre.isEmpty()==true) {
           errores=errores+getString(R.string.falta_nombre)+" ";
                /*nombre.isEmpty: es un booleano que devuelve true si el nombre esta vacio
                 .string nos indica que lo que buscamos esta en el strings.xml
                 .falta_nombre es el id del string que buscamos en el strings.xml
                 getString() da el contenido del string que buscamos
                 " " lo ponemos para que si faltan varios campos, no se superpongan uno detrás de otro.
                 */
       }

       if (apellidos.isEmpty()==true) {
           errores=errores+getString(R.string.falta_apellidos)+ " ";

       }
       if (edadTexto.isEmpty()==true) {
           errores=errores+getString(R.string.falta_edad)+" ";
       }

            /*Los primeros 3 if lo que hacen es detectar y guardar los errores.
            El siguiente if decide que hacer en funcion de si hay errores o no:
                -Si hay errores, los muestra en el textView y va a terminar el metodo usando el return
                (así conseguimos evitar generar una notificacion con datos incompletos)
                -Si no hay errores, borra los mensajes de error anteriores y permite continuar con el metodo mostrarDatos.
            *
            */
        if (errores.isEmpty()==false) {
            textoError.setText(errores);  //setText introduce lo que haya en su interior en textoError.
            return;
        }
        else {
            textoError.setText("");

        }

                  //Una vez que hemos comprobado los campos obligatorios, ya si podemos obtener la informacion para construir el mensaje.
        int edad;



        try {
            edad=Integer.parseInt(edadTexto); //Esto convierte el texto de la edad en un NUMERO.
        }
        catch (NumberFormatException e) {
            textoError.setText(R.string.edad_invalida); //La conversión puede salir mal, por lo que tenemos que gestionar el posible
            return;
        }

        String mayoriaEdad;
                //Este if-else nos sirve para determinar si la persona es mayor o menor
                //de edad y guardar el resultado para añadirlo en la notificacion final mediante el comando toast.
                //Usamos el nombre mayoriaEdad para guardar el resultado que obtengamos tras comprobar la edad, sea mayor o menor de edad.

        if (edad>=18) {
            mayoriaEdad=getString(R.string.mayor_edad);

        }
        else {
            mayoriaEdad=getString(R.string.menor_edad);
        }

                /*
                Ahora obtenemos el género.
                grupoGenero recordamos que es el grupo que contiene los botones de Hombre y Mujer.
                getCheckedRadioButtonId() nos dice el id del BOTON SELECCIONADO en el radioGroup(tuve que buscar este metodo en internet).
                idGenero es donde guardamos ese id.
                genero es una variable para guardar el genero
                 */
        int idGenero = grupoGenero.getCheckedRadioButtonId();

        String genero;

        //Ahora miramos que botón está seleccionado.

       if (idGenero==R.id.radioHombre) { //Con esto miramos si el id del boton seleccionado coincide con el id de Hombre.
           genero=getString(R.string.hombre_texto);
       }
       else if (idGenero==R.id.radioMujer) {
           genero=getString(R.string.mujer_texto);
       }
       else {
           genero=getString(R.string.genero_no_indicado); // No lo dice la practica,
           //pero lo añado porque todas las aplicaciones incluyen que el cliente
           // no introduzca su género.
       }

           /*Ahora obtenemos el estado civil. Dado que ya tenemos creado
           el spinner y sus posibles opciones en el XML, no tenemos que añadir nada mas alli. Solo modificar los estados
           y cambiar de soltero a solter@ y así. Así evitamos tener que diferenciar los casos. (soltero-soltera,casado-casada...)
           Ademas, buscando, encontramos que hay una funcion que simplifica mucho el codigo que es getSelectedItem() (ofrecida por el copilot).
           spinnerEstado es nuestro desplegable. getSelectedItem() me da la opcion seleccionada de ese desplegable
           toString, como ya hemos comentado otras veces, convierte esa opcion en una cadena de texto.

           * */

       String estadoCivil=spinnerEstado.getSelectedItem().toString();

       //A posteriori, para evitar que si el cliente no selecciona un estado civil
       //salga en la notificacion "estado civil no indicado" tenemos que hacer lo siguiente.
       //Basicamente, si la posicion del spinner escogida es 0, significa que no ha cambiado ese campo
       //Por ende, establecemos que ese campo valga "estado civil no indicado" (que lo añadimos en XML).
       if (spinnerEstado.getSelectedItemPosition() == 0) {
           estadoCivil = getString(R.string.estado_no_indicado);
       }


        /*A continuacion vamos a comprobar si tiene hijos.
        Ya tenemos creado el switch y conectado a java mediante findViewById.
        * Ahora lo que vamos a hacer es saber si está activado o desactivado el switch
        Añadimos textos a strings.xml
        switchHijos.isChecked() me dice si el interruptor esta activado.
        Si sale que si (true) guardamos el valor "con hijos"
        Si sale que no (false) guardamos el valor "sin hijos"
        */

       String hijos;

       if (switchHijos.isChecked()==true) {
           hijos=getString(R.string.con_hijos);
       }
       else {
           hijos=getString(R.string.sin_hijos);
       }
           //Construimos la notificacion.Usamos el metodo getString, solo que ahora, sustituye los
           //marcadores que hemos escrito en strings.xml por los valores que le pasamos entre parentesis
           //Recordamos que R.string.resumen nos dice que texto de strings.xml queremos usar.


       String resumen=getString(R.string.resumen,apellidos,nombre,mayoriaEdad,genero,estadoCivil,hijos);
           //Finalmente, mostramos el Toast.
           //Toast.makeText() lo que hace es crear un mensaje temporal. This hace referencia a esta actividad
           //resumen contiene el mensaje que hemos creado. Toast.LENGTH_LONG hace que el mensaje tenga una duracion larga
           //.show() hace que aparezca en si el mensaje temporal
       Toast.makeText(this,resumen,Toast.LENGTH_LONG).show();
       //Solo me salen 2 lineas si uso el toast. He estado mirando y es por limitaciones de android, no por el código
       //Si quisiera que se viera todo, tendria que usar otra herramiento que no fuese un toast.

    }

    private void limpiarDatos() {
        //Vaciamos los campos de texto:
        editNombre.setText("");
        editApellidos.setText("");
        editEdad.setText("");

        //Quitamos el genero que haya seleccionado el cliente
        grupoGenero.clearCheck();

        //Volvemos a la primera opcion que pusimos en el estado civil
        spinnerEstado.setSelection(0); //la primera opcion es el 0

        //Desactivamos el switch de los hijos (es decir, lo ponemos a falso )
        switchHijos.setChecked(false);

        //Con esto borramos los mensajes de error
        textoError.setText("");
    }

    /*---------------------------------------------------------------------------------------------------------------------
                    TRADUCCION A INGLES:
    * En mi aplicacion, tengo un spinner con los 2 idiomas, pero para que funcione,
    * tengo que hacer un par de cosas:
        * 1) Que al abrir la aplicacion, que esta compruebe que idioma se esta utilizando
        * y que se coloque el spinner en esa opcion
        * 2) y que cuando el cliente elija otro idioma, se cambien los textos de toda mi aplicacion
        * */

    private void configurarIdioma() {
                /*Vamos a empezar detectando cual es el idioma que esta usando la aplicacion en este momento. Para ello, usamos una serie de metodos concatenados que tuve que consultar con la IA:
                 *    -getResources() me permite acceder a los recursos de la aplicacion(en este caso textos)
                 *    -getConfiguration() sirve para consultar la configuracion actual de mi aplicacion.
                 *    -getLocales() me da la lista de idiomas que tenemos configurados (en este caso ingles y español)
                 *    -get(0) me permite escoger el primer idioma de esa lista que hemos obtenido.
                 *    -getLanguage() me da el codigo de ese idioma ("es" para el español y "en" para el ingles).
                 *En definitiva, todo esto para saber el idioma.
                 */
        String idiomaActual=getResources().getConfiguration().getLocales().get(0).getLanguage();


        /*Ahora vamos a colocar el spinnerIdiomas en el idioma correcto.
                 * Mi spinner tiene 2 opciones:
                 *    -Español (posicion 0)
                 *    -English (posicion 1).
                 * Entonces queremos que si abrimos la aplicacion por ejemplo en ingles, que el spinner
                 * muestre ENGLISH y no ESPAÑOL.
                 * */


        if (idiomaActual.equals("en")==true) {
            spinnerIdioma.setSelection(1);
        }
        else {
            spinnerIdioma.setSelection(0);
        }

                /*Con esto solo hacemos que el spinner muestre el idioma que ya utiliza mi aplicacion.
                * Todavia no he hecho que cambie el idioma cuando el cliente escoja otra opcion*/

                /*Tal y como dije antes, para hacer esta parte tuve que BUSCAR AYUDA EN LA IA dada la complejidad de conceptos.
                Trato de explicarlo de forma sencilla a continuacion:
                    Con la siguiente linea de codigo detectamos cuando el cliente elige otro idioma.
                    Es analogo a botonMostrar.setOnClickListener(this) que hemos usado antes
                    solo que setOnClickListener detecta cuando mi cliente pulsa el boton
                    y setOnItemSelectedListener detecta cuadno este selecciona una opcion de un spinner (en este caso, del spinnerIdiomas)
                    Con new AdapterView.onItemSelectedListener() creamos un objeto que se va a encargar de detectar esas selecciones del spinner que acabo de mencionar.
                */
        spinnerIdioma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
                    /*El metodo onItemSelected es el metodo que Android ejecuta cuando se selecciona una opcion
                    Tal y como indica la documentacion, los parametros significan:
                    - parent: el componente que contiene las opciones (en nuestro caso es el spinnerIdiomas)
                    -view: la vista que corresponde a la opcion seleccionada por el cliente.
                    -position: la posicion de la opcion seleccionada por el cliente.
                    -id: el id de la opcion seleccionada por el cliente.
                    Cuando el cliente selecciona una opcion del spinner, android le pasa automaticamente esos parámetros,
                    por lo que no debemos preocuparnos de ellos.
                    A nosotros solo nos importa el campo position, puesto que cuando el cliente
                    seleccione Español, la variable position vale 0, y so selecciona English
                    dicha variable valdrá 1.

                    Ponemos Override porque heredamos ese metodo y vamos a sobreescribirlo.
                    * */
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    /*
                    Por ende, usando lo que nos devuelva el campo position (el valor 0 o 1), podemos saber que idioma
                    ha elegido el cliente. Para ello, escribimos el siguiente codigo en el que vamos a guardar
                    el idioma escogido.
                    * */
                String idioma; //aqui vamos a guardar el idioma escogido

                if (position==0) {
                    idioma="es";
                }
                else {
                    idioma="en"; //esto ocurre cuando position =1 , que es el unico otro valor que puede tomar en esta aplicacion el campo position.
                }

                //Con esto todavia no he cambiado el idioma. Solo he guardado cual ha escogido el cliente.

                /*Ahora vamos a comprobar si tenemos que cambiar el idioma
                * idiomaActual es el idioma que teniamos en la aplicacion al abrir la pantalla
                * idioma es el idioma que acaba de escoger el cliente en el spinner:
                    * Si el idioma seleccionado es diferente del idioma actual es cuando entramos en el IF
                       y cambiamos el idioma mediante AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(idioma))
                    * Por otro lado, si ambas variables son iguales, no hacemos nada
                * Tras esto, android automaticamente, usa el string.xml correspondiente al idioma que hemos escogido y "traduce" la pagina
                * (no la traduce como tal, lo que hace es usar los textos del otro string.xml que añadimos en su momento). */

                if (idiomaActual.equals(idioma)==false) {
                    AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(idioma));
                }




            }

            /*Al usar AdapterView.OnItemSelectedListener tengo que implementar el metodo onItemSelected() (que es lo que hace cuando se selecciona una opcion en el spinner)
            * y SI O SI el metodo onNothingSelected() (cuando no se selecciona ninguna opcion en el spinner).
            * Si no implemento este ultimo metodo el programa me muestra un error. Android me sugirio esta solucion para quitar el error*/
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

    }


}