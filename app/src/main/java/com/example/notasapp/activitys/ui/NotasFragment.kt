package com.example.notasapp.activitys.ui

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.example.notasapp.R
import com.example.notasapp.activitys.ui.adapters.NotaAdapter
import com.example.notasapp.core.RestrofitBuilder
import com.example.notasapp.databinding.FragmentNotasBinding
import com.example.notasapp.models.Curso
import com.example.notasapp.models.Escuela
import com.example.notasapp.models.Nota
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotasFragment : Fragment() {
    private var _binding: FragmentNotasBinding? = null
    private val binding get() = _binding!!
    private  var listaCursosAll = emptyList<Curso>()
    private var listaNotasAll = emptyList<Nota>()
    private  lateinit var shared: SharedPreferences
    private  lateinit var core: RestrofitBuilder
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotasBinding.inflate(inflater, container, false)
        val root: View = binding.root
        core = RestrofitBuilder()
        shared =  requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE)
        getNotas()
        binding.listNotasContent.setOnItemClickListener { adapterView, view, i, l ->
            val builder = AlertDialog.Builder(requireActivity())
            builder.setTitle("Opciones")
            builder.setMessage("¿Qué desea hacer?")
            builder.setPositiveButton("Eliminar") { dialog, which ->
                Toast.makeText(requireActivity(),
                    "Eliminado!!", Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("Editar") { dialog, which ->
                Toast.makeText(requireActivity(),
                    "Editado!!", Toast.LENGTH_SHORT).show()
            }
            builder.show()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun getCursos(){
        if(shared.getString("user_name","")!=""){
            //get Notas Endpoint
            val res = shared.getString("escuela_id","")?.let { core.getCursosEscuela(it) }
            res?.enqueue(
                object : Callback<List<Curso>> {
                    override fun onResponse(call: Call<List<Curso>>, response: Response<List<Curso>>) {
                        if (response.code() == 200) {
                            val lista = response.body()!!
                            lista.forEach {
                                it.nota = listaNotasAll.find { el->el?.curso_id==it?.id }?.nota
                            }
                            val adapter = NotaAdapter(requireActivity(), lista)
                            binding.listNotasContent.adapter = adapter
                        }
                    }

                    override fun onFailure(call: Call<List<Curso>>, t: Throwable) {
                        print(t.message)
                    }
                }
            )

        }

    }
    fun getNotas(){
        if(shared.getString("user_name","")!=""){
            //get Notas Endpoint
            val res = shared.getString("estudiante_id","")?.let { core.getNotasEstudiante(it) }
            res?.enqueue(
                object : Callback<List<Nota>> {
                    override fun onResponse(call: Call<List<Nota>>, response: Response<List<Nota>>) {
                        if (response.code() == 200) {
                            listaNotasAll = response.body()!!
                            getCursos()
                        }
                    }
                    override fun onFailure(call: Call<List<Nota>>, t: Throwable) {
                        print(t.message)
                    }
                }
            )

        }

    }
}