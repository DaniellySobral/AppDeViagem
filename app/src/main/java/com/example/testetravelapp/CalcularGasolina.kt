package com.example.testetravelapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CalcularGasolina : AppCompatActivity() {
    private lateinit var editTextDistancia: EditText
    private lateinit var editTextConsumo: EditText
    private lateinit var editTextPreco: EditText
    private lateinit var botaoCalcule: Button
    private lateinit var textoResult: TextView
    private lateinit var botaoVoltar: ImageButton
    private lateinit var checkBoxCaminhoVolta: CheckBox

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_calcular_gasolina)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }
        editTextDistancia = findViewById(R.id.editTextDistancia)
        editTextConsumo = findViewById(R.id.editTextConsumo)
        editTextPreco = findViewById(R.id.editTextPreco)
        botaoCalcule = findViewById(R.id.botaoCalcule)
        textoResult = findViewById(R.id.textoResult)
        checkBoxCaminhoVolta = findViewById(R.id.checkBoxCaminhoVolta)

        botaoCalcule.setOnClickListener {
            calcularCusto()
        }

        botaoVoltar = findViewById(R.id.botaoVoltar)

        botaoVoltar.setOnClickListener {
            finish()
        }
    }
    private fun calcularCusto() {

        val distancia = editTextDistancia.text.toString().toDoubleOrNull()
        val consumo = editTextConsumo.text.toString().toDoubleOrNull()
        val preco = editTextPreco.text.toString().toDoubleOrNull()


        if (distancia != null && consumo != null && preco != null) {
            var distanciaTotal = distancia

            // Verificar se o caminho de volta foi confirmado
            if (checkBoxCaminhoVolta.isChecked) {
                distanciaTotal *= 2 // Dobra a distância
            }

            val litros = distanciaTotal / consumo // Litros necessários para a viagem
            val total = litros * preco // Custo total da viagem


            textoResult.text = "Custo Total: R$ ${"%.2f".format(total)}\nLitros Consumidos: ${"%.2f".format(litros)} L"
        } else {

            textoResult.text = "Por favor, preencha todos os campos corretamente."
        }
    }
}