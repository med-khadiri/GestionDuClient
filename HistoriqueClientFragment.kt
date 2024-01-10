package ma.istakhemisset.gestionclient

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import android.widget.TextView


class HistoriqueClientFragment : Fragment() {
    private lateinit var livraisonDao: LivraisonDao
    private lateinit var textViewQuantite:TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        this.livraisonDao = LivraisonDao(requireContext())
        val view = inflater.inflate(R.layout.fragment_historique_client, container, false)
        textViewQuantite = view.findViewById(R.id.textViewTotalQuante!!)
        view.findViewById<ListView>(R.id.listViewLivraison).adapter = SimpleCursorAdapter(
            requireContext(),
            R.layout.mise_en_forme_livraison, this.livraisonDao.getLivraisonClient(Comunicator.getElement(0)),
            arrayOf(
                ClientDao.COLUMN_ID,
                ClientDao.COLUMN_NOM,
                ClientDao.COLUMN_PRENOM,
                ClientDao.COLUMN_TEL,
                LivraisonDao.COLUMN_QUANTITE,
                LivraisonDao.COLUMN_DATE
            ),
            arrayOf(
                R.id.textViewIdLivraison,
                R.id.textViewNomLivraison,
                R.id.textViewPrenomLivraison,
                R.id.textViewTelLivraison,
                R.id.textViewQuantiteLivraison,
                R.id.textViewDateLivraison
            ).toIntArray()
        )
        var total:Int = 0
        val clientliv=this.livraisonDao.getQuantitetotal(Comunicator.getElement(0))
        Log.e("tag",  "la livraison est  ${clientliv.getColumnIndex(LivraisonDao.COLUMN_QUANTITE)}")
        while (clientliv.moveToNext())


        {
            Log.e("tag",  "le paiement est  ${clientliv.getInt(4)}")
            total += clientliv.getInt(4)


        }
        textViewQuantite.setText("Quantité Total:"+total)
        return view
    }

}