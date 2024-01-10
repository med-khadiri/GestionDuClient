package ma.istakhemisset.gestionclient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class SaisieClientFragment : Fragment()  {
    private lateinit var editTextNom:EditText
    private lateinit var editTextPrenom:EditText
    private lateinit var editTextTel:EditText
    private lateinit var btnAjouter:Button
    private lateinit var clientDao: ClientDao
    private lateinit var paiementDao:PaiementDao
    private lateinit var editTextEmail: EditText

    private lateinit var editTextPrix : EditText






    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,
                               savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_saisie_client, container, false)
        editTextNom=view.findViewById(R.id.editTextNom!!)

        editTextPrenom=view.findViewById(R.id.editTextPrenom!!)
        editTextTel=view.findViewById(R.id.editTextTel!!)
        editTextEmail=view.findViewById(R.id.editTextEmail!!)
        editTextPrix = view.findViewById(R.id.editTextPrix!!)
        this.clientDao=ClientDao(requireContext())
        btnAjouter=view.findViewById(R.id.btnAjouterClient!!)
        btnAjouter.setOnClickListener   {
            if (editTextNom.text.isNotBlank() && editTextPrenom.text.isNotBlank() && editTextTel.text.isNotBlank() && editTextEmail.text.isNotBlank()  && editTextPrix.text.isNotBlank())

            {
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                val current = LocalDateTime.now().format(formatter)
                this.clientDao.insert(Client(this.editTextNom.text.toString(),this.editTextPrenom.text.toString(),this.editTextEmail.text.toString(),this.editTextTel.text.toString(),0.0,current.toString(),this.editTextPrix.text.toString().toDouble()))

                Toast.makeText(this@SaisieClientFragment.requireContext(), "Ajout réussi", Toast.LENGTH_SHORT).show()
                clearEditText()
            }
            else
            {
                Toast.makeText(this@SaisieClientFragment.requireContext(), "Informations Client Manquantes !", Toast.LENGTH_SHORT).show()
            }

        }
        return view
    }

    private fun clearEditText() {
        this.editTextTel.text.clear()
        this.editTextNom.text.clear()
        this.editTextPrenom.text.clear()
        this.editTextEmail.text.clear()
        this.editTextPrix.text.clear()
        this.editTextNom.requestFocus()
    }


}