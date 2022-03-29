package com.example.notasapp.activitys.ui

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.example.notasapp.activitys.ui.adapters.NotaAdapter
import com.example.notasapp.core.RestrofitBuilder
import com.example.notasapp.databinding.FragmentNotasBinding
import com.example.notasapp.models.Curso
import com.example.notasapp.models.Nota
import com.example.notasapp.models.RegisterNota
import com.example.notasapp.models.RespuestaMensaje
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
            opcionesNota(listaCursosAll[i])
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
                            listaCursosAll = response.body()!!
                            listaCursosAll.forEach {
                                var nota = listaNotasAll.find { el->el?.curso_id==it?.id }
                                it.nota = nota?.nota
                                it.nota_id = nota?.id?.toInt()
                            }
                            val adapter = NotaAdapter(requireActivity(), listaCursosAll)
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
    fun opcionesNota(curso: Curso){
        var textNota = "Agregar Nota"
        if(curso.nota!=null){
            textNota="Editar Nota"
        }
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Opciones")
        builder.setMessage("¿Qué desea hacer?")
        builder.setPositiveButton("Eliminar") { dialog, which ->
            deleteNota(curso)
        }
        builder.setNegativeButton(textNota) { dialog, which ->
            editarNota(curso)
        }

        builder.show()
    }
    fun editarNota(curso: Curso){
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(curso.nombre)

        val input = EditText(requireActivity())
        if(curso.nota!=null){
            input.setText(curso.nota!!.toString())
        }
        input.setHint("Ingrese la Nota")
        input.inputType = InputType.TYPE_CLASS_TEXT

        builder.setView(input)

        builder.setPositiveButton("Guardar") { dialog, which ->
            var m_Text = input.text.toString()
            if (m_Text.toInt()>20){
                Toast.makeText(requireActivity(),
                    "Ingrese una nota correcta!!", Toast.LENGTH_SHORT).show()
            }else{
                var nota = RegisterNota(
                    curso_id = curso.id,
                    estudiante_id = (shared.getString("estudiante_id",""))!!.toInt(),
                    nota = m_Text.toInt()
                )
                if(curso.nota!=null){
                    //update
                    val res = core.updateNotaCurso(curso.nota_id!! ,nota)
                    res.enqueue( object : Callback<Nota> {
                        override fun onResponse(call: Call<Nota>, response: Response<Nota>) {
                            if (response.code() == 201) {
                                //listaNotasAll = response.body()!!
                                Toast.makeText(requireActivity(),
                                    "Actualizado!!", Toast.LENGTH_SHORT).show()
                                getNotas()
                            }
                        }
                        override fun onFailure(call: Call<Nota>, t: Throwable) {
                            print(t.message)
                        }
                    })
                }else{
                    //register
                    val res = core.registerNotaCurso(nota)
                    res.enqueue( object : Callback<Nota> {
                        override fun onResponse(call: Call<Nota>, response: Response<Nota>) {
                            if (response.code() == 201) {
                                //listaNotasAll = response.body()!!
                                Toast.makeText(requireActivity(),
                                    "Guardado!!", Toast.LENGTH_SHORT).show()
                                getNotas()
                            }
                        }
                        override fun onFailure(call: Call<Nota>, t: Throwable) {
                            print(t.message)
                        }
                    })
                }
            }
        }
        builder.setNegativeButton("Cancelar") { dialog, which ->
            //eliminar nota

            dialog.cancel()
        }

        builder.show()
    }
    fun deleteNota(curso: Curso){
        val res = core.deleteNotaCurso(curso.nota_id!!)
        res.enqueue( object : Callback<RespuestaMensaje> {
            override fun onResponse(call: Call<RespuestaMensaje>, response: Response<RespuestaMensaje>) {
                if (response.code() == 200) {
                    var rpta = response.body()!!
                    Toast.makeText(requireActivity(),
                        rpta.msg, Toast.LENGTH_SHORT).show()
                    getNotas()
                }
            }
            override fun onFailure(call: Call<RespuestaMensaje>, t: Throwable) {
                print(t.message)
            }
        })
    }
}