package com.example.notasapp.activitys.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.notasapp.R
import com.example.notasapp.activitys.EstudianteActivity
import com.example.notasapp.core.RestrofitBuilder
import com.example.notasapp.databinding.ActivityLoaderBinding
import com.example.notasapp.databinding.FragmentInicioBinding
import com.example.notasapp.models.DatosEstudiante
import kotlinx.android.synthetic.main.login_inicio.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InicioFragment : Fragment() {
    private var _binding: FragmentInicioBinding? = null
    private val binding get() = _binding!!
    private  lateinit var core: RestrofitBuilder
    private  lateinit var shared: SharedPreferences
    private  lateinit var spinner: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInicioBinding.inflate(inflater, container, false)
        val root: View = binding.root
        spinner = root.findViewById(R.id.loader_inicio)!!
        core = RestrofitBuilder()
        shared =  requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE)
        getDatos()
        binding.txtInicioNombre.text=("Bienvenido "+shared.getString("estudiante_nombres","") +" " + shared.getString("estudiante_apellidos","")).toUpperCase()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun getDatos(){
        if(shared.getString("estudiante_id","")!=""){
            spinner.visibility=View.VISIBLE
            val res = core.getDatosEstudiante(shared.getString("estudiante_id","")!!)
            res.enqueue( object : Callback<DatosEstudiante> {
                override fun onResponse(call: Call<DatosEstudiante>, response: Response<DatosEstudiante>) {
                    if (response.code() == 200) {
                        var rpta = response.body()!!
                        binding.txtInicioEscuela.text=rpta.escuela
                        binding.txtInicioFacultad.text=rpta.facultad
                        binding.txtInicioUniversidad.text=rpta.universidad
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
}