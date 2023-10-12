package pizzoto.app.Repository.Local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import pizzoto.app.adapters.ProductoAdapter;
import pizzoto.app.models.PedidoDescripcion;
import pizzoto.app.models.Producto;

public class CarritoLocalRepository extends SQLiteOpenHelper {
    private static final String databaseName = "Pizzorto.db";
    private static final int dbVersion = 1;

    private static final String tablaProducto = "Producto";
    private static final String columnId = "id";
    private static final String columnCantidad = "cantidad";

    public CarritoLocalRepository(Context context) {
        super(context, databaseName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSQL = "CREATE TABLE Producto ( id INTEGER PRIMARY KEY not null, " +
                columnCantidad + " INTEGER)";
        db.execSQL(createTableSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tablaProducto);
        onCreate(db);
    }

    public void insertProductos(PedidoDescripcion pedidoDescripcion) {
        //declaraciones
        SQLiteDatabase db = this.getWritableDatabase();
        Producto producto = pedidoDescripcion.getProducto();
        int idProducto = producto.getId();
        int cantidadPedido = pedidoDescripcion.getCantidad();

        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT " + columnCantidad + " FROM " + tablaProducto + " WHERE " + columnId + " = " + idProducto, null);
            //si existe se suma
            if (cursor.moveToFirst()) {
                int cantidadExistente = cursor.getInt(0);
                cantidadPedido += cantidadExistente;
                ContentValues values = new ContentValues();
                values.put(columnCantidad, cantidadPedido);
                System.out.println(cantidadExistente);
                System.out.println(idProducto);

                db.update(tablaProducto, values, columnId + " = " + idProducto, null);

            } else {
                //se no se crea
                ContentValues values = new ContentValues();
                values.put(columnId, idProducto);
                values.put(columnCantidad, cantidadPedido);
                System.out.println(cantidadPedido);
                System.out.println(idProducto);
                db.insert(tablaProducto, null, values);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

    public List<PedidoDescripcion> getPedidoDescriptions() {
        List<PedidoDescripcion> pedidoDescriptions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT " + columnId + ", " + columnCantidad + " FROM " + tablaProducto, null);

            if (cursor.moveToFirst()) {
                do {
                    Producto producto= new Producto();
                    producto.setId(cursor.getInt(0));
                    int cantidad = cursor.getInt(1);
                    PedidoDescripcion pedidoDescription = new PedidoDescripcion(producto, cantidad);
                    pedidoDescriptions.add(pedidoDescription);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return pedidoDescriptions;
    }

    public void eliminarRegistroPorId(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            int rowsDeleted = db.delete(tablaProducto, columnId + " = ?", new String[]{String.valueOf(id)});

            if (rowsDeleted > 0) {
                System.out.println("Registro eliminado con éxito. ID: " + id);
            } else {
                System.out.println("No se encontró ningún registro con el ID: " + id);

            }
        } catch (Exception e) {
            System.out.println("Error al eliminar el registro con ID " + id + ": " + e.getMessage());

        } finally {
            db.close();
        }
    }

    public void disminuirCantidadProducto(int idProducto) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT " + columnCantidad + " FROM " + tablaProducto + " WHERE " + columnId + " = " + idProducto, null);

            if (cursor.moveToFirst()) {
                int cantidadExistente = cursor.getInt(0);
                if (cantidadExistente > 1) {
                    cantidadExistente--;
                    ContentValues values = new ContentValues();
                    values.put(columnCantidad, cantidadExistente);
                    db.update(tablaProducto, values, columnId + " = " + idProducto, null);
                } else {
                    eliminarRegistroPorId(idProducto);
                }
                System.out.println(cantidadExistente );
                System.out.println(idProducto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }


    }
    public void aumentarCantidadProducto(int idProducto) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT " + columnCantidad + " FROM " + tablaProducto + " WHERE " + columnId + " = " + idProducto, null);

            if (cursor.moveToFirst()) {
                int cantidadExistente = cursor.getInt(0);
                if (cantidadExistente > 1) {
                    cantidadExistente++;
                    ContentValues values = new ContentValues();
                    values.put(columnCantidad, cantidadExistente);
                    db.update(tablaProducto, values, columnId + " = " + idProducto, null);
                } else {
                    eliminarRegistroPorId(idProducto);
                }
                System.out.println(cantidadExistente );
                System.out.println(idProducto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }






    public void borrarRegistros() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tablaProducto, null, null);
        db.close();
    }

}
