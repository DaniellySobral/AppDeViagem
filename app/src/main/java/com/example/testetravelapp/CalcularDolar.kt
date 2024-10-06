package com.example.testetravelapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CalcularDolar : AppCompatActivity() {

    private lateinit var editTextReal: EditText
    private lateinit var editTextCambio: EditText
    private lateinit var botaoCalcular: Button
    private lateinit var textoResultado: TextView
    lateinit var botaoVoltar: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_calcular_dolar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        editTextReal = findViewById(R.id.editTextDistancia)
        editTextCambio = findViewById(R.id.editTextConsumo)
        botaoCalcular = findViewById(R.id.botaoCalcule)
        textoResultado = findViewById(R.id.textoResult)

        botaoCalcular.setOnClickListener {
            convertCurrency()
        }

        botaoVoltar = findViewById(R.id.botaoVoltar)

        botaoVoltar.setOnClickListener {
            finish()
        }
    }

    private fun convertCurrency() {
        // Obtém os valores das entradas
        val Reais = editTextReal.text.toString().toDoubleOrNull()
        val Cambio = editTextCambio.text.toString().toDoubleOrNull()

        // Verifica se os valores são válidos
        if (Reais != null && Cambio != null && Cambio > 0) {
            // Calcula o valor em Dólares
            val amountInDollars = Reais * Cambio

            // Exibe o resultado
            textoResultado.text = "Valor em Dólares (USD): \n\$ ${"%.2f".format(amountInDollars)}"
        } else {
            // Exibe uma mensagem de erro se os valores forem inválidos
            textoResultado.text = "Por favor, preencha todos os campos corretamente."
        }
    }
}