package engineer.davidauza;

import engineer.davidauza.modelos.ClienteModelo;

import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
 * Esta clase contiene el programa que actúa como cliente en el simulador de transacciones.
 */
public class Cliente {

    /**
     * Esta constante contiene el número de puerto que se utilizará en el programa.
     */
    private static final int PORT = 4444;

    /**
     * Esta constante almacena la expresión regular para validar el correo electrónico del
     * usuario.
     */
    private static final String REGEX_EMAIL =
            "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    /**
     * Esta constante almacena la expresión regular para validar los teléfonos del usuario, que
     * son números entre 7 y 10 dígitos.
     */
    private static final String REGEX_TELEFONO = "\\d{7,10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";

    /**
     * Esta constante almacena la expresión regular para evaluar las cuentas, que son un número de
     * 10 dígitos cada una.
     */
    private static final String REGEX_CUENTA = "\\d{10}";

    /**
     * enum que contiene los tipos de transacciones que puede realizar el cliente.
     */
    private enum Transaciones {
        CREACION_CLIENTE,
        CONSULTAR_SALDO_CLIENTE,
        CONSIGNAR_CUENTA_CLIENTE,
        RETIRAR_CUENTA_CLIENTE
    }

    /**
     * Alamacena el cliente que se creará si se selecciona la opción uno en el menú principal.
     */
    private static ClienteModelo mNuevoCliente = null;

    /**
     * HashMap que contiene los datos del nuevo cliente creado.
     */
    private static HashMap<String, String> mNuevoClienteHash = null;

    /**
     * Socket para realizar la conexión con el servidor.
     */
    private static Socket mSocketCliente = null;

    /**
     * BufferedReader necesario para realizar la conexión con el servidor.
     */
    private static BufferedReader mEntrada = null;

    /**
     * PrintWriter necesario para realizar la conexión con el servidor.
     */
    private static PrintWriter mSalida = null;

