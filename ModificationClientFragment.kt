package ma.istakhemisset.gestionclient

import android.app.AlertDialog
import android.content.DialogInterface
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import java.security.KeyStore.TrustedCertificateEntry

class ModificationClientFragment : Fragment() {

    private lateinit var editTextIdModif: EditText
    private lateinit var editTextNomModif: EditText
    private lateinit var editTextPrenomModif: EditText
    private lateinit var editTextTelModif: EditText
    private lateinit var btnChercher: Button
    private lateinit var btnModifier: Button
    private lateinit var btnSupprimer:Button
    private lateinit var clientDao: ClientDao
    private lateinit var paiementDao: PaiementDao
    private lateinit var livraisonDao: LivraisonDao
    private lateinit var editTextEmailModif: EditText
    private lateinit var editTextPrixModif: EditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_modification_client, container, false)
        editTextNomModif=view.findViewById(R.id.editTextNomModif!!)
        editTextIdModif=view.findViewById(R.id.editTextIdModif!!)
        editTextPrenomModif=view.findViewById(R.id.editTextPrenomModif!!)
        editTextTelModif=view.findViewById(R.id.editTextTelModif!!)
        editTextEmailModif=view.findViewById(R.id.editTextEmailModif!!)
        editTextPrixModif = view.findViewById(R.id.editTextPrixModif!!)
        btnModifier= view.findViewById(R.id.btnClientModif!!)
        btnSupprimer=view.findViewById(R.id.btnClientSpprimer!!)
        this.clientDao=ClientDao(requireContext())
        this.paiementDao=PaiementDao(requireContext())
        this.livraisonDao=LivraisonDao(requireContext())
        btnChercher=view.findViewById(R.id.btnClientChercher!!)
        btnChercher.setOnClickListener{
            if (this.editTextIdModif.text.isNotBlank())
            {
                try {
                    val clientLine:Cursor = this.clientDao.getById(editTextIdModif.text.toString().toInt())
                    clientLine.moveToFirst()
                    this.editTextNomModif.setText(clientLine.getString(1))
                    //Log.d("Tag",clientLine.columnCount.toString())
                    this.editTextPrenomModif.setText(clientLine.getString(2))
                    this.editTextEmailModif.setText(clientLine.getString(3))
                    this.editTextTelModif.setText(clientLine.getString(4))
                    //this.editTextMontModif.setText(clientLine.getDouble(5).toString())
                    //this.editTextDateModif.setText(clientLine.getString(6))
                    this.editTextPrixModif.setText((clientLine.getDouble(7).toString()))
                }catch (e:java.lang.Exception)
                {
                    Toast.makeText(this.requireContext(), "Ce client n'existe pas!", Toast.LENGTH_SHORT).show()
                }



            }
            else
            {
                Toast.makeText(this@ModificationClientFragment.requireContext(), "Vous n'avez pas donner du Id", Toast.LENGTH_SHORT).show()
            }
        }

        btnModifier.setOnClickListener {
            if (this.editTextIdModif.text.isNotBlank() &&this.editTextNomModif.text.isNotBlank() && this.editTextPrenomModif.text.isNotBlank() && this.editTextEmailModif.text.isNotBlank() && this.editTextTelModif.text.isNotBlank()   && this.editTextPrixModif.text.isNotBlank())
            {

                val client = this.clientDao
                val idclient=this.editTextIdModif.text.toString().toInt()
                val nom=this.editTextNomModif.text.toString()
                val prenom = this.editTextPrenomModif.text.toString()
                val email = this.editTextEmailModif.text.toString()
                val tel = this.editTextTelModif.text.toString()
                val prix = this.editTextPrixModif.text.toString().toDouble()
                val build = AlertDialog.Builder(context)

                build.apply {
                    setTitle("Confirmation")
                    setMessage("Voulez-vous vraiment appliquer les modifications?")
                    setPositiveButton("oui",DialogInterface.OnClickListener { dialog, which ->
                        try {
                        client.update(idclient,nom,prenom,email,tel,prix)
                        Toast.makeText(this@ModificationClientFragment.requireContext(), "modification réussite", Toast.LENGTH_SHORT).show()
                        clearEditText()
                        }catch (e:Exception)
                        {
                        Toast.makeText(this@ModificationClientFragment.requireContext(), "ce Client n'existe pas !", Toast.LENGTH_SHORT).show()
                        }  })
                    setNegativeButton("non",DialogInterface.OnClickListener { dialog, which ->
                        Toast.makeText(
                            this@ModificationClientFragment.requireContext(),
                            "Modification annulé",
                            Toast.LENGTH_SHORT
                        ).show()  })

                }
                build.create().show()




            }
         }
        btnSupprimer.setOnClickListener {
            val client = this.clientDao
            val paiement = this.paiementDao
            val livraison = this.livraisonDao
            val idClient = this.editTextIdModif.text.toString().toInt()
            val clientInfo = client.getById(this.editTextIdModif.text.toString().toInt())



            if (this.editTextIdModif.text.isNotBlank() && clientInfo.moveToFirst())
            {
                val build = AlertDialog.Builder(context)

                build.apply {
                    setTitle("Confirmation")
                    setTitle("Voulez-vous vraiment supprimer le client ${clientInfo.getString(1)} ${clientInfo.getString(2)}")
                    setPositiveButton("oui",DialogInterface.OnClickListener { dialog, which ->

                            client.removeById(idClient)
                            paiement.removeById(idClient)
                            livraison.removeById(idClient)
                            Log.e("Tag","Clientremoved")
                            Toast.makeText(this@ModificationClientFragment.requireContext(), "Suppression réussite", Toast.LENGTH_SHORT).show()
                            clearEditText()
                          })
                    setNegativeButton("non",DialogInterface.OnClickListener { dialog, which ->
                        Toast.makeText(
                            this@ModificationClientFragment.requireContext(),
                            "Suppression annulé",
                            Toast.LENGTH_SHORT
                        ).show()  })

                }
                build.create().show()

            }
        }



        return view
    }
    private fun clearEditText() {
        this.editTextTelModif.text.clear()
        this.editTextNomModif.text.clear()
        this.editTextPrenomModif.text.clear()
        this.editTextEmailModif.text.clear()
        this.editTextPrixModif.text.clear()
        this.editTextIdModif.requestFocus()
    }
    private fun show()
    {
        Comunicator.clearList()
        val builder = AlertDialog.Builder(context)
        val dialog = builder.create()

        builder.apply {
            setTitle("Confirmation")
            setMessage("Voulez-vous vraiment appliquer les modifications?")
            setPositiveButton("Oui",DialogInterface.OnClickListener { dialog, which -> Comunicator.ajouter(1)  })
            setNegativeButton("Non",DialogInterface.OnClickListener { dialog, which -> Comunicator.ajouter(2)   })

        }
        dialog.show()


    }
}
