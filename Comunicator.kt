package ma.istakhemisset.gestionclient

import java.text.FieldPosition

class Comunicator() {
    companion object {
        var listid = mutableListOf<Int>()
        fun ajouter(id:Int){
            this.listid.add(id)


        }
        fun clearList()
        {
            this.listid.clear()
        }
        fun getElement(position:Int): Int = this.listid[position]




    }
}