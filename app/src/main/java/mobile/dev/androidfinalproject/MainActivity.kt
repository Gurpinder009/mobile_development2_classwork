package mobile.dev.androidfinalproject

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import mobile.dev.androidfinalproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var _binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)




        val navHostFragment = supportFragmentManager.findFragmentById(_binding.fragmentContainerView2.id) as NavHostFragment
        val navController = navHostFragment.navController


        _binding.bottomNavBar.setupWithNavController(navController)

        ViewCompat.setOnApplyWindowInsetsListener(_binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
    }





}