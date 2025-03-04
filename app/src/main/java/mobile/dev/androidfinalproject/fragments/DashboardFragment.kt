package mobile.dev.androidfinalproject.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import mobile.dev.androidfinalproject.GetStartedActivity

import mobile.dev.androidfinalproject.databinding.FragmentDashboardBinding
import mobile.dev.androidfinalproject.utilities.SingletonFirebaseAuth


class DashboardFragment (
    private var _binding : FragmentDashboardBinding? = null
) : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater,container,false);
        return _binding?.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.signOut?.setOnClickListener{
            _-> run{
                SingletonFirebaseAuth.getInstance().getFirebaseAuth().signOut()
                val intent = Intent(context, GetStartedActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}