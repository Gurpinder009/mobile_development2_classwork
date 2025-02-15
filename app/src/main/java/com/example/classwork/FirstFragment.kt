package com.example.classwork

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.classwork.databinding.FragmentFirstBinding
import kotlin.math.log


class FirstFragment(
    private var fragmentBinding:FragmentFirstBinding?= null
        ) : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.fragmentBinding = FragmentFirstBinding.inflate(inflater, container,false)
        return this.fragmentBinding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.fragmentBinding?.button?.setOnClickListener(this::handleClick)


//        this.fragmentBinding?.button?.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.first_to_second_fragment,null))
    }

    fun handleClick(view:View){
        val action:FirstFragmentDirections.FirstToSecondFragment = FirstFragmentDirections.firstToSecondFragment();
        action.message = fragmentBinding?.editTextText!!.text.toString();
        Navigation.findNavController(view).navigate(action)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        this.fragmentBinding = null;
    }


}