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

class Acciones : AppCompatActivity() {

    private lateinit var tvusuacciones: TextView
    private lateinit var btnvolacc: Button

    private var usu: Usuario? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_acciones)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvusuacciones = findViewById(R.id.tvusuacciones)
        btnvolacc = findViewById(R.id.btnvolacc)

        usu = intent.getSerializableExtra("usu") as? Usuario

        tvusuacciones.setText("Bienvenido(a) : " + usu?.nombre)

        volver()
        desactivarFlecha()
    }

    private fun volver() {
        btnvolacc.setOnClickListener {
            var intent = Intent(this@Acciones, Menu::class.java)
            intent.putExtra("usu", usu)
            startActivity(intent)
        }
    }

    private fun desactivarFlecha() {
        onBackPressedDispatcher.addCallback(this) {}
    }
}