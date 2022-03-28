package com.example.notasapp.activitys

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notasapp.R
import com.example.notasapp.core.RestrofitBuilder
import com.example.notasapp.models.ResLogin
import com.example.notasapp.models.Universidad
import com.example.notasapp.models.Usuario
import kotlinx.android.synthetic.main.login_inicio.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(){
    private  lateinit var core: RestrofitBuilder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_inicio)
        core = RestrofitBuilder()

        btnIniciarSesion.setOnClickListener {
            login()
        }
        btnRegistrarUsuario.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    fun login(){
        var user= Usuario(
            name = txtUser.text.toString(),
            password = txtContraseña.text.toString()
        )
        println(user)
        val res = core.login(user)
        res.enqueue(
            object : Callback<ResLogin> {
                override fun onResponse(call: Call<ResLogin>, response: Response<ResLogin>) {
                    if (response.code() == 200) {
                        //println(response.body()!!.user.name)
                        //println("asdsda--1")
                        val intent = Intent(this@LoginActivity, EstudianteActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(this@LoginActivity,"Success",Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<ResLogin>, t: Throwable) {
                    print(t.message)
                    Toast.makeText(this@LoginActivity,"Error",Toast.LENGTH_LONG).show()
                }
            }
        )
    }
}