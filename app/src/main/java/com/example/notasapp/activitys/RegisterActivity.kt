package com.example.notasapp.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.notasapp.R
import com.example.notasapp.core.RestrofitBuilder
import com.example.notasapp.models.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.login_inicio.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var   listaUniversidad:List<Universidad>
    private lateinit var   listaFacultades:List<Facultad>
    private lateinit var   listaEscuelas:List<Escuela>
    private  lateinit var  sp_uni:Spinner
    private  lateinit var  sp_fac:Spinner
    private  lateinit var  sp_esc:Spinner
    private  lateinit var core:RestrofitBuilder
    private  lateinit var spinner: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        spinner = loader_register as View
        //iniciarlizando variables
        sp_fac=  findViewById(R.id.spnRegisterFacultad)
        sp_uni=  findViewById(R.id.spnRegisterUniversidad)
        sp_esc=  findViewById(R.id.spnRegisterEscuela)
        core = RestrofitBuilder()

        getUniversidades()

        sp_uni.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                getFacultades(position)
            }
        }
        sp_fac.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                getEscuelas(position)
            }
        }
        btnRegistrarLoginBack.setOnClickListener {
            finish()
        }
        btnRegistrar.setOnClickListener {
            registerUsuario()
        }
    }
    fun getUniversidades(){
        spinner.visibility = View.VISIBLE
        val res = core.createGetUnis()
        res.enqueue(
            object : Callback<List<Universidad>> {
                override fun onResponse(call: Call<List<Universidad>>, response: Response<List<Universidad>>) {
                    if (response.code() == 200) {
                        val lista = arrayListOf<String>()
                        listaUniversidad = response.body()!!
                        listaUniversidad.forEach {
                            lista.add( it?.nombre)
                        }
                        var adapter = ArrayAdapter(
                            this@RegisterActivity,
                            android.R.layout.simple_spinner_item,
                            lista
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        sp_uni.adapter = adapter
                    }
                    spinner.visibility = View.GONE
                }
                override fun onFailure(call: Call<List<Universidad>>, t: Throwable) {
                    print(t.message)
                    spinner.visibility = View.GONE
                }
            }
        )
    }

    fun getFacultades(i:Int){
        spinner.visibility = View.VISIBLE
        val uni = listaUniversidad[i]
        //limpiar escuelas
        listaEscuelas=arrayListOf()
        var adapter = ArrayAdapter(
            this@RegisterActivity,
            android.R.layout.simple_spinner_item,
            listaEscuelas
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_esc.adapter = adapter

        val res = core.getFacultades(uni.id.toString())
        res.enqueue(
            object : Callback<List<Facultad>> {
                override fun onResponse(call: Call<List<Facultad>>, response: Response<List<Facultad>>) {
                    if (response.code() == 200) {
                        val lista = arrayListOf<String>()
                        listaFacultades = response.body()!!
                        listaFacultades.forEach {
                            lista.add( it?.nombre)
                        }
                        var adapter = ArrayAdapter(
                            this@RegisterActivity,
                            android.R.layout.simple_spinner_item,
                            lista
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        sp_fac.adapter = adapter
                    }
                    spinner.visibility = View.GONE
                }

                override fun onFailure(call: Call<List<Facultad>>, t: Throwable) {
                    print(t.message)
                    spinner.visibility = View.GONE
                }
            }
        )
    }
    fun getEscuelas(i:Int){
        spinner.visibility = View.VISIBLE
        val fac = listaFacultades[i]
        val res = core.getEscuelas(fac.id.toString())
        res.enqueue(
            object : Callback<List<Escuela>> {
                override fun onResponse(call: Call<List<Escuela>>, response: Response<List<Escuela>>) {
                    if (response.code() == 200) {
                        val lista = arrayListOf<String>()
                        listaEscuelas = response.body()!!
                        listaEscuelas.forEach {
                            lista.add( it?.nombre)
                        }
                        var adapter = ArrayAdapter(
                            this@RegisterActivity,
                            android.R.layout.simple_spinner_item,
                            lista
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        sp_esc.adapter = adapter
                    }
                    spinner.visibility = View.GONE
                }

                override fun onFailure(call: Call<List<Escuela>>, t: Throwable) {
                    print(t.message)
                    spinner.visibility = View.GONE
                }
            }
        )
    }
    fun registerUsuario(){
        spinner.visibility = View.VISIBLE
        var escuela = listaEscuelas[spnRegisterEscuela.selectedItemPosition]
        var register = RegisterEstudiante(
            nombres = txtViewNombres.text.toString(),
            apellidos = txtViewApellidos.text.toString(),
            codigo = txtViewCodigo.text.toString(),
            direccion = txtViewDireccion.text.toString(),
            dni = txtViewDni.text.toString(),
            email = txtViewEmail.text.toString(),
            escuela_id = escuela.id.toString()
        )
        val res = core.registrarEstudiante(register)
        res.enqueue(
            object : Callback<Estudiante> {
                override fun onResponse(call: Call<Estudiante>, response: Response<Estudiante>) {
                    if (response.code() == 201) {
                        val builder = AlertDialog.Builder(this@RegisterActivity)
                        builder.setTitle("Estudiante Registrado!!")
                        builder.setMessage("Usuario:"+register.email+"\nContraseña:"+register.dni)
                        builder.setPositiveButton("Ok") { dialog, which ->
                           this@RegisterActivity.finish()
                        }
                        builder.show()

                    }
                    spinner.visibility = View.GONE
                }
                override fun onFailure(call: Call<Estudiante>, t: Throwable) {
                    print(t.message)
                    spinner.visibility = View.GONE
                    Toast.makeText(this@RegisterActivity,"Error", Toast.LENGTH_LONG).show()
                }
            }
        )
    }
}