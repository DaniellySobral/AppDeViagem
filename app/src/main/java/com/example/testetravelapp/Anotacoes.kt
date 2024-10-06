package com.example.testetravelapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Anotacoes : AppCompatActivity() {

    private lateinit var editTextTarefa: EditText
    private lateinit var botaoAdicionar: Button
    private lateinit var listViewTarefa: ListView
    private lateinit var botaoVoltar: ImageButton

    private val tarefa = mutableListOf<String>()
    private lateinit var adapter: ArrayAdapter<String>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_anotacoes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        editTextTarefa = findViewById(R.id.editTextTarefa)
        botaoAdicionar = findViewById(R.id.botaoAdicionar)
        listViewTarefa = findViewById(R.id.listViewTarefa)

        // Configurar adapter para a ListView (usado para comunicar entre a lista de string e a ListView)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tarefa)
        listViewTarefa.adapter = adapter


        botaoAdicionar.setOnClickListener {
            addTask()
        }

        // Remover tarefa
        listViewTarefa.setOnItemClickListener { _, _, position, _ -> //só quero utilizar o parâmetro position e ignorar os outros
            removerTarefa(position)
        }

        botaoVoltar = findViewById(R.id.botaoVoltar)

        botaoVoltar.setOnClickListener {
            finish()
        }
    }

    private fun addTask() {
        val textTarefa = editTextTarefa.text.toString()
        if (textTarefa.isNotEmpty()) {
            tarefa.add(textTarefa) // Adicionar tarefa à lista
            adapter.notifyDataSetChanged() // Atualizar ListView
            editTextTarefa.text.clear() // Limpar campo de entrada para permitir novo (editText)
        }
    }

    private fun removerTarefa(position: Int) {
        tarefa.removeAt(position) // Remove a tarefa da lista na posição selecionada
        adapter.notifyDataSetChanged() // Atualiza ListView para saber que foi apagado
    }
}