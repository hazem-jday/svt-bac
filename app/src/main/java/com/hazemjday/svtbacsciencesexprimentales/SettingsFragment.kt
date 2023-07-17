package com.hazemjday.svtbacsciencesexprimentales

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.android.material.slider.Slider
import com.google.android.material.switchmaterial.SwitchMaterial


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment() {
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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val dark : SwitchMaterial = view.findViewById(R.id.switch1)
        val darkModePrefManager = DarkModePrefManager(activity!!)
        if(darkModePrefManager.isNightMode){
            dark.isChecked=true
        }
        dark.setOnCheckedChangeListener{ _, _ ->

                darkModePrefManager.setDarkMode(!darkModePrefManager.isNightMode)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                val intent = Intent(activity, MainActivity::class.java)
                val b = Bundle()
                b.putBoolean("dark", true)
                intent.putExtras(b)
                startActivity(intent)
                activity?.finish()
        }
        val timerSwitch = view.findViewById<SwitchMaterial>(R.id.switch2)
        val editor: SharedPreferences.Editor
        val pref: SharedPreferences = activity!!.getSharedPreferences("timer", 0)
        editor = pref.edit()
        val timerSlider = view.findViewById<Slider>(R.id.slider1)

        if(pref.contains("v")){
            timerSlider.value = pref.getFloat("v",50F)
        }
        else{
            editor.putFloat("v",50F).commit()
        }

        if(pref.getBoolean("on",false)){
            timerSwitch.isChecked=true
            timerSlider.isEnabled=true
        }
        else{
            timerSwitch.isChecked=false
            timerSlider.isEnabled=false
        }

        timerSwitch.setOnCheckedChangeListener { _, checked ->
            timerSlider.isEnabled = checked
            editor.putBoolean("on",checked).commit()
        }

        timerSlider.addOnChangeListener { _, value, _ ->
            editor.putFloat("v",value).commit()
        }


        return view
    }
}