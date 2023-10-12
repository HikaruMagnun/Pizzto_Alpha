package pizzoto.app.models;

public class Empleado {
    private int id;
    private String mombre;
    private String apellido;

    public Empleado(int id, String mombre, String apellido) {
        this.id = id;
        this.mombre = mombre;
        this.apellido = apellido;
    }

    public Empleado() {
        id = 1;
        mombre = "Empleado";
        apellido = "Virtual";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMombre() {
        return mombre;
    }

    public void setMombre(String mombre) {
        this.mombre = mombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
