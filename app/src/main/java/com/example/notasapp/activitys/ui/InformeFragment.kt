package com.example.notasapp.activitys.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.notasapp.R
import com.example.notasapp.core.RestrofitBuilder
import com.example.notasapp.databinding.FragmentInformeBinding
import com.example.notasapp.models.Curso
import com.example.notasapp.models.DatosEstudiante
import com.example.notasapp.models.Nota
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InformeFragment : Fragment() {
    private var _binding: FragmentInformeBinding? = null
    private val binding get() = _binding!!
    private  var listaCursosAll = emptyList<Curso>()
    private var listaNotasAll = emptyList<Nota>()
    private  lateinit var shared: SharedPreferences
    private  lateinit var core: RestrofitBuilder
    private  lateinit var spinner: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInformeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        spinner = root.findViewById(R.id.loader_informe)!!
        core = RestrofitBuilder()
        shared =  requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE)
        getNotas()
        return root
    }
    fun getNotas(){
        //get Notas Endpoint
        val res = shared.getString("estudiante_id","")?.let { core.getNotasEstudiante(it) }
        res?.enqueue( object : Callback<List<Nota>> {
            override fun onResponse(call: Call<List<Nota>>, response: Response<List<Nota>>) {
                if (response.code() == 200) {
                    listaNotasAll = response.body()!!
                    getCursos()
                }
            }
            override fun onFailure(call: Call<List<Nota>>, t: Throwable) {
                print(t.message)
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
                        getDatos()
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

    fun showData(){
        var t_creditos_O=0
        var t_creditos_O_apr=0
        var t_creditos_E=0
        var t_creditos_E_apr=0
        var s_notas=0
        var promedio=0
        listaCursosAll.forEach {
            if(it?.nota!=null){
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
            }
        }
        if(t_creditos_E+t_creditos_O>0){
            promedio= (s_notas)/(t_creditos_E+t_creditos_O)
        }
        val creObliGra = shared.getString("escuela_cre_obl","")!!.toInt()
        val creElecGra = shared.getString("escuela_cre_ele","")!!.toInt()
        binding.txtInformeNumCreObliGraduacion.text = creObliGra.toString()
        binding.txtInformeNumCreElecGraduacion.text = creElecGra.toString()
        binding.txtInformeNumCreTotalGraduacion.text = (creObliGra+creElecGra).toString()

        binding.txtInformeNumCreObliAprobados.text = t_creditos_O_apr.toString()
        binding.txtInformeNumCreElecAprobados.text = t_creditos_E_apr.toString()
        binding.txtInformeNumCreTotalAprobados.text = (t_creditos_O_apr+t_creditos_E_apr).toString()

        val obDebe = creObliGra-t_creditos_O_apr
        val elDebe = creElecGra-t_creditos_E_apr
        binding.txtInformeNumCreObliDebe.text = (obDebe).toString()
        binding.txtInformeNumCreElecDebe.text = (elDebe).toString()
        binding.txtInformeNumCreTotalDebe.text = (obDebe+elDebe).toString()


        binding.txtInformeEstudiante.text = shared.getString("estudiante_codigo","")+
                " - " + shared.getString("estudiante_apellidos","")+", "+
                shared.getString("estudiante_nombres","")
        binding.txtInformeResumen.text = binding.txtInformeResumen.text.toString() + "\nSu promedio total es: ${promedio}"

    }
    fun getDatos(){
        if(shared.getString("estudiante_id","")!=""){
            spinner.visibility=View.VISIBLE
            val res = core.getDatosEstudiante(shared.getString("estudiante_id","")!!)
            res.enqueue( object : Callback<DatosEstudiante> {
                override fun onResponse(call: Call<DatosEstudiante>, response: Response<DatosEstudiante>) {
                    if (response.code() == 200) {
                        var rpta = response.body()!!
                        binding.txtInformeResumen.text = "Universidad: ${rpta.universidad}" +
                                "\nFacultad: ${rpta.facultad}" +
                                "\nEscuela: ${rpta.escuela}"
                        showData()
                    }
                    spinner.visibility=View.GONE
                }
                override fun onFailure(call: Call<DatosEstudiante>, t: Throwable) {
                    print(t.message)
                    spinner.visibility=View.GONE
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}