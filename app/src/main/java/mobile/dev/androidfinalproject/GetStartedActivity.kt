package mobile.dev.androidfinalproject

import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import mobile.dev.androidfinalproject.databinding.ActivityGetStartedBinding
import mobile.dev.androidfinalproject.utilities.SingletonFirebaseAuth


class GetStartedActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityGetStartedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityGetStartedBinding.inflate(layoutInflater)
        setContentView(_binding.root)



        if(SingletonFirebaseAuth.getInstance().isLoggedIn()){
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        ViewCompat.setOnApplyWindowInsetsListener(_binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
    }
}