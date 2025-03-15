package mobile.dev.androidfinalproject

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mobile.dev.androidfinalproject.databinding.FragmentGetDetailsBinding

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
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }






    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}