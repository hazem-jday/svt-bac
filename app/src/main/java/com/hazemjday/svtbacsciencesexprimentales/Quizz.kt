package com.hazemjday.svtbacsciencesexprimentales

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.content.res.TypedArray
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView


class Quizz : AppCompatActivity() {
    private val array = ArrayList<ArrayList <String>>()
    private val responses = ArrayList <String>()
    private var index = 0
    private var score = 0
    private var mr = false
    private lateinit var progressBar: ProgressBar
    private lateinit var timerBar: ProgressBar
    private lateinit var obtainTypedArray : TypedArray
    private lateinit var r1 : MaterialCardView
    private lateinit var r2 : MaterialCardView
    private lateinit var r3 : MaterialCardView
    private lateinit var r4 : MaterialCardView
    private lateinit var confirm : Button
    private lateinit var myRep : MaterialButton
    private lateinit var timer: CountDownTimer
    private lateinit var pref: SharedPreferences
    private var duration = 50F
    private var chrono = false
    private var checkpoint = 50F
    private var icon =0
    private lateinit var title : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (DarkModePrefManager(this).isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        pref = this.getSharedPreferences("timer", 0)
        chrono = pref.getBoolean("on",false)
        duration = pref.getFloat("v",50F)
        init()

        confirm.setOnClickListener{
            if (index < 4) {
                responsesPages()

            } else {
                stopTimer()
                addResponse()
                calcScore()

                setContentView(R.layout.activity_quizz_result)


                findViewById<TextView>(R.id.quizz_header_result).text=title
                findViewById<ImageView>(R.id.chp_icon_result).setImageResource(icon)

                findViewById<TextView>(R.id.score).text = "$score / 5"
                findViewById<TextView>(R.id.note).text = if (score == 5) "Excellent !" else if (score == 4) "Bravo :D" else if (score == 3) "Bien :)" else if (score == 2) "Pas mal :|" else if (score == 1) "Mauvais :(" else "Zéro :'("

                findViewById<MaterialCardView>(R.id.quit_btn).setOnClickListener {
                    finish()
                }
                findViewById<MaterialCardView>(R.id.corrig_btn).setOnClickListener {
                    index = 0
                    setContentView(R.layout.activity_quizz_correction)


                    findViewById<TextView>(R.id.quizz_header).text=title
                    findViewById<ImageView>(R.id.chp_icon).setImageResource(icon)

                    findViewById<TextView>(R.id.question).text = array[index][0]
                    findViewById<TextView>(R.id.r1txt).text = array[index][1]
                    findViewById<TextView>(R.id.r2txt).text = array[index][2]
                    findViewById<TextView>(R.id.r3txt).text = array[index][3]
                    findViewById<TextView>(R.id.r4txt).text = array[index][4]

                    val r = array[index][5]

                    myRep = findViewById(R.id.ma_reponse)

                    myRep.setOnClickListener {

                        findViewById<MaterialCardView>(R.id.r1).backgroundTintList = ContextCompat.getColorStateList(this, R.color.quizz_item_selector)
                        findViewById<MaterialCardView>(R.id.r2).backgroundTintList = ContextCompat.getColorStateList(this, R.color.quizz_item_selector)
                        findViewById<MaterialCardView>(R.id.r3).backgroundTintList = ContextCompat.getColorStateList(this, R.color.quizz_item_selector)
                        findViewById<MaterialCardView>(R.id.r4).backgroundTintList = ContextCompat.getColorStateList(this, R.color.quizz_item_selector)

                        if(!mr){
                            val rep = responses[index]
                            if(rep.contains("1")) findViewById<MaterialCardView>(R.id.r1).backgroundTintList = ContextCompat.getColorStateList(this, R.color.my_answer)
                            if(rep.contains("2")) findViewById<MaterialCardView>(R.id.r2).backgroundTintList = ContextCompat.getColorStateList(this, R.color.my_answer)
                            if(rep.contains("3")) findViewById<MaterialCardView>(R.id.r3).backgroundTintList = ContextCompat.getColorStateList(this, R.color.my_answer)
                            if(rep.contains("4")) findViewById<MaterialCardView>(R.id.r4).backgroundTintList = ContextCompat.getColorStateList(this, R.color.my_answer)
                            mr = true
                            myRep.text = "Voir correction"
                        }
                        else{
                            val rep = array[index][5]
                            if(rep.contains("1")){
                                findViewById<MaterialCardView>(R.id.r1).backgroundTintList = ContextCompat.getColorStateList(this, R.color.caribbean_green)

                            }
                            if(rep.contains("2")){
                                findViewById<MaterialCardView>(R.id.r2).backgroundTintList = ContextCompat.getColorStateList(this, R.color.caribbean_green)
                            }
                            if(rep.contains("3")){
                                findViewById<MaterialCardView>(R.id.r3).backgroundTintList = ContextCompat.getColorStateList(this, R.color.caribbean_green)
                            }
                            if(rep.contains("4")){
                                findViewById<MaterialCardView>(R.id.r4).backgroundTintList = ContextCompat.getColorStateList(this, R.color.caribbean_green)
                            }

                            mr = false
                            myRep.text = "Voir ma réponse"
                        }
                    }

                    if(r.contains("1")){
                        findViewById<MaterialCardView>(R.id.r1).backgroundTintList = ContextCompat.getColorStateList(this, R.color.caribbean_green)

                    }
                    if(r.contains("2")){
                        findViewById<MaterialCardView>(R.id.r2).backgroundTintList = ContextCompat.getColorStateList(this, R.color.caribbean_green)
                    }
                    if(r.contains("3")){
                        findViewById<MaterialCardView>(R.id.r3).backgroundTintList = ContextCompat.getColorStateList(this, R.color.caribbean_green)
                    }
                    if(r.contains("4")){
                        findViewById<MaterialCardView>(R.id.r4).backgroundTintList = ContextCompat.getColorStateList(this, R.color.caribbean_green)
                    }

                    confirm = findViewById(R.id.confirm)
                    progressBar = findViewById(R.id.progress)
                    progressBar.progress = (index +1)*20

                    confirm.setOnClickListener {
                        index++
                        myRep.text = "Voir ma réponse"
                        mr = false

                        myRep.setOnClickListener {
                            findViewById<MaterialCardView>(R.id.r1).backgroundTintList = ContextCompat.getColorStateList(this, R.color.quizz_item_selector)
                            findViewById<MaterialCardView>(R.id.r2).backgroundTintList = ContextCompat.getColorStateList(this, R.color.quizz_item_selector)
                            findViewById<MaterialCardView>(R.id.r3).backgroundTintList = ContextCompat.getColorStateList(this, R.color.quizz_item_selector)
                            findViewById<MaterialCardView>(R.id.r4).backgroundTintList = ContextCompat.getColorStateList(this, R.color.quizz_item_selector)

                            if(!mr){
                                val rep = responses[index]
                                if(rep.contains("1")) findViewById<MaterialCardView>(R.id.r1).backgroundTintList = ContextCompat.getColorStateList(this, R.color.my_answer)
                                if(rep.contains("2")) findViewById<MaterialCardView>(R.id.r2).backgroundTintList = ContextCompat.getColorStateList(this, R.color.my_answer)
                                if(rep.contains("3")) findViewById<MaterialCardView>(R.id.r3).backgroundTintList = ContextCompat.getColorStateList(this, R.color.my_answer)
                                if(rep.contains("4")) findViewById<MaterialCardView>(R.id.r4).backgroundTintList = ContextCompat.getColorStateList(this, R.color.my_answer)
                                mr = true

                                myRep.text = "Voir correction"
                            }
                            else{
                                val rep = array[index][5]
                                if(r.contains("1")){
                                    findViewById<MaterialCardView>(R.id.r1).backgroundTintList = ContextCompat.getColorStateList(this, R.color.caribbean_green)

                                }
                                if(rep.contains("2")){
                                    findViewById<MaterialCardView>(R.id.r2).backgroundTintList = ContextCompat.getColorStateList(this, R.color.caribbean_green)
                                }
                                if(rep.contains("3")){
                                    findViewById<MaterialCardView>(R.id.r3).backgroundTintList = ContextCompat.getColorStateList(this, R.color.caribbean_green)
                                }
                                if(rep.contains("4")){
                                    findViewById<MaterialCardView>(R.id.r4).backgroundTintList = ContextCompat.getColorStateList(this, R.color.caribbean_green)
                                }
                                myRep.text = "Voir ma réponse"
                                mr = false
                            }
                        }
                        if (index < 5) {

                            findViewById<MaterialCardView>(R.id.r1).backgroundTintList = ContextCompat.getColorStateList(this, R.color.quizz_item_selector)
                            findViewById<MaterialCardView>(R.id.r2).backgroundTintList = ContextCompat.getColorStateList(this, R.color.quizz_item_selector)
                            findViewById<MaterialCardView>(R.id.r3).backgroundTintList = ContextCompat.getColorStateList(this, R.color.quizz_item_selector)
                            findViewById<MaterialCardView>(R.id.r4).backgroundTintList = ContextCompat.getColorStateList(this, R.color.quizz_item_selector)

                            progressBar.progress = (index + 1) * 20
                            findViewById<TextView>(R.id.quizz_sub).text = "QCM ${index + 1}/5"
                            findViewById<TextView>(R.id.question).text = array[index][0]
                            findViewById<TextView>(R.id.r1txt).text = array[index][1]
                            findViewById<TextView>(R.id.r2txt).text = array[index][2]
                            findViewById<TextView>(R.id.r3txt).text = array[index][3]
                            findViewById<TextView>(R.id.r4txt).text = array[index][4]

                            val re = array[index][5]
                            if(re.contains("1")){

                                findViewById<MaterialCardView>(R.id.r1).backgroundTintList = ContextCompat.getColorStateList(this, R.color.caribbean_green)

                            }
                            if(re.contains("2")){
                                findViewById<MaterialCardView>(R.id.r2).backgroundTintList = ContextCompat.getColorStateList(this, R.color.caribbean_green)
                            }
                            if(re.contains("3")){
                                findViewById<MaterialCardView>(R.id.r3).backgroundTintList = ContextCompat.getColorStateList(this, R.color.caribbean_green)
                            }
                            if(re.contains("4")){
                                findViewById<MaterialCardView>(R.id.r4).backgroundTintList = ContextCompat.getColorStateList(this, R.color.caribbean_green)
                            }
                            if(index == 4){
                                confirm.text = "Quitter"
                                confirm.backgroundTintList = ContextCompat.getColorStateList(this,R.color.quitter)
                            }
                        }
                        else{
                            finish()
                        }
                    }
                }
            }
        }



    }
    private fun init(){
        setContentView(R.layout.activity_quizz)
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        val resources: Resources = resources
        val chp:String = intent.getStringExtra("chp").toString()
        obtainTypedArray = when (chp) {
            "homme" -> resources.obtainTypedArray(R.array.homme)
            "femme" -> resources.obtainTypedArray(R.array.femme)
            "proc" -> resources.obtainTypedArray(R.array.procreation)
            "brassage" -> resources.obtainTypedArray(R.array.brassage)
            "gen_hum" -> resources.obtainTypedArray(R.array.humaine)
            "tissu" -> resources.obtainTypedArray(R.array.tissus)
            "reflexe" -> resources.obtainTypedArray(R.array.reflexe)
            "muscle" -> resources.obtainTypedArray(R.array.muscle)
            "pression" -> resources.obtainTypedArray(R.array.pression)
            "hygiene" -> resources.obtainTypedArray(R.array.hygiene)
            "soi" -> resources.obtainTypedArray(R.array.soi)
            "dysfonc" -> resources.obtainTypedArray(R.array.dysfonc)
            else -> { // Note the block
                resources.obtainTypedArray(R.array.homme)
            }
        }
        val length = obtainTypedArray.length()
        for (i in 0 until length) {
            val a = resources.getStringArray(obtainTypedArray.getResourceId(i, 0))

            array.add(a.toList() as ArrayList<String>)
        }
        array.shuffle()
        title = when (chp) {
            "homme" -> getResources().getString(R.string.chp_homme)
            "femme" -> getResources().getString(R.string.chp_femme)
            "proc" -> getResources().getString(R.string.chp_proc)
            "brassage" -> getResources().getString(R.string.chp_brassage)
            "gen_hum" -> getResources().getString(R.string.chp_gen_hum)
            "tissu" -> getResources().getString(R.string.chp_tissu)
            "reflexe" -> getResources().getString(R.string.chp_reflexe)
            "muscle" -> getResources().getString(R.string.chp_muscle)
            "pression" -> getResources().getString(R.string.chp_pression)
            "hygiene" -> getResources().getString(R.string.chp_hygiene)
            "soi" -> getResources().getString(R.string.chp_soi)
            "dysfonc" -> getResources().getString(R.string.chp_dysfonc)
            else -> { // Note the block
                getResources().getString(R.string.chp_homme)
            }
        }
        icon  = when (chp) {
            "homme" -> R.drawable.ic_homme
            "femme" -> R.drawable.ic_femme
            "proc" -> R.drawable.ic_proc
            "brassage" -> R.drawable.ic_brass
            "gen_hum" -> R.drawable.ic_gen_hum
            "tissu" -> R.drawable.ic_gen_hum
            "reflexe" -> R.drawable.ic_reflexe
            "muscle" -> R.drawable.ic_muscle
            "pression" -> R.drawable.ic_pression
            "hygiene" -> R.drawable.ic_hygiene
            "reponse" -> R.drawable.ic_reponse
            "soi" -> R.drawable.ic_soi
            "dysfonc" -> R.drawable.ic_dysfonc
            else -> { // Note the block
                R.drawable.ic_homme
            }
        }
        findViewById<TextView>(R.id.quizz_header).text=title
        findViewById<ImageView>(R.id.chp_icon).setImageResource(icon)
        r1 = findViewById(R.id.r1)
        r2 = findViewById(R.id.r2)
        r3 = findViewById(R.id.r3)
        r4 = findViewById(R.id.r4)
        r1.setOnClickListener {
            r1.toggle()
        }
        r2.setOnClickListener {
            r2.toggle()
        }
        r3.setOnClickListener {
            r3.toggle()
        }
        r4.setOnClickListener {
            r4.toggle()
        }
        progressBar = findViewById(R.id.progress)




        progressBar.progress = (index +1)*20
        findViewById<TextView>(R.id.question).text = array[index][0]
        findViewById<TextView>(R.id.r1txt).text = array[index][1]
        findViewById<TextView>(R.id.r2txt).text = array[index][2]
        findViewById<TextView>(R.id.r3txt).text = array[index][3]
        findViewById<TextView>(R.id.r4txt).text = array[index][4]
        timerBar = findViewById(R.id.timer)
        timerBar.max=duration.toInt()*1000
        confirm = findViewById<MaterialButton>(R.id.confirm)
        initTimer(this,duration)
        startTimer()


    }



