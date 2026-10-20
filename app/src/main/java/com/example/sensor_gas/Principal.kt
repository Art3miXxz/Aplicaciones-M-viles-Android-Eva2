package com.example.sensor_gas

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Principal : AppCompatActivity() {

    private lateinit var txtusu: EditText
    private lateinit var txtcon: EditText
    private lateinit var btnini: Button
    private lateinit var btncue: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_principal)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtusu = findViewById(R.id.txtusu)
        txtcon = findViewById(R.id.txtcon)
        btnini = findViewById(R.id.btnini)
        btncue = findViewById(R.id.btncue)

        botonLogin()
        botonCrearCuenta()
        desactivarFlecha()
    }

    fun botonLogin() {
        btnini.setOnClickListener {
            if (txtusu.text.toString().trim().isEmpty()
                || txtcon.text.toString().trim().isEmpty()) {

                Toast.makeText(
                    this@Principal,
                    "¡ Complete Los Campos Faltantes !",
                    Toast.LENGTH_LONG
                ).show()

            } else {

                var rutIngresado = txtusu.getText().toString().trim().uppercase()
                var conIngresada = txtcon.getText().toString().trim()
                var estado = false

                val dbref = FirebaseDatabase.getInstance().getReference("Usuario")

                dbref.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        for (x in snapshot.children) {

                            val rutDB = x.child("rut").value?.toString() ?: ""
                            val conDB = x.child("contraseña").value?.toString() ?: ""

                            if (rutIngresado.equals(rutDB, ignoreCase = true) && conIngresada == conDB) {

                                estado = true
                                val rut = x.child("rut").getValue().toString()
                                val nom = x.child("nombre").getValue().toString()
                                val ape = x.child("apellido").getValue().toString()
                                val ema = x.child("email").getValue().toString()
                                val tip = x.child("tipo").getValue().toString()

                                var usu = Usuario(rut, nom, ape, ema, tip, conDB)
                                var intent = Intent(this@Principal, Menu::class.java)
                                intent.putExtra("usu", usu)
                                startActivity(intent)

                            }
                        }

                        if (estado == false) {
                            txtusu.setText("")
                            txtcon.setText("")
                            ocultarTeclado()

                            Toast.makeText(
                                this@Principal,
                                "¡ Error de Usuario y/o Contraseña !",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(this@Principal, "Error de conexión", Toast.LENGTH_SHORT).show()
                    }
                })

            }
        }
    }

    private fun botonCrearCuenta() {
        btncue.setOnClickListener {
            val intent = Intent(this@Principal, Cuenta::class.java)
            startActivity(intent)
        }
    }

    fun desactivarFlecha() {
        onBackPressedDispatcher.addCallback(this) {}
    }

    private fun ocultarTeclado() {
        val view = currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}