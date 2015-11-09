package net.grandpepper.caiena.grandpepper.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import net.grandpepper.caiena.grandpepper.beans.Author;
import net.grandpepper.caiena.grandpepper.beans.CallForPeppers;
import net.grandpepper.caiena.grandpepper.beans.Contact;
import net.grandpepper.caiena.grandpepper.beans.Event;
import net.grandpepper.caiena.grandpepper.beans.GrandPepper;
import net.grandpepper.caiena.grandpepper.beans.Location;
import net.grandpepper.caiena.grandpepper.beans.Talk;
import net.grandpepper.caiena.grandpepper.models.IEntidade;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    //nome do banco
    private static final String DATABASE_FILE_NAME = "grandpepper";

    // versao do banco - qualquer alteracao de banco incremente no valor
    private static final int DATABASE_VERSION = 1;

    private Map<Class, Dao<IEntidade, Object>> daos = new HashMap<>();

    public DatabaseHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {

            Log.i(DatabaseHelper.class.getName(), "onCreate Database");

            Log.i(DatabaseHelper.class.getName(), "Create table Info");
            TableUtils.createTable(connectionSource, GrandPepper.class);
            Log.i(DatabaseHelper.class.getName(), "Create table Event");
            TableUtils.createTable(connectionSource, Event.class);
            Log.i(DatabaseHelper.class.getName(), "Create table Author");
            TableUtils.createTable(connectionSource, Author.class);
            Log.i(DatabaseHelper.class.getName(), "Create table CallForPeppers");
            TableUtils.createTable(connectionSource, CallForPeppers.class);
            Log.i(DatabaseHelper.class.getName(), "Create table Contact");
            TableUtils.createTable(connectionSource, Contact.class);
            Log.i(DatabaseHelper.class.getName(), "Create table Location");
            TableUtils.createTable(connectionSource, Location.class);
            Log.i(DatabaseHelper.class.getName(), "Create table Talk");
            TableUtils.createTable(connectionSource, Talk.class);


        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade Database");

            Log.i(DatabaseHelper.class.getName(), "Update table Info");
            TableUtils.dropTable(connectionSource, GrandPepper.class, true);

            Log.i(DatabaseHelper.class.getName(), "Update table Event");
            TableUtils.dropTable(connectionSource, Event.class, true);

            Log.i(DatabaseHelper.class.getName(), "Update table Author");
            TableUtils.dropTable(connectionSource, Author.class, true);

            Log.i(DatabaseHelper.class.getName(), "Update table CallForPeppers");
            TableUtils.dropTable(connectionSource, CallForPeppers.class, true);

            Log.i(DatabaseHelper.class.getName(), "Update table Contact");
            TableUtils.dropTable(connectionSource, Contact.class, true);

            Log.i(DatabaseHelper.class.getName(), "Update table Location");
            TableUtils.dropTable(connectionSource, Location.class, true);

            Log.i(DatabaseHelper.class.getName(), "Update table Talk");
            TableUtils.dropTable(connectionSource, Talk.class, true);

            onCreate(db, connectionSource);
        } catch (android.database.SQLException | SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "exception during onUpgrade", e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    public <T> Dao<T, Object> getDAO(Class<T> entidadeClass) {
        Dao<T, Object> dao;
        if (daos.get(entidadeClass) == null) {
            try {
                dao = getDao(entidadeClass);
            } catch (SQLException e) {
                Log.e(DatabaseHelper.class.getName(), "exception during getDAO", e);
                throw new RuntimeException(e);
            }
            daos.put(entidadeClass, (Dao<IEntidade, Object>) dao);
        }
        return (Dao<T, Object>) daos.get(entidadeClass);
    }

}
