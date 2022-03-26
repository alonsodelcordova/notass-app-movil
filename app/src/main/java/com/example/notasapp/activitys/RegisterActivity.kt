package com.example.notasapp.activitys

import android.os.Bundle
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.notasapp.R
import com.example.notasapp.core.RestrofitBuilder
import com.example.notasapp.models.Universidad
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity
    : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registrar_usuario)
        val res = RestrofitBuilder().createGetUnis()
        res.enqueue(
            object : Callback<List<Universidad>> {
                override fun onResponse(call: Call<List<Universidad>>, response: Response<List<Universidad>>) {
                    if (response.code() == 200) {
                        val listM = response.body()!!
                        /*Spinner s = (Spinner) findViewById(R.id.Spinner01);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, arraySpinner);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        s.setAdapter(adapter);*/
                    }
                }

                override fun onFailure(call: Call<List<Universidad>>, t: Throwable) {
                    idTexto.text= t.message
                }
            }
        )
    }
}