package com.example.notasapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.notasapp.core.RestrofitBuilder
import com.example.notasapp.models.Universidad
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val res = RestrofitBuilder().createGetUnis()
        res.enqueue(
            object : Callback<List<Universidad>> {
                override fun onResponse(call: Call<List<Universidad>>, response: Response<List<Universidad>>) {
                    if (response.code() == 200) {
                        val listM = response.body()!!
                        idTexto.text=listM[0].nombre
                    }
                }

                override fun onFailure(call: Call<List<Universidad>>, t: Throwable) {
                    idTexto.text= t.message
                }
            }
        )
    }
}