    /**
     * Método main de la clase Cliente encargado de ejecutar el programa.
     */
    public static void main(String[] args) {
        System.out.println("\n¡Bienvenido al Banco XYZ!" +
                "\n\nPresione:" +
                "\n\n1 Para crear un cliente" +
                "\n2 Para consultar el saldo de un cliente" +
                "\n3 Para consignar un monto de dinero en la cuenta de un cliente" +
                "\n4 Para retirar dinero de la cuenta de un cliente" +
                "\n5 Para terminar el programa");

        Scanner sc = new Scanner(System.in);

        byte seleccionUsuario = -1;
        boolean entradaValida = false;

        while (!entradaValida) {
            System.out.println("\nIngrese su selección y la tecla enter/return:");
            if (sc.hasNextByte()) {
                seleccionUsuario = sc.nextByte();
            } else {
                sc.nextLine();
            }
            switch (seleccionUsuario) {
                case 1:
                    mNuevoCliente = crearCliente();
                    mNuevoClienteHash = crearHashCliente();
                    conectarseAlServidor();
                    enviarMensajeAlServidor(mNuevoClienteHash.toString());
                    liberarRecursos();
                    entradaValida = true;
                    break;
                case 2:
                case 3:
                case 4:
                    System.out.println("Opción no disponible en el momento. Se implementará para la entrega 3.");
                    // TODO pendiente para la entrega 3.
                    break;
                case 5:
                    System.out.println("\nVuelva pronto.\n\nHasta luego.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("\nEntrada no válida.");
                    break;
            }
        }
        sc.close();
    }

    /**
     * Este método se encarga de realizar la conexión con el servidor.
     */
    private static void conectarseAlServidor() {
        // Se crea el socket del lado del cliente, enlazado con el servidor en la misma máquina
        // del cliente, escuchando en el puerto 4444
        try {
            mSocketCliente = new Socket("localhost", PORT);
            // Se obtiene el canal de entrada
            mEntrada = new BufferedReader(new InputStreamReader(mSocketCliente.getInputStream()));
            // Se obtiene el canal de salida
            mSalida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(mSocketCliente.getOutputStream())),
                    true);
        } catch (IOException e) {
            System.err.println("No se pudo establecer canales de entrada y salida para la conexión");
            System.exit(-1);
        }
    }

    /**
     * Este método envía el mensaje al servidor según las elecciones del usuario.
     */
    private static void enviarMensajeAlServidor(String mensaje) {
        try {
            // Se envía al servidor
            mSalida.println(mensaje);
            // Se envía a la entrada estándar la respuesta del servidor
            mensaje = mEntrada.readLine();
            System.out.println("Respuesta: El servidor recibió: " + mensaje);
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    /**
     * Este método libera los recursos utilizados para conectarse al socket servidor.
     */
    private static void liberarRecursos() {
        // Se liberan los recursos utilizados
        mSalida.close();
        try {
            mEntrada.close();
            mSocketCliente.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Este método se encarga de crear un nuevo cliente.
     */
    private static ClienteModelo crearCliente() {
        Scanner sc = new Scanner(System.in);
        ClienteModelo nuevoCliente = new ClienteModelo();
        System.out.println("\nSe procederá a crear un nuevo cliente en la base de datos. " +
                "Por favor ingrese los datos solicitados:\n");
        System.out.print("Nombres: ");
        nuevoCliente.setNombres(sc.nextLine());
        System.out.print("Apellidos: ");
        nuevoCliente.setApellidos(sc.nextLine());
        System.out.print("Ingrese el ID Ciudad de la ciudad correspondiente:" +
                "\n1 Bogotá " +
                "\n2 Medellín " +
                "\n3 Barranquilla " +
                "\n4 Cali " +
                "\n5 Cartagena" +
                "\n6 Bucaramanga" +
                "\n7 Santa Marta" +
                "\n8 Cúcuta\n");
        byte ciudadSeleccionada = -1;
        String entradaNoValida = "\nEntrada no válida.\n\nIngrese un número de ciudad válido:";
        while (ciudadSeleccionada < 1 || ciudadSeleccionada > 8) {
            if (sc.hasNextByte()) {
                byte valorTemporal = sc.nextByte();
                if (valorTemporal >= 1 && valorTemporal <= 8) {
                    ciudadSeleccionada = valorTemporal;
                } else {
                    System.out.println(entradaNoValida);
                }
            } else {
                System.out.println(entradaNoValida);
            }
            sc.nextLine();
        }
        nuevoCliente.setIDCiudad(ciudadSeleccionada);
        System.out.print("Dirección: ");
        nuevoCliente.setDireccion(sc.nextLine());
        System.out.print("Email: ");
        String emailIngresado = sc.nextLine();
        while (!emailIngresado.matches(REGEX_EMAIL)) {
            System.out.print("Ingrese un email válido: ");
            emailIngresado = sc.nextLine();
        }
        nuevoCliente.setEmail(emailIngresado);
        System.out.println("Ingrese los teléfonos del usuario separados por comas:");
        List<String> listaTelefonos = crearLista(REGEX_TELEFONO);
        nuevoCliente.setTelefonos(listaTelefonos);
        System.out.println("Ingrese los números de cuenta del cliente separados por comas: ");
        List<String> listaCuentas = crearLista(REGEX_CUENTA);
        nuevoCliente.setCuentas(listaCuentas);
        sc.close();
        return nuevoCliente;
    }

    /**
     * Este método crea una lista sin espacios de acuerdo a los datos que suministra el usuario
     * y de acuerdo con la expresión regular pasada como argumento.
     *
     * @param regexAEvaluar es la expresión regular con la que se validará si es válido el
     *                      input del usuario.
     * @return Se retorna la lista con los datos ingresados por el usuario, evaluados por la
     * expresión regular.
     */
    private static List<String> crearLista(String regexAEvaluar) {
        Scanner sc = new Scanner(System.in);
        String datosIngresados = "";
        int entradasValidas = 0;
        List<String> lista = new ArrayList<>();
        do {
            datosIngresados = sc.nextLine();
            datosIngresados = datosIngresados.replaceAll("\\s+", "");
            lista = Arrays.asList(datosIngresados.split("\\s*,\\s*"));
            entradasValidas = 0;
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).matches(regexAEvaluar)) {
                    entradasValidas++;
                }
            }
            if (entradasValidas == lista.size()) {
                break;
            } else {
                if (regexAEvaluar.equals(REGEX_TELEFONO)) {
                    System.out.println("Números de teléfono no válidos. Ingrese números entre 7 y 10 dígitos:");
                } else {
                    System.out.println("Números de cuenta no válidos. Ingrese números de 10 dígitos:");
                }
            }
        } while (entradasValidas != lista.size());
        return lista;
    }

    /**
     * Este método crea el HashMap que contiene los datos del cliente creado.
     */
    private static HashMap<String, String> crearHashCliente() {
        HashMap<String, String> nuevoClienteHash = new HashMap<>();
        nuevoClienteHash.put("idTransaccion", Transaciones.CREACION_CLIENTE.toString());
        nuevoClienteHash.put("nombre", mNuevoCliente.getNombres());
        nuevoClienteHash.put("apellido", mNuevoCliente.getApellidos());
        nuevoClienteHash.put("idCiudad", Integer.toString(mNuevoCliente.getIDCiudad()));
        nuevoClienteHash.put("direccion", mNuevoCliente.getDireccion());
        nuevoClienteHash.put("email", mNuevoCliente.getEmail());
        nuevoClienteHash.put("telefonos", mNuevoCliente.getTelefonos().toString());
        nuevoClienteHash.put("cuentas", mNuevoCliente.getCuentas().toString());
        return nuevoClienteHash;
    }
}
