package mobile.dev.androidfinalproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import mobile.dev.androidfinalproject.databinding.FragmentGetDetailsBinding
import mobile.dev.androidfinalproject.dbHelpers.SingletonFirebaseDb
import mobile.dev.androidfinalproject.dbHelpers.UserDbHelper
import mobile.dev.androidfinalproject.utilities.SingletonFirebaseAuth

class GetDetailsFragment(
    private var _binding: FragmentGetDetailsBinding? = null
) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGetDetailsBinding.inflate(inflater,container,false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.moreDetailsSaveBtn?.setOnClickListener(this::handleSave)
    }




    fun handleSave(view:View){

        val data = mapOf(
            "height" to "20",
            "weight" to "30"
        )

        val email = SingletonFirebaseAuth.getInstance().getCurrentUser().email!!

        UserDbHelper.updateDetails(email,data, successListener = { _ ->
                Toast.makeText(context, "Data saved Successfully", Toast.LENGTH_SHORT)
                    .show()

                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
                activity?.finish()


        },
            failureListener = { error ->
                Log.e("SignUp", "Error posting user data", error)
                Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            })




    }






    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}