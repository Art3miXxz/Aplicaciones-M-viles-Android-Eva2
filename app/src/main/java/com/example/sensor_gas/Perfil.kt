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

class Perfil : AppCompatActivity() {

    private lateinit var tvusuperfil: TextView
    private lateinit var tvrutper: TextView
    private lateinit var tvnomper: TextView
    private lateinit var tvapeper: TextView
    private lateinit var tvcorper: TextView
    private lateinit var tvcarper: TextView
    private lateinit var tvconper: TextView
    private lateinit var btnvolper: Button

    private var usu: Usuario? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvusuperfil = findViewById(R.id.tvusuperfil)
        tvrutper = findViewById(R.id.tvrutper)
        tvnomper = findViewById(R.id.tvnomper)
        tvapeper = findViewById(R.id.tvapeper)
        tvcorper = findViewById(R.id.tvcorper)
        tvcarper = findViewById(R.id.tvcarper)
        tvconper = findViewById(R.id.tvconper)
        btnvolper = findViewById(R.id.btnvolper)

        usu = intent.getSerializableExtra("usu") as? Usuario

        tvusuperfil.setText("Bienvenido(a) : " + usu?.nombre)
        tvrutper.setText(usu?.rut)
        tvnomper.setText(usu?.nombre)
        tvapeper.setText(usu?.apellido)
        tvcorper.setText(usu?.email)
        tvcarper.setText(usu?.tipo)
        tvconper.setText(usu?.contraseña)

        volver()
        desactivarFlecha()
    }

    private fun volver() {
        btnvolper.setOnClickListener {
            val intent = Intent(this@Perfil, Menu::class.java)
            intent.putExtra("usu", usu)
            startActivity(intent)
        }
    }

    private fun desactivarFlecha() {
        onBackPressedDispatcher.addCallback(this) {}
    }
}