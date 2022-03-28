package com.example.notasapp.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.notasapp.R
import com.example.notasapp.core.RestrofitBuilder
import com.example.notasapp.models.Escuela
import com.example.notasapp.models.Facultad
import com.example.notasapp.models.Universidad
import kotlinx.android.synthetic.main.activity_register.*
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
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
    }
    fun getUniversidades(){
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
                }
                override fun onFailure(call: Call<List<Universidad>>, t: Throwable) {
                    print(t.message)
                }
            }
        )
    }

    fun getFacultades(i:Int){
        val uni = listaUniversidad[i]
        val res = RestrofitBuilder().getFacultades(uni.id.toString())
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
                }

                override fun onFailure(call: Call<List<Facultad>>, t: Throwable) {
                    print(t.message)
                }
            }
        )
    }
    fun getEscuelas(i:Int){
        val fac = listaFacultades[i]
        val res = RestrofitBuilder().getEscuelas(fac.id.toString())
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
                }

                override fun onFailure(call: Call<List<Escuela>>, t: Throwable) {
                    print(t.message)
                }
            }
        )
    }
}