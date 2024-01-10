package ma.istakhemisset.gestionclient

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import android.widget.Toast


class AffichageClientPayer : Fragment() {
    private lateinit var clientDao: ClientDao
    private lateinit var btnReduire:Button
    private lateinit var paiementDao: PaiementDao
    private lateinit var livraisonDao: LivraisonDao


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        this.clientDao = ClientDao(requireContext())
        this.paiementDao = PaiementDao(requireContext())
        this.livraisonDao = LivraisonDao(requireContext())
        val view = inflater.inflate(R.layout.fragment_affichage_client_payer, container, false)
        btnReduire = view.findViewById(R.id.btnReduireLaBase!!)
        view.findViewById<ListView>(R.id.listViewClientPayer).adapter = SimpleCursorAdapter(
            requireContext(),
            R.layout.mise_en_forme_client_payer, this.clientDao.getOnlyPayed(),
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
                R.id.textViewIdPayé,
                R.id.textViewNomPayé,
                R.id.textViewPrenomPayé,
                R.id.textViewTelPayé,
                R.id.textViewEmailPayé,
                R.id.textViewMontantPayé,
                R.id.textViewDatePayé,
                R.id.textViewPrixPayé
            ).toIntArray()
        )
        btnReduire.setOnClickListener {
            val build = AlertDialog.Builder(context)
            val paiement = this.paiementDao
            val livraison = this.livraisonDao

            build.apply {
                setTitle("Confirmation")
                setMessage("La reduction de la taille nécessite la suppression de toutes les données soit du paiement ou livraison des clients ayant un montant égal à 0. \nEtes-vous sûr de bien vouloir reduire la taille quand même?")
                setPositiveButton("oui", DialogInterface.OnClickListener { dialog, which ->

                        paiement.removeByAmount(0.0)
                        livraison.removeByAmount(0.0)
                        Toast.makeText(requireContext(), "Data Reduite", Toast.LENGTH_SHORT).show()

                      })
                setNegativeButton("non", DialogInterface.OnClickListener { dialog, which ->
                    Toast.makeText(
                        requireContext(),
                        "Operation annulé",
                        Toast.LENGTH_SHORT
                    ).show()  })

            }
            build.create().show()




        }

        return view

    }



}