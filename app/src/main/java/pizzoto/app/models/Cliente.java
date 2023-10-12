package pizzoto.app.models;

public class Cliente {
    private int id;
    private String nombre;
    private String correo;
    private String contrasena;
    private String apellidos;

    // Constructores
    public Cliente() {
    }

    public Cliente(int id,String nombre, String correo, String contrasena, String apellidos) {
        this.id=id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.apellidos = apellidos;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }


}

