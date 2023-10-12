package pizzoto.app.models;

import java.util.ArrayList;
import java.util.List;

public class Producto {
    private int id ;
    private String nombre  ;
    private String tamano ;
    private int tiempo_preparacion ;
    private double precio ;
    private String descripcion;
    private List<Ingrediente> listaIngrediente = new ArrayList<>();

    public Producto(int id, String nombre, String tamano, int tiempo_preparacion, double precio, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.tamano = tamano;
        this.tiempo_preparacion = tiempo_preparacion;
        this.precio = precio;
        this.descripcion = descripcion;
    }

    public Producto() {
    }

    public Producto(int id) {
    }

    public List<Ingrediente> getListaIngrediente() {
        return listaIngrediente;
    }

    public void setListaIngrediente(List<Ingrediente> listaIngrediente) {
        this.listaIngrediente = listaIngrediente;
    }

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

    public String getTamano() {
        return tamano;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
    }

    public int getTiempo_preparacion() {
        return tiempo_preparacion;
    }

    public void setTiempo_preparacion(int tiempo_preparacion) {
        this.tiempo_preparacion = tiempo_preparacion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
