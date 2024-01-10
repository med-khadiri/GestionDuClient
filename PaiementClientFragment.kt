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


class PaiementClientFragment : Fragment() {
    private lateinit var editTextIdClient: EditText
    private lateinit var textViewNomPaiem: TextView
    private lateinit var textViewPrenomPaiem: TextView
    private lateinit var clientDao: ClientDao
    private lateinit var paiementDao: PaiementDao
    private lateinit var textViewMontantPaiem: TextView
    private lateinit var btnSelection:Button
    private lateinit var editTextMontantPaye: EditText
    private lateinit var btnPayer:Button

    private lateinit var btnHistoriquePaie:Button
    private var historiquePaiement:HistoriquePaiement?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_paiement_client, container, false)
        this.clientDao=ClientDao(requireContext())
        this.paiementDao = PaiementDao(requireContext())

        editTextIdClient=view.findViewById(R.id.editTextIdPaiement!!)
        editTextMontantPaye = view.findViewById(R.id.editTextMontantPayé!!)
        textViewNomPaiem = view.findViewById(R.id.TextViewNomPaiement!!)
        textViewPrenomPaiem = view.findViewById(R.id.TextViewPrenomPaiement!!)
        textViewMontantPaiem = view.findViewById(R.id.TextViewMontantPaiement!!)
        btnSelection = view.findViewById(R.id.btnPaiementSelectionner!!)
        btnHistoriquePaie=view.findViewById(R.id.btnHistoriquePaie!!)
        btnPayer = view.findViewById(R.id.btnPayer!!)
        btnSelection.setOnClickListener {
            if (this.editTextIdClient.text.isNotBlank())
            {
                try {
                    val clientInfo:Cursor = this.clientDao.getById(this.editTextIdClient.text.toString().toInt())
                    clientInfo.moveToFirst()
                    this.textViewNomPaiem.setText("Nom:\n${clientInfo.getString(1)}")
                    this.textViewPrenomPaiem.setText("Prenom:\n"+clientInfo.getString(2))
                    this.textViewMontantPaiem.setText("Montant:\n"+clientInfo.getDouble(5).toString())
                }catch (e:Exception){
                    Toast.makeText(this@PaiementClientFragment.requireContext(), "le client avec le id ${this.editTextIdClient.text.toString()} n'existe pas", Toast.LENGTH_SHORT).show()
                }
            }
            else
            {
                Toast.makeText(this@PaiementClientFragment.requireContext(), "Le champ Id Client ne peut pas etre vide !", Toast.LENGTH_SHORT).show()
            }
        }
        btnPayer.setOnClickListener {
            if (this.editTextMontantPaye.text.isNotBlank() && this.editTextIdClient.text.isNotBlank())
            {
                try {
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                    val current = LocalDateTime.now().format(formatter)
                    //this.paiementDao.insert(Paiement(current,this.editTextIdClient.text.toString().toInt(),this.editTextMontantPaye.text.toString().toFloat()))
                    var clientInfo:Cursor = this.clientDao.getById(this.editTextIdClient.text.toString().toInt())
                    clientInfo.moveToFirst()
                    val updatedClientMontant:Double = clientInfo.getDouble(5) - this.editTextMontantPaye.text.toString().toDouble()
                    if (clientInfo.getDouble(5) >= this.editTextMontantPaye.text.toString().toDouble())
                    {
                        this.paiementDao.insert(Paiement(current,this.editTextIdClient.text.toString().toInt(),this.editTextMontantPaye.text.toString().toFloat()))
                        this.clientDao.updateMontantClient(this.editTextIdClient.text.toString().toInt(),updatedClientMontant)
                        clientInfo.close()
                        clientInfo = this.clientDao.getById(this.editTextIdClient.text.toString().toInt())
                        clientInfo.moveToFirst()
                        this.textViewMontantPaiem.setText("Montant:\n"+clientInfo.getDouble(5).toString())
                        Toast.makeText(this@PaiementClientFragment.requireContext(), "Payé!", Toast.LENGTH_SHORT).show()
                        clearFieldMantant()
                    }
                    else
                    {
                        Toast.makeText(this@PaiementClientFragment.requireContext(), "Le montant du Client est plus petit que celui à payer!", Toast.LENGTH_SHORT).show()
                    }
                }catch (e:Exception)
                {
                    Toast.makeText(this@PaiementClientFragment.requireContext(), "Ce client n'existe pas!", Toast.LENGTH_SHORT).show()
                }


            }else
            {
                Toast.makeText(this@PaiementClientFragment.requireContext(), "les champs 'montant' et 'IdClient' ne peuvent pas etre vide!", Toast.LENGTH_SHORT).show()
                clearFields()
            }
        }
        btnHistoriquePaie.setOnClickListener {
            if(this.editTextIdClient.text.isNotBlank())
            {
                Comunicator.clearList()
                Comunicator.ajouter(this.editTextIdClient.text.toString().toInt())
                if (historiquePaiement==null)
                {
                    historiquePaiement= HistoriquePaiement()
                    chargerFragment(historiquePaiement!!)
                }
                else
                {
                    chargerFragment(historiquePaiement!!)
                }


            }
            else
            {
                Toast.makeText(this@PaiementClientFragment.requireContext(), "Id client Manquant !", Toast.LENGTH_SHORT).show()
            }
        }
        this.editTextIdClient.setOnClickListener{
            clearFields()
        }




        return view
    }

    private fun clearFieldMantant() {
        this.editTextMontantPaye.text.clear()
        this.editTextIdClient.requestFocus()
    }


    private fun clearFields() {
        this.editTextMontantPaye.text.clear()
        this.textViewPrenomPaiem.setText("Prenom:")
        this.textViewNomPaiem.setText("Nom:")
        this.textViewMontantPaiem.setText("Montant:")
        this.editTextIdClient.text.clear()
        this.editTextIdClient.requestFocus()
    }
    private fun chargerFragment(fragment: Fragment)
    {
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.myFrameLayout, fragment!!)
            ?.commit()
    }

}