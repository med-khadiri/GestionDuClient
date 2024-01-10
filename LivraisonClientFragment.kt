package ma.istakhemisset.gestionclient

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class LivraisonClientFragment : Fragment() {


    private lateinit var editTextIdClient: EditText
    private lateinit var textViewNomLivraison: TextView
    private lateinit var textViewPrenomLivraison: TextView
    private lateinit var clientDao: ClientDao
    private lateinit var paiementDao: PaiementDao
    private lateinit var livraisonDao: LivraisonDao
    private lateinit var textViewPrixLivraison: TextView
    private lateinit var btnSelection: Button
    private lateinit var editTextQuantite: EditText
    private lateinit var btnLivrer: Button
    private lateinit var btnHistoriqueLivraison: Button
    private var historiqueLivraison: HistoriqueClientFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_livraison_client, container, false)
        this.clientDao = ClientDao(requireContext())
        this.paiementDao = PaiementDao(requireContext())
        this.livraisonDao= LivraisonDao(requireContext())
        editTextIdClient = view.findViewById(R.id.editTextIdLivraison!!)
        editTextQuantite = view.findViewById(R.id.editTextQuantiteLivrer!!)
        textViewNomLivraison = view.findViewById(R.id.TextViewNomLivraison!!)
        textViewPrenomLivraison = view.findViewById(R.id.TextViewPrenomLivraison!!)
        textViewPrixLivraison = view.findViewById(R.id.TextViewPrixLivraison!!)
        btnSelection = view.findViewById(R.id.btnLivraisonSelectionner!!)
        btnHistoriqueLivraison = view.findViewById(R.id.btnHistoriqueLivraison!!)
        btnLivrer = view.findViewById(R.id.btnLivrer!!)
        btnSelection.setOnClickListener {
            if (this.editTextIdClient.text.isNotBlank()) {
                try {
                    val clientInfo: Cursor =
                        this.clientDao.getById(this.editTextIdClient.text.toString().toInt())
                    clientInfo.moveToFirst()
                    this.textViewNomLivraison.setText("Nom:\n${clientInfo.getString(1)}")
                    this.textViewPrenomLivraison.setText("Prenom:\n" + clientInfo.getString(2))
                    this.textViewPrixLivraison.setText("Prix Unitaire:\n" + clientInfo.getDouble(7).toString())
                }catch (exeption:java.lang.Exception)
                {
                    Toast.makeText(this@LivraisonClientFragment.requireContext(), "Ce client avec le Id ${this.editTextIdClient.text.toString()} n'existe pas", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(
                    this@LivraisonClientFragment.requireContext(),
                    "Le champ Id Client ne peut pas etre vide !",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        btnLivrer.setOnClickListener {
            if (this.editTextQuantite.text.isNotBlank() && this.editTextIdClient.text.isNotBlank()) {
                try {
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                    val current = LocalDateTime.now().format(formatter)
                    this.livraisonDao.insert(
                        Livraison(

                            this.editTextIdClient.text.toString().toInt(),
                            current,
                            this.editTextQuantite.text.toString().toInt()
                        )
                    )
                    val clientInfo: Cursor =
                        this.clientDao.getById(this.editTextIdClient.text.toString().toInt())
                    clientInfo.moveToFirst()
                    val updatedClientMontant: Double =
                        clientInfo.getDouble(5) + (this.editTextQuantite.text.toString().toDouble()*clientInfo.getDouble(7))
                    this.clientDao.updateMontantClient(
                        this.editTextIdClient.text.toString().toInt(),
                        updatedClientMontant
                    )

                    Toast.makeText(
                        this@LivraisonClientFragment.requireContext(),
                        "Livré!",
                        Toast.LENGTH_SHORT
                    ).show()
                    clearFieldQuantite()
                }catch (e:java.lang.Exception)
                {
                    Toast.makeText(this@LivraisonClientFragment.requireContext(), "ce Client n'existe pas!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(
                    this@LivraisonClientFragment.requireContext(),
                    "les champs 'quantité' et 'IdClient' ne peuvent pas etre vide!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        btnHistoriqueLivraison.setOnClickListener {
            if(this.editTextIdClient.text.isNotBlank())
            {
                Comunicator.clearList()
                Comunicator.ajouter(this.editTextIdClient.text.toString().toInt())
                if (historiqueLivraison==null)
                {
                    historiqueLivraison= HistoriqueClientFragment()
                    chargerFragment(historiqueLivraison!!)
                }
                else
                {
                    chargerFragment(historiqueLivraison!!)
                }


            }
            else
            {
                Toast.makeText(this@LivraisonClientFragment.requireContext(), "Id client Manquant !", Toast.LENGTH_SHORT).show()
            }

        }
        editTextIdClient.setOnClickListener {
            clearFields()
        }



            return view


    }

    private fun clearFields() {
        this.editTextQuantite.text.clear()
        this.textViewPrenomLivraison.setText("Prenom:")
        this.textViewNomLivraison.setText("Nom:")
        this.textViewPrixLivraison.setText("Prix Unitaire:")
        this.editTextIdClient.text.clear()
        this.editTextIdClient.requestFocus()
    }
    private fun clearFieldQuantite()
    {
        this.editTextQuantite.text.clear()
        this.editTextQuantite.requestFocus()
    }
    private fun chargerFragment(fragment: Fragment)
    {
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.myFrameLayout, fragment!!)
            ?.commit()
    }
}