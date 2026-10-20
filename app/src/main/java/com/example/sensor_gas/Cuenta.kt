package com.example.sensor_gas

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.FirebaseDatabase
import java.security.MessageDigest

class Cuenta : AppCompatActivity() {

    private lateinit var txtrut: EditText
    private lateinit var txtnomcue: EditText
    private lateinit var txtapecue: EditText
    private lateinit var txtcorcue: EditText
    private lateinit var spncarcue: Spinner
    private lateinit var txtconcue: EditText
    private lateinit var txtcon2cue: EditText
    private lateinit var btnreg: Button
    private lateinit var btnvolcue: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cuenta)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtrut = findViewById(R.id.txtrut)
        txtnomcue = findViewById(R.id.txtnomcue)
        txtapecue = findViewById(R.id.txtapecue)
        txtcorcue = findViewById(R.id.txtcorcue)
        spncarcue = findViewById(R.id.spncarcue)
        txtconcue = findViewById(R.id.txtconcue)
        txtcon2cue = findViewById(R.id.txtcon2cue)
        btnreg = findViewById(R.id.btnreg)
        btnvolcue = findViewById(R.id.btnvolcue)

        configurarSpinner()
        registrarUsuario()
        botonVolver()
    }

    private fun configurarSpinner() {
        val opcionesCargo = arrayOf("Seleccione un Cargo...", "Administrador", "Operador", "Supervisor")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, opcionesCargo)
        spncarcue.adapter = adapter
    }

    private fun registrarUsuario() {
        btnreg.setOnClickListener {
            val pass1 = txtconcue.text.toString().trim()
            val pass2 = txtcon2cue.text.toString().trim()
            val cargoSeleccionado = spncarcue.selectedItem.toString()

            if (txtrut.text.isEmpty() || txtnomcue.text.isEmpty() || txtapecue.text.isEmpty() || txtcorcue.text.isEmpty() || pass1.isEmpty() || cargoSeleccionado == "Seleccione un Cargo...") {
                Toast.makeText(this, "Complete todos los campos obligatorios", Toast.LENGTH_SHORT).show()
            } else if (pass1 != pass2) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            } else {
                val rut = txtrut.text.toString().trim()
                val nombre = txtnomcue.text.toString().trim()
                val apellido = txtapecue.text.toString().trim()
                val email = txtcorcue.text.toString().trim()
                val cargo = cargoSeleccionado

                val conCifrada = pass1

                val nuevoUsuario = Usuario(rut, nombre, apellido, email, cargo, conCifrada)

                val dbref = FirebaseDatabase.getInstance().getReference("Usuario")
                val key = dbref.push().key ?: "1"

                dbref.child(key).setValue(nuevoUsuario).addOnCompleteListener { tarea ->
                    if (tarea.isSuccessful) {
                        Toast.makeText(this, "Cuenta creada exitosamente", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@Cuenta, Principal::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Error al crear la cuenta", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun botonVolver() {
        btnvolcue.setOnClickListener {
            val intent = Intent(this@Cuenta, Principal::class.java)
            startActivity(intent)
        }
    }

    private fun cifrarSHA256(texto: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(texto.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}