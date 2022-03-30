package com.example.notasapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.notasapp.activitys.LoginActivity
import com.example.notasapp.activitys.RegisterActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private  lateinit var shared: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        shared = getSharedPreferences("data", Context.MODE_PRIVATE)
        if(shared.getString("user_name","")!=""){
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
        }
        btnMainInicio.setOnClickListener {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)

        }

    }
}


/*val builder = AlertDialog.Builder(this)
       builder.setTitle("Mensaje de Alerta")
       builder.setMessage("pregunta")
       builder.setPositiveButton("SI") { dialog, which ->
           Toast.makeText(applicationContext,
               "si", Toast.LENGTH_SHORT).show()
       }
       builder.setNegativeButton("NO") { dialog, which ->
           Toast.makeText(applicationContext,
               "No", Toast.LENGTH_SHORT).show()
       }
       builder.show()*/