    private fun initTimer(context: Context,duration: Float){
        if(!chrono){
            timerBar.visibility = View.INVISIBLE
        }
        timer = object: CountDownTimer((duration*1000).toLong(), 200) {
            override fun onTick(p: Long) {
                timerBar.progress = p.toInt()
                checkpoint = p.toFloat()
            }

            override fun onFinish() {
                Toast.makeText(context,"Temps épuisé !",Toast.LENGTH_SHORT).show()
                confirm.performClick()
                timerBar.progress = 0
            }
        }
    }
    private fun startTimer(){
        if(chrono){
            timer.start()
        }
    }
    private fun stopTimer(){
        if(chrono){
            timer.cancel()
        }
    }

    private fun calcScore() {
        for (i in 0 until 4) {
            if (responses[i] == array[i][5]) {
                score++
            }

        }
    }
    private fun addResponse() {
        responses.add(if (r1.isChecked) "1" else "" + if (r2.isChecked) "2" else "" + if (r3.isChecked) "3" else "" + if (r4.isChecked) "4" else "")
    }
    private fun responsesPages(){
        stopTimer()
        responses.add((if (r1.isChecked) "1" else "") + (if (r2.isChecked) "2" else "") + (if (r3.isChecked) "3" else "") + (if (r4.isChecked) "4" else ""))
        index++
        progressBar.progress = (index + 1) * 20
        findViewById<TextView>(R.id.quizz_sub).text = "QCM ${index + 1}/5"
        findViewById<TextView>(R.id.question).text = array[index][0]
        findViewById<TextView>(R.id.r1txt).text = array[index][1]
        findViewById<TextView>(R.id.r2txt).text = array[index][2]
        findViewById<TextView>(R.id.r3txt).text = array[index][3]
        findViewById<TextView>(R.id.r4txt).text = array[index][4]
        r1.isChecked = false
        r2.isChecked = false
        r3.isChecked = false
        r4.isChecked = false
        initTimer(this,duration)
        startTimer()

    }

    override fun onPause() {
        super.onPause()
        stopTimer()
    }

    override fun onRestart() {
        super.onRestart()
        initTimer(this,checkpoint/1000)
        startTimer()
    }
}