package com.example.notasapp.activitys.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.notasapp.R
import com.example.notasapp.models.Curso
import kotlinx.android.synthetic.main.activity_item_nota.view.*

class NotaAdapter(
    private val nContext: Context,
    private val listNotas:List<Curso>
): ArrayAdapter<Curso>(
    nContext,0,listNotas
)  {
    @SuppressLint("ResourceAsColor")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(nContext).inflate(
            R.layout.activity_item_nota, parent, false
        )
        val curso = listNotas[position]
        layout.txtItemNotaNombre.text= curso.nombre
        layout.txtItemNotaTipo.text="Tipo:"+curso.tipo
        layout.txtItemNotaCreditos.text = "Creditos: "+curso.creditos
        if(curso.nota!=null){
            layout.txtItemNotaNota.text = "Nota: " + curso.nota!!
            if( curso.nota!! > 10){
                layout.txtItemNotaNota.setTextColor(R.color.azul)
            }
        }else{
            //layout.txtItemNotaNota.visibility= View.GONE
            layout.txtItemNotaNota.text="Agregar"
        }

        return  layout
    }
}
