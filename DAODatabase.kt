package ma.istakhemisset.gestionclient

import android.content.Context
import android.database.sqlite.SQLiteDatabase

abstract class  DAODatabase(context:Context)
{
    protected lateinit  var  db :SQLiteDatabase
    protected lateinit var helper:DataBaseHelper


    companion object{
        protected const val DATABASE_NAME="gestionClient.db"
        protected const val DATABASE_VERSION=1
    }

    init {
        this.helper= DataBaseHelper(context, DATABASE_NAME,null, DATABASE_VERSION)
        this.db=this.helper.writableDatabase


    }




}