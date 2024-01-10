package ma.istakhemisset.gestionclient

import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import android.widget.TextView

class HistoriquePaiement : Fragment() {

    private lateinit var paiementDao: PaiementDao
    private lateinit var textViewTotal:TextView




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        this.paiementDao = PaiementDao(requireContext())
        val view = inflater.inflate(R.layout.fragment_historique_paiement, container, false)
        textViewTotal=view.findViewById(R.id.textViewTotal!!)
        

        view.findViewById<ListView>(R.id.listViewPaiement).adapter = SimpleCursorAdapter(
            requireContext(),
            R.layout.mise_en_forme_paiement, this.paiementDao.getPaimentClient(Comunicator.getElement(0)),
            arrayOf(
                ClientDao.COLUMN_ID,
                ClientDao.COLUMN_NOM,
                ClientDao.COLUMN_PRENOM,
                ClientDao.COLUMN_TEL,
                PaiementDao.COLUMN_MONTANT,
                PaiementDao.COLUMN_DATE
            ),
            arrayOf(
                R.id.textViewIdPaiement,
                R.id.textViewNomPaiement,
                R.id.textViewPrenomPaiement,
                R.id.textViewTelPaiement,
                R.id.textViewMontantPaiement,
                R.id.textViewDatePaiement
            ).toIntArray()
        )
        var total:Double = 0.0
        val clientpaim=this.paiementDao.getTotal(Comunicator.getElement(0))
        while (clientpaim.moveToNext())
        {   //Log.e("tag",  "le paiement est  ${clientpaim.getDouble(4)}")
            total += clientpaim.getDouble(4)

        }
        textViewTotal.setText("Total:"+total)
        return view

    }
}