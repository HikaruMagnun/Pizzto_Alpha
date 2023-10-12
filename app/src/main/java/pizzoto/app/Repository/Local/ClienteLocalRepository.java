package pizzoto.app.Repository.Local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import pizzoto.app.models.Cliente;

public class ClienteLocalRepository extends SQLiteOpenHelper {
    private static final String databaseName = "Pizzorto.db";
    private static final int dbVersion = 1;

    private static final String tablaCliente = "Cliente";

    public ClienteLocalRepository(Context context) {
        super(context, databaseName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableClienteSQL = "CREATE TABLE " + tablaCliente + " (" +
                "nombre TEXT PRIMARY KEY NOT NULL, " +
                "apellido TEXT)";

        db.execSQL(createTableClienteSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tablaCliente);
        onCreate(db);
    }

    public void insertarCliente(Cliente cliente) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", cliente.getNombre());
        values.put("apellido", cliente.getApellidos());
        db.insert(tablaCliente, null, values);
        db.close();
    }

    public void eliminarTodosLosClientes() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tablaCliente, null, null);
        db.close();
    }

    public Cliente obtenerPrimerCliente() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cliente cliente = null;

        String[] columns = {"nombre", "apellido"};
        Cursor cursor = db.query(tablaCliente, columns, null, null, null, null, null, "1");

        if (cursor.moveToFirst()) {
            String nombre = cursor.getString(0);
            String apellido = cursor.getString(1);
            cliente = new Cliente();
            cliente.setNombre(nombre);
            cliente.setApellidos(apellido);
        }

        cursor.close();
        db.close();

        return cliente;
    }




}

