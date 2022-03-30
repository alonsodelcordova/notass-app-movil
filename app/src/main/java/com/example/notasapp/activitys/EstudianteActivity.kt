package com.example.notasapp.activitys

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.notasapp.R
import com.example.notasapp.databinding.ActivityEstudianteBinding
import com.google.android.material.navigation.NavigationView

class EstudianteActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityEstudianteBinding
    private  lateinit var shared: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEstudianteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarEstudiante.toolbar)
        // change email user
        shared = getSharedPreferences("data", Context.MODE_PRIVATE)
        if(shared.getString("user_name","")!=""){
            val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
            val headerView = navigationView.getHeaderView(0)
            val email_txt = headerView.findViewById<View>(R.id.textNavHeaderEmail) as TextView
            email_txt.text = shared.getString("user_name","")

            val name_txt = headerView.findViewById<View>(R.id.textNavHeaderNames) as TextView
            name_txt.text = (shared.getString("estudiante_nombres","") +" " + shared.getString("estudiante_apellidos","")).toUpperCase()
            val codigo_txt = headerView.findViewById<View>(R.id.txtNavHeaderCodigo) as TextView
            codigo_txt.text ="CU: "+ shared.getString("estudiante_codigo","")
        }


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_estudiante)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_inicio,
                R.id.nav_notas,
                R.id.nav_informe,
                R.id.nav_historial,
                R.id.nav_ubicanos
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.estudiante, menu)

        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_settings->{
                val editor = shared.edit()
                editor.clear()
                editor.commit()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_estudiante)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}