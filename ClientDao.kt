package ma.istakhemisset.gestionclient

import android.content.ContentValues
import android.content.Context
import android.database.Cursor

class ClientDao(context:Context) :DAODatabase(context )
{


    companion object
    {
        const val TABLE_NAME="client"
        const val COLUMN_ID="_id"
        const val COLUMN_NOM="nom"
        const val COLUMN_PRENOM="prenom"
        const val COLUMN_TEL="telephone"
        const val COLUMN_EMAIL="email"
        const val COLUMN_MONTANT="montant"
        const val COLUMN_DATE="date"
        const val COLUMN_PRIX="prix"
        const val CREATE_TABLE=
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NOM + " TEXT,"  +
                    COLUMN_PRENOM + " TEXT," + COLUMN_EMAIL + " TEXT,"+ COLUMN_TEL + " TEXT,"+ COLUMN_MONTANT+" REAL DEFAULT 0.0,"+
                    COLUMN_DATE+" TEXT,"+
                    COLUMN_PRIX + " REAL);"
        const val DROP_TABLE="DROP TABLE " + TABLE_NAME+ " IF EXISTS ;"

    }
    fun insert(client:Client)
    {
        val contentValues =ContentValues()
        contentValues.put(COLUMN_NOM,client.nom)
        contentValues.put(COLUMN_PRENOM,client.prenom)
        contentValues.put(COLUMN_EMAIL,client.email)
        contentValues.put(COLUMN_TEL,client.tel)
        contentValues.put(COLUMN_MONTANT,client.montant)
        contentValues.put(COLUMN_DATE,client.date)
        contentValues.put(COLUMN_PRIX,client.prix)
        this.db.insert(TABLE_NAME,null,contentValues)


    }
    fun update(idCompa:Int,nom:String,prenom:String,email:String,tel:String,prix:Double)
    {
        val contentValues=ContentValues()
        contentValues.put(COLUMN_NOM,nom)
        contentValues.put(COLUMN_PRENOM,prenom)
        contentValues.put(COLUMN_EMAIL,email)
        contentValues.put(COLUMN_TEL,tel)
        contentValues.put(COLUMN_PRIX,prix)


        this.db.update(TABLE_NAME,contentValues, COLUMN_ID+" = ? ", arrayOf(idCompa.toString()))

    }
    fun updateMontantClient(id:Int,montant: Double)
    {
        val contentValues=ContentValues()
        contentValues.put(COLUMN_MONTANT,montant)
        this.db.update(TABLE_NAME,contentValues, COLUMN_ID+" =? ", arrayOf(id.toString()))

    }
    fun getAll():Cursor
    {
        return this.db.query(TABLE_NAME,null,null,null,null,null,null)
        //return this.db.rawQuery("SELECT $COLUMN_ID,$COLUMN_NOM,${PaiementDao.COLUMN_ID},${PaiementDao.COLUMN_DATE},${PaiementDao.COLUMN_MONTANT} FROM $TABLE_NAME INNER JOIN ${PaiementDao.TABLE_NAME} ON $COLUMN_ID = ${PaiementDao.COLUMN_ID_CLIENT} WHERE $COLUMN_ID =?;",
            //arrayOf(PaiementDao.COLUMN_ID_CLIENT))

    }
    fun getById(id:Int):Cursor
    {
        return this.db.query(TABLE_NAME,null, "$COLUMN_ID =?", arrayOf(id.toString()),null,null,null)
    }
    fun removeById(id:Int)
    {
        this.db.execSQL("DELETE FROM $TABLE_NAME WHERE $COLUMN_ID =?", arrayOf(id.toString()))
    }
    
    fun getOnlyPayed():Cursor
    {
        return this.db.query(TABLE_NAME,null,"$COLUMN_MONTANT=?", arrayOf("0"),null,null,null,null)
    }

}