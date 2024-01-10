package ma.istakhemisset.gestionclient

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DataBaseHelper(context:Context,name: String?,factory: SQLiteDatabase.CursorFactory?, version: Int,)
    : SQLiteOpenHelper(context, name, factory, version)
{

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(ClientDao.CREATE_TABLE)
        db?.execSQL(PaiementDao.CREATE_TABLE)
        db?.execSQL(LivraisonDao.CREATE_TABLE)
        //Log.d("tag","base donnée creer")


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}