package com.example.notasapp.activitys

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notasapp.R
import com.example.notasapp.core.RestrofitBuilder
import com.example.notasapp.models.ResLogin
import com.example.notasapp.models.Usuario
import kotlinx.android.synthetic.main.login_inicio.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(){
    private  lateinit var core: RestrofitBuilder
    private  lateinit var spinner: View
    private  lateinit var shared:SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_inicio)
        core = RestrofitBuilder()
        spinner = loader_login as View
        shared = getSharedPreferences("data", Context.MODE_PRIVATE)
        if(shared.getString("user_name","")!=""){
            val intent = Intent(this, EstudianteActivity::class.java)
            startActivity(intent)
        }

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
        if(user.name==""||user.password==""){
            Toast.makeText(this@LoginActivity,"Complete los campos",Toast.LENGTH_LONG).show()
            return
        }
        spinner.visibility = View.VISIBLE
        val res = core.login(user)
        res.enqueue( object : Callback<ResLogin> {
                override fun onResponse(call: Call<ResLogin>, response: Response<ResLogin>) {
                    if (response.code() == 200) {
                        var body = response.body()!!
                        // save credentials
                        val editor = shared.edit()
                        editor.putString("user_name",body.user.name)
                        editor.putString("user_tipo",body.user.tipo)
                        editor.putString("user_id",body.user.id)
                        editor.putString("estudiante_id",body.estudiante?.id.toString())
                        editor.putString("estudiante_nombres",body.estudiante?.nombres.toString())
                        editor.putString("estudiante_apellidos",body.estudiante?.apellidos.toString())
                        editor.putString("estudiante_codigo",body.estudiante?.codigo.toString())
                        editor.putString("escuela_id",body.estudiante?.escuela_id.toString())
                        editor.putString("escuela_ciclos",body.estudiante?.escuela?.ciclos.toString())
                        editor.putString("escuela_cre_obl",body.estudiante?.escuela?.cre_obli.toString())
                        editor.putString("escuela_cre_ele",body.estudiante?.escuela?.cre_ele.toString())
                        editor.commit()

                        val intent = Intent(this@LoginActivity, EstudianteActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(this@LoginActivity,"Success",Toast.LENGTH_LONG).show()
                        txtUser.text.clear()
                        txtContraseña.text.clear()

                    }
                    spinner.visibility=View.GONE
                }
                override fun onFailure(call: Call<ResLogin>, t: Throwable) {
                    print(t.message)
                    spinner.visibility=View.GONE
                    Toast.makeText(this@LoginActivity,"Error",Toast.LENGTH_LONG).show()
                }
            }
        )
    }
}