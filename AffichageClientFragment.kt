package ma.istakhemisset.gestionclient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.SimpleCursorAdapter


class AffichageClientFragment : Fragment() {
    private lateinit var clientDao: ClientDao


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        this.clientDao = ClientDao(requireContext())
        val view = inflater.inflate(R.layout.fragment_affichage_client, container, false)
        view.findViewById<ListView>(R.id.listViewClient).adapter = SimpleCursorAdapter(
            requireContext(),
            R.layout.mise_en_forme_client, this.clientDao.getAll(),
            arrayOf(
                ClientDao.COLUMN_ID,
                ClientDao.COLUMN_NOM,
                ClientDao.COLUMN_PRENOM,
                ClientDao.COLUMN_EMAIL,
                ClientDao.COLUMN_TEL,
                ClientDao.COLUMN_MONTANT,
                ClientDao.COLUMN_DATE,
                ClientDao.COLUMN_PRIX
            ),
            arrayOf(
                R.id.textViewId,
                R.id.textViewNom,
                R.id.textViewPrenom,
                R.id.textViewTel,
                R.id.textViewEmail,
                R.id.textViewMontant,
                R.id.textViewDate,
                R.id.textViewPrix
            ).toIntArray()
        )
        return view

    }



}