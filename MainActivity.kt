package ma.istakhemisset.gestionclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var drawer:DrawerLayout
    private lateinit var navigationView :NavigationView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var toggle: ActionBarDrawerToggle
    private  var saisieFragment :SaisieClientFragment ?=null
    private var affchageFragment: AffichageClientFragment?=null
    private var modifierFragment: ModificationClientFragment?=null
    private var payerFragment:PaiementClientFragment?=null
    private var historiquePaiement:HistoriquePaiement?=null
    private var affichageClientPayer:AffichageClientPayer?=null
    private var livrerFragment:LivraisonClientFragment?=null
    //private lateinit var frameLayout: FrameLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawer=findViewById(R.id.myDrawer)
        navigationView=findViewById(R.id.myNavView)
        toolbar=findViewById(R.id.myToolBar)
        setSupportActionBar(toolbar)
        toggle= ActionBarDrawerToggle(this,drawer,toolbar,R.string.open_drawer,R.string.close_drawer)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        if (affchageFragment==null)
        {
            affchageFragment= AffichageClientFragment()
            chargerFragment(affchageFragment)
        }
        else
        {
            chargerFragment(affchageFragment)
        }
        navigationView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.menuAjouter->
                {

                    if (saisieFragment==null)
                    {
                        saisieFragment= SaisieClientFragment()
                        chargerFragment(saisieFragment)
                    }
                    else
                    {
                        chargerFragment(saisieFragment)
                    }


                }
                R.id.menuAfficher->
                {
                    if (affchageFragment==null)
                    {
                        affchageFragment= AffichageClientFragment()
                        chargerFragment(affchageFragment)
                    }
                    else
                    {
                        chargerFragment(affchageFragment)
                    }

                }
                R.id.menuModifier->
                {
                    if (modifierFragment==null)
                    {
                        modifierFragment= ModificationClientFragment()
                        chargerFragment(modifierFragment)
                    }
                    else
                    {
                        chargerFragment(modifierFragment)
                    }
                }
                R.id.menuPayer->
                {
                    if (payerFragment==null)
                    {
                        payerFragment= PaiementClientFragment()
                        chargerFragment(payerFragment)
                    }
                    else
                    {
                        chargerFragment(payerFragment)
                    }
                }
                R.id.menuLivrer->
                {
                    if (livrerFragment==null)
                    {
                        livrerFragment= LivraisonClientFragment()
                        chargerFragment(livrerFragment)
                    }
                    else
                    {
                        chargerFragment(livrerFragment)
                    }
                }
                R.id.menuAfficherPaye->
                {
                    if (affichageClientPayer==null)
                    {
                        affichageClientPayer= AffichageClientPayer()
                        chargerFragment(affichageClientPayer)
                    }
                    else
                    {
                        chargerFragment(affichageClientPayer)
                    }
                }



            }
            drawer.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener true
        }

    }

     private   fun chargerFragment(fragment: Fragment?) =
            this.supportFragmentManager.beginTransaction().replace(R.id.myFrameLayout, fragment!!)
                .commit()

    override fun onBackPressed() {
        if (this.drawer.isDrawerOpen(GravityCompat.START)) this.drawer.closeDrawer(GravityCompat.START)
        else  //super.onBackPressed()
            this.finish()
    }




}