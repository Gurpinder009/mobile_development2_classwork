package com.example.classwork

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.classwork.databinding.FragmentSecondBinding


class SecondFragment(
    private var fragmentBinding:FragmentSecondBinding?= null
) : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.fragmentBinding = FragmentSecondBinding.inflate(inflater, container,false)
        return this.fragmentBinding?.root
    }


    override fun onStart() {
        super.onStart()
        val args:SecondFragmentArgs = SecondFragmentArgs.fromBundle(requireArguments())
        val message:String = args.message
        fragmentBinding?.textView2?.text = message
    }


    override fun onDestroyView() {
        super.onDestroyView()
        this.fragmentBinding = null;
    }



    public interface OnFragmentInteractionListener{
        fun onFragmentInteraction(uri: Uri)
    }


}