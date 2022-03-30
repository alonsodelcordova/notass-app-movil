package com.example.notasapp.activitys.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.notasapp.R
import com.example.notasapp.activitys.ui.adapters.NotaAdapter
import com.example.notasapp.core.RestrofitBuilder
import com.example.notasapp.databinding.FragmentHistorialBinding
import com.example.notasapp.models.Curso
import com.example.notasapp.models.Nota
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.roundToInt

class historial : Fragment() {
    private var _binding: FragmentHistorialBinding? = null
    private val binding get() = _binding!!
    private  var listaCursosAll = emptyList<Curso>()
    private  var listaCursos = arrayListOf<Curso>()
    private var listaNotasAll = emptyList<Nota>()
    private  lateinit var shared: SharedPreferences
    private  lateinit var core: RestrofitBuilder
    private  lateinit var  sp_ciclos: Spinner
    private var ciclo:Number = 0
    private  lateinit var spinner: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistorialBinding.inflate(inflater, container, false)
        val root: View = binding.root
        spinner = root.findViewById(R.id.loader_historia)!!
        core = RestrofitBuilder()
        sp_ciclos= binding.spHistorialCiclos
        shared =  requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE)
        getCiclos()
        getNotas()
        return root
    }

    fun getNotas(){
        //get Notas Endpoint
        spinner.visibility=View.VISIBLE
        val res = shared.getString("estudiante_id","")?.let { core.getNotasEstudiante(it) }
        res?.enqueue( object : Callback<List<Nota>> {
            override fun onResponse(call: Call<List<Nota>>, response: Response<List<Nota>>) {
                if (response.code() == 200) {
                    listaNotasAll = response.body()!!
                    getCursos()
                }
                spinner.visibility=View.GONE
            }
            override fun onFailure(call: Call<List<Nota>>, t: Throwable) {
                print(t.message)
                spinner.visibility=View.GONE
            }
        })
    }
    fun getCursos(){
        if(shared.getString("user_name","")!=""){
            //get Notas Endpoint
            spinner.visibility=View.VISIBLE
            val res = shared.getString("escuela_id","")?.let { core.getCursosEscuela(it) }
            res?.enqueue( object : Callback<List<Curso>> {
                override fun onResponse(call: Call<List<Curso>>, response: Response<List<Curso>>) {
                    if (response.code() == 200) {
                        listaCursosAll = response.body()!!
                        listaCursosAll.forEach {
                            var nota = listaNotasAll.find { el->el.curso_id==it.id }
                            it.nota = nota?.nota
                            it.nota_id = nota?.id?.toInt()
                        }
                        showListCursos(ciclo)
                    }
                    spinner.visibility=View.GONE
                }
                override fun onFailure(call: Call<List<Curso>>, t: Throwable) {
                    print(t.message)
                    spinner.visibility=View.GONE
                }
            })
        }
    }

    fun showListCursos(ciclo:Number){
        listaCursos= arrayListOf()
        var t_creditos_O=0
        var t_creditos_O_apr=0
        var t_creditos_E=0
        var t_creditos_E_apr=0
        var s_notas=0
        var promedio=0.00
        listaCursosAll.forEach {
            if(it.ciclo.toString()==ciclo.toString() && it?.nota!=null){
                if(it.tipo=="O"){
                    if(it.nota!!>10){
                        t_creditos_O_apr+= it.creditos.toInt()
                    }
                    t_creditos_O += it.creditos.toInt()
                }else{
                    if(it.nota!!>10){
                        t_creditos_E_apr+= it.creditos.toInt()
                    }
                    t_creditos_E += it.creditos.toInt()
                }
                s_notas += (it.nota!!.toInt()*it.creditos.toInt())
                listaCursos.add(it)
            }
        }
        if(listaCursos.size==0){
            Toast.makeText(requireActivity(),
                "Vacio!!", Toast.LENGTH_SHORT).show()
        }else{
            if(t_creditos_E+t_creditos_O>0){
                promedio= s_notas.toDouble()/(t_creditos_E+t_creditos_O).toDouble()
                promedio = (promedio * 100.0).roundToInt() / 100.0
            }
        }
        val adapter = NotaAdapter(requireActivity(), listaCursos)
        binding.listHistorialCursos.adapter = adapter
        binding.txtHistorialResumen.text = ("Nº Cursos: ${listaCursos.size} " +
                "\nPromedio del Ciclo: ${promedio}" +
                "\nCreditos Obigatorios Aprobados: ${t_creditos_O_apr}" +
                "\nCreditos Electivos Aprobados: ${t_creditos_E_apr}")
    }

    fun  getCiclos(){
        if(shared.getString("escuela_ciclos","")!=""){
            val ciclos = shared.getString("escuela_ciclos","")
            //spFrNotasCiclos
            val lista = arrayListOf<Int>()
            for (i in 1..(ciclos!!.toInt())) {
                lista.add(i)
            }
            var adapter = ArrayAdapter(
                requireActivity(),
                android.R.layout.simple_spinner_item,
                lista
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            sp_ciclos.adapter = adapter
        }
        sp_ciclos.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                ciclo=position+1
                showListCursos(ciclo)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}