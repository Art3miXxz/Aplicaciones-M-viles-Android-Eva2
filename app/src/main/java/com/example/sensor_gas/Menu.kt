package com.example.sensor_gas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Menu : AppCompatActivity() {

    private lateinit var tvusumenu: TextView
    private lateinit var btn1: Button
    private lateinit var btn2: Button
    private lateinit var btn3: Button
    private lateinit var btn4: Button
    private lateinit var btn5: Button // Botón Cerrar Sesión

    private var usu: Usuario? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvusumenu = findViewById(R.id.tvusumenu)
        btn1 = findViewById(R.id.btn1)
        btn2 = findViewById(R.id.btn2)
        btn3 = findViewById(R.id.btn3)
        btn4 = findViewById(R.id.btn4)
        btn5 = findViewById(R.id.btn5)

        usu = intent.getSerializableExtra("usu") as? Usuario

        tvusumenu.setText("Bienvenido(a) : " + usu?.nombre)

        boton1()
        boton2()
        boton3()
        boton4()
        botonCerrarSesion()
        desactivarFlecha()
    }

    private fun boton1() {
        btn1.setOnClickListener {
            var intent = Intent(this@Menu, ManipularESP32::class.java)
            intent.putExtra("usu", usu)
            startActivity(intent)
        }
    }

    private fun boton2() {
        btn2.setOnClickListener {
            var intent = Intent(this@Menu, Acciones::class.java)
            intent.putExtra("usu", usu)
            startActivity(intent)
        }
    }

    private fun boton3() {
        btn3.setOnClickListener {
            var intent = Intent(this@Menu, Estadisticas::class.java)
            intent.putExtra("usu", usu)
            startActivity(intent)
        }
    }

    private fun boton4() {
        btn4.setOnClickListener {
            var intent = Intent(this@Menu, Perfil::class.java)
            intent.putExtra("usu", usu)
            startActivity(intent)
        }
    }

    private fun botonCerrarSesion() {
        btn5.setOnClickListener {
            var a = AlertDialog.Builder(this@Menu)
            a.setTitle("Pregunta")
            a.setMessage("¿Desea Cerrar La Sesión?")
            a.setNegativeButton("Cancelar") { dialog, wich ->
                dialog.dismiss()
            }
            a.setPositiveButton("Aceptar") { dialog, wich ->
                var intent = Intent(this@Menu, Principal::class.java)
                startActivity(intent)
            }
            a.setCancelable(false)
            a.show()
        }
    }

    private fun desactivarFlecha() {
        onBackPressedDispatcher.addCallback(this) {}
    }
}