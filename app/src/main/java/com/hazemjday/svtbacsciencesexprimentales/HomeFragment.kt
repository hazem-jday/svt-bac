package com.hazemjday.svtbacsciencesexprimentales

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_home, container, false)

        view.findViewById<View>(R.id.to_homme).setOnClickListener{
            val i = Intent(activity,Quizz::class.java)
            i.putExtra("chp","homme")
            startActivity(i)
        }
        view.findViewById<View>(R.id.to_femme).setOnClickListener{
            val i = Intent(activity,Quizz::class.java)
            i.putExtra("chp","femme")
            startActivity(i)
        }
        view.findViewById<View>(R.id.to_proc).setOnClickListener{
            val i = Intent(activity,Quizz::class.java)
            i.putExtra("chp","proc")
            startActivity(i)
        }
        view.findViewById<View>(R.id.to_brassage).setOnClickListener{
            val i = Intent(activity,Quizz::class.java)
            i.putExtra("chp","brassage")
            startActivity(i)
        }
        view.findViewById<View>(R.id.to_gen_hum).setOnClickListener{
            val i = Intent(activity,Quizz::class.java)
            i.putExtra("chp","gen_hum")
            startActivity(i)
        }
        view.findViewById<View>(R.id.to_tissu).setOnClickListener{
            val i = Intent(activity,Quizz::class.java)
            i.putExtra("chp","tissu")
            startActivity(i)
        }
        view.findViewById<View>(R.id.to_reflexe).setOnClickListener{
            val i = Intent(activity,Quizz::class.java)
            i.putExtra("chp","reflexe")
            startActivity(i)
        }
        view.findViewById<View>(R.id.to_muscle).setOnClickListener{
            val i = Intent(activity,Quizz::class.java)
            i.putExtra("chp","muscle")
            startActivity(i)
        }
        view.findViewById<View>(R.id.to_pression).setOnClickListener{
            val i = Intent(activity,Quizz::class.java)
            i.putExtra("chp","pression")
            startActivity(i)
        }
        view.findViewById<View>(R.id.to_hygiene).setOnClickListener{
            val i = Intent(activity,Quizz::class.java)
            i.putExtra("chp","hygiene")
            startActivity(i)
        }
        view.findViewById<View>(R.id.to_soi).setOnClickListener{
            val i = Intent(activity,Quizz::class.java)
            i.putExtra("chp","soi")
            startActivity(i)
        }
        view.findViewById<View>(R.id.to_reponse).setOnClickListener{
            val i = Intent(activity,Quizz::class.java)
            i.putExtra("chp","reponse")
            startActivity(i)
        }
        view.findViewById<View>(R.id.to_dysfonc).setOnClickListener{
            val i = Intent(activity,Quizz::class.java)
            i.putExtra("chp","dysfonc")
            startActivity(i)
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}