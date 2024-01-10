package ma.istakhemisset.gestionclient

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log

class PaiementDao(context: Context):DAODatabase(context)
{


    companion object
    {
        const val TABLE_NAME="paiement"
        const val COLUMN_ID="_id"
        const val COLUMN_MONTANT="montant"
        const val COLUMN_DATE="date"
        const val COLUMN_ID_CLIENT="_idClient"

        const val CREATE_TABLE=
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_DATE+" TEXT,"+
                    COLUMN_MONTANT+" REAL,"+
                    COLUMN_ID_CLIENT+" INTEGER REFERENCES "+ClientDao.TABLE_NAME+"("+ClientDao.COLUMN_ID+"));"
        const val DROP_TABLE="DROP TABLE " + TABLE_NAME+ " IF EXISTS ;"

    }
    fun insert(paiement: Paiement)
    {
        val contentValues = ContentValues()
        contentValues.put(COLUMN_DATE,paiement.date)
        contentValues.put(COLUMN_ID_CLIENT,paiement.id_client)
        contentValues.put(COLUMN_MONTANT,paiement.montant)

        this.db.insert(TABLE_NAME,null,contentValues)


    }
    fun getTotal(id:Int):Cursor
    {
        return this.db.rawQuery("SELECT ${ClientDao.TABLE_NAME}.${ClientDao.COLUMN_ID}," +
                "${ClientDao.COLUMN_NOM},${ClientDao.COLUMN_PRENOM},${ClientDao.COLUMN_TEL}," +
                "$TABLE_NAME.$COLUMN_MONTANT,$TABLE_NAME.$COLUMN_DATE FROM ${ClientDao.TABLE_NAME} INNER JOIN" +
                " $TABLE_NAME ON ${ClientDao.TABLE_NAME}.${ClientDao.COLUMN_ID}=$TABLE_NAME.$COLUMN_ID_CLIENT WHERE ${ClientDao.TABLE_NAME}.${ClientDao.COLUMN_ID} =?  ; ",
            arrayOf(id.toString()))
    }
    fun getPaimentClient(id:Int):Cursor
    {

         val cursor = this.db.rawQuery("SELECT ${ClientDao.TABLE_NAME}.${ClientDao.COLUMN_ID}," +
                 "${ClientDao.COLUMN_NOM},${ClientDao.COLUMN_PRENOM},${ClientDao.COLUMN_TEL}," +
                 "$TABLE_NAME.$COLUMN_MONTANT,$TABLE_NAME.$COLUMN_DATE FROM ${ClientDao.TABLE_NAME} INNER JOIN" +
                 " $TABLE_NAME ON ${ClientDao.TABLE_NAME}.${ClientDao.COLUMN_ID}=$TABLE_NAME.$COLUMN_ID_CLIENT WHERE ${ClientDao.TABLE_NAME}.${ClientDao.COLUMN_ID} =?  ; ",
             arrayOf(id.toString()))

         //Log.e("Tag",cursor.count.toString())
        return cursor
    }
    fun getAll():Cursor
    {
        return this.db.query(TABLE_NAME,null,null,null,null,null,null)
    }
    fun removeById(id:Int)
    {
        this.db.execSQL("DELETE FROM $TABLE_NAME WHERE $COLUMN_ID_CLIENT=?", arrayOf(id.toString()))
    }
    fun removeByAmount(montant:Double)
    {
        this.db.execSQL("DELETE FROM $TABLE_NAME WHERE EXISTS (SELECT ${ClientDao.TABLE_NAME}.${ClientDao.COLUMN_ID},${ClientDao.TABLE_NAME}.${ClientDao.COLUMN_MONTANT} FROM ${ClientDao.TABLE_NAME} WHERE ${ClientDao.TABLE_NAME}.${ClientDao.COLUMN_ID} = $COLUMN_ID_CLIENT AND ${ClientDao.TABLE_NAME}.${ClientDao.COLUMN_MONTANT} =?)",
            arrayOf(montant.toString())
        )

    }









}