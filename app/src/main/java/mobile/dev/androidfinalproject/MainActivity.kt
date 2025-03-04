package mobile.dev.androidfinalproject

import android.R
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import mobile.dev.androidfinalproject.databinding.ActivityMainBinding
import mobile.dev.androidfinalproject.utilities.SingletonFirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var _binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

//        val toolbar: androidx.appcompat.widget.Toolbar = _binding.customToolbar
//        setSupportActionBar(toolbar)
//        val toolbarTitle = _binding.toolbarTitle
//        toolbarTitle.text = "My Custom App"
//




        ViewCompat.setOnApplyWindowInsetsListener(_binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
    }





}