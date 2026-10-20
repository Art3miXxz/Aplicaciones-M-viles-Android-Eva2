package com.example.sensor_gas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Estadisticas : AppCompatActivity() {

    private lateinit var tvusuestadisticas: TextView
    private lateinit var tv3est: TextView // Campo donde se mostrará el nombre del usuario
    private lateinit var tv6est: TextView // Activación de sensor
    private lateinit var tv9est: TextView // Hora
    private lateinit var btnvolest: Button

    private var usu: Usuario? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_estadisticas)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvusuestadisticas = findViewById(R.id.tvusuestadisticas)
        tv3est = findViewById(R.id.tv3est)
        tv6est = findViewById(R.id.tv6est)
        tv9est = findViewById(R.id.tv9est)
        btnvolest = findViewById(R.id.btnvolest)

        usu = intent.getSerializableExtra("usu") as? Usuario

        tvusuestadisticas.setText("Bienvenido(a) : " + usu?.nombre)
        tv3est.setText(usu?.nombre)


        volver()
        desactivarFlecha()
    }

    private fun volver() {
        btnvolest.setOnClickListener {
            val intent = Intent(this@Estadisticas, Menu::class.java)
            intent.putExtra("usu", usu)
            startActivity(intent)
        }
    }

    private fun desactivarFlecha() {
        onBackPressedDispatcher.addCallback(this) {}
    }
}