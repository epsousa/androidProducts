package com.example.evair.produtos

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [fragment_sobre.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [fragment_sobre.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_sobre : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_fragment_sobre, container, false)
    }

}
