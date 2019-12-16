package engineer.davidauza;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.sql.*;


/**
 * Esta clase contiene el programa que actúa como servidor en el simulador de transacciones.
 */
public class Servidor {

    /**
     * Esta constante contiene el número de puerto que se utilizará en el programa.
     */
    private static final int PORT = 4444;

    /**
     * enum que contiene los tipos de transacciones que puede realizar el servidor.
     */
    private enum Transaciones {
        CREACION_CLIENTE,
        CONSULTAR_SALDO_CLIENTE,
        CONSIGNAR_CUENTA_CLIENTE,
        RETIRAR_CUENTA_CLIENTE
    }

    /**
     * Método main de la clase Servidor encargado de ejecutar el programa.
     */
    public static void main(String[] args) throws IOException {
        // Contiene el puerto en el que se escuchan las peticiones.
        ServerSocket socketServidor = null;
        try {
            socketServidor = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.println("No es posible escuchar en el puerto: " + PORT);
            System.exit(-1);
        }

        Socket socketCliente = null;
        BufferedReader entrada = null;
        PrintWriter salida = null;

        System.out.println("\nEscuchando: " + socketServidor);
        try {
            // Bloqueo hasta recibir la petición del cliente, abriendo un socket para este.
            socketCliente = socketServidor.accept();
            System.out.println("\nConexión aceptada: " + socketCliente);
            // Se establece el canal de entrada
            entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
            // Se establece el canal de salida
            salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketCliente.getOutputStream())),
                    true);

            String mensajeCliente = entrada.readLine();
            System.out.println("\nMensaje Cliente: " + mensajeCliente);
            salida.println(mensajeCliente);

            HashMap hashMensaje = stringAHashMap(mensajeCliente);

            String codigoTransaccion = (String) hashMensaje.get("idTransaccion");
            if (codigoTransaccion.equals(Transaciones.CREACION_CLIENTE.toString())) {
                insertarUsuarioBaseDeDatos(hashMensaje);
            } else if (codigoTransaccion.equals(Transaciones.CONSULTAR_SALDO_CLIENTE.toString())) {
                // TODO implementar entrega 3
            } else if (codigoTransaccion.equals(Transaciones.CONSIGNAR_CUENTA_CLIENTE.toString())) {
                // TODO implementar entrega 3
            } else if (codigoTransaccion.equals(Transaciones.RETIRAR_CUENTA_CLIENTE.toString())) {
                // TODO implementar entrega 3
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
        salida.close();
        entrada.close();
        socketCliente.close();
        socketServidor.close();
    }

    /**
     * Este método inserta un nuevo usuario en la base de datos.
     */
    private static void insertarUsuarioBaseDeDatos(HashMap<String, String> hashCliente) {
        String url = "jdbc:mysql://localhost:3306/mydb?autoReconnect=true&useSSL=false";
        String usuario = "root";
        String contrasena = "Diosmeama16my";
        try {
            Connection conexion = DriverManager.getConnection(url, usuario, contrasena);
            Statement sentencia = conexion.createStatement();
            String sql = "INSERT INTO mydb.cliente (nombres, apellidos, ciudad_id, direccion, email)" +
                    "VALUES(\'" + hashCliente.get("nombre") + "\', \'" + hashCliente.get("apellido")
                    + "\', " + hashCliente.get("idCiudad") + ", \'" + hashCliente.get("direccion")
                    + "\', \'" + hashCliente.get("email") + "\');";
            System.out.println(sql);
//            ResultSet setResultados = sentencia.executeQuery(sql);
            sentencia.executeUpdate(sql);
//            System.out.println(setResultados.toString());
//            setResultados.close();
            sentencia.close();
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Este método convierte un String recibido como mensaje del cliente en un HashMap.
     *
     * @param str es el String recibido como mensaje del cliente.
     * @return el HashMap producto de la transformación.
     */
    private static HashMap<String, String> stringAHashMap(String str) {
        HashMap<String, String> hash = new HashMap<String, String>();
        str = str.replaceAll("[{}\\s+]", "");
        String[] pares = str.split(",");
        for (int i = 0; i < pares.length; i++) {
            String par = pares[i];
            String[] keyValue = par.split("=");
            hash.put(keyValue[0], keyValue[1]);
        }
        return hash;
    }
}
