package engineer.davidauza.modelos;

import java.util.List;

/**
 * Esta clase representa a un cliente.
 */
public class ClienteModelo {

    /**
     * Alamacena el ID del cliente.
     */
    private int mID;

    /**
     * Almacena los nombres del cliente.
     */
    private String mNombres;

    /**
     * Almacena los apellidos del cliente.
     */
    private String mApellidos;

    /**
     * Almacena el ID de la ciudad a la que pertenece el cliente.
     */
    private int mIDCiudad;

    /**
     * Almacena la dirección del cliente.
     */
    private String mDireccion;

    /**
     * Almacena el correo electrónico del cliente.
     */
    private String mEmail;

    /**
     * Almacena el teléfono o los teléfonos del cliente.
     */
    private List<String> mTelefonos;

    /**
     * Almacena la o las cuentas que posee el cliente.
     */
    private List<String> mCuentas;

    /**
     * Este método retorna el ID del cliente.
     */
    public int getID() {
        return mID;
    }

    /**
     * Este método fija el ID del cliente.
     */
    public void setID(int mID) {
        this.mID = mID;
    }

    /**
     * Este método retorna los nombre del cliente.
     */
    public String getNombres() {
        return mNombres;
    }

    /**
     * Este método establece los nombres del cliente.
     */
    public void setNombres(String mNombres) {
        this.mNombres = mNombres;
    }

    /**
     * Este método retorna los apellidos del cliente.
     */
    public String getApellidos() {
        return mApellidos;
    }

    /**
     * Este método establece los apellidos del cliente.
     */
    public void setApellidos(String mApellidos) {
        this.mApellidos = mApellidos;
    }

    /**
     * Este método retorna el ID de la ciudad en la que vive el cliente.
     */
    public int getIDCiudad() {
        return mIDCiudad;
    }

    /**
     * Este método establece el ID de la ciudad en la que vive el cliente.
     */
    public void setIDCiudad(int mIDCiudad) {
        this.mIDCiudad = mIDCiudad;
    }

    /**
     * Este método retorna la dirección en la que vive el cliente.
     */
    public String getDireccion() {
        return mDireccion;
    }

    /**
     * Este método establece la dirección en la que vive el cliente.
     */
    public void setDireccion(String mDireccion) {
        this.mDireccion = mDireccion;
    }

    /**
     * Este método retorna el email del cliente.
     */
    public String getEmail() {
        return mEmail;
    }

    /**
     * Este método establece el email del cliente.
     */
    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    /**
     * Este método retorna los teléfonos del cliente.
     */
    public List<String> getTelefonos() {
        return mTelefonos;
    }

    /**
     * Este método establece los teléfonos del cliente a través de una lista.
     */
    public void setTelefonos(List<String> mTelefonos) {
        this.mTelefonos = mTelefonos;
    }

    /**
     * Este método retorna las cuentas del cliente.
     */
    public List<String> getCuentas() {
        return mCuentas;
    }

    /**
     * Este método establece las cuentas del cliente.
     */
    public void setCuentas(List<String> mCuentas) {
        this.mCuentas = mCuentas;
    }
}
