package mobile.dev.androidfinalproject

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import mobile.dev.androidfinalproject.databinding.ActivityMainBinding
import mobile.dev.androidfinalproject.utilities.SingletonFirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var _binding:ActivityMainBinding
    private lateinit var _navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()


        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)



        val toolbar: androidx.appcompat.widget.Toolbar = _binding.toolbar
        setSupportActionBar(toolbar)





        val navHostFragment = supportFragmentManager.findFragmentById(_binding.fragmentContainerView2.id) as NavHostFragment
        _navController = navHostFragment.navController


        _binding.bottomNavBar.setupWithNavController(_navController)


        val windowInsetsController = WindowInsetsControllerCompat(window, window.decorView)
        windowInsetsController.isAppearanceLightStatusBars = false
        window.statusBarColor = ContextCompat.getColor(this, R.color.primary)



        ViewCompat.setOnApplyWindowInsetsListener(_binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_signout ->{
                SingletonFirebaseAuth.getInstance().getFirebaseAuth().signOut()
                val intent = Intent(baseContext, GetStartedActivity::class.java)
                startActivity(intent)
                finish()
                true
            }
            R.id.action_profile-> {

                if(_navController.currentDestination?.id != R.id.profileFragment) {
                    _navController.navigate(R.id.action_to_profile_fragment)
                }
                true
            }


            else ->  super.onOptionsItemSelected(item)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

}