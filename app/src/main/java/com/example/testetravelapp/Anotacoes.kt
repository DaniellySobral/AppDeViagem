package com.example.testetravelapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Anotacoes : AppCompatActivity() {

    private lateinit var editTextTask: EditText
    private lateinit var buttonAddTask: Button
    private lateinit var listViewTasks: ListView

    private val tasks = mutableListOf<String>()
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
        editTextTask = findViewById(R.id.editTextTask)
        buttonAddTask = findViewById(R.id.buttonAddTask)
        listViewTasks = findViewById(R.id.listViewTasks)

        // Configurar o adapter para a ListView
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tasks)
        listViewTasks.adapter = adapter

        // Adicionar tarefa ao clicar no botão
        buttonAddTask.setOnClickListener {
            addTask()
        }

        // Remover tarefa ao clicar na lista
        listViewTasks.setOnItemClickListener { _, _, position, _ ->
            removeTask(position)
        }
    }

    private fun addTask() {
        val taskText = editTextTask.text.toString()
        if (taskText.isNotEmpty()) {
            tasks.add(taskText) // Adiciona a tarefa à lista
            adapter.notifyDataSetChanged() // Atualiza a ListView
            editTextTask.text.clear() // Limpa o campo de entrada
        }
    }

    private fun removeTask(position: Int) {
        tasks.removeAt(position) // Remove a tarefa da lista
        adapter.notifyDataSetChanged() // Atualiza a ListView
    }
}