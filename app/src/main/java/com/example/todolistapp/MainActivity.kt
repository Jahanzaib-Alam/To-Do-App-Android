package com.example.todolistapp

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolistapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Late Init means we will initailise it later
    private lateinit var todoAdaptor: TodoAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        todoAdaptor = TodoAdaptor(mutableListOf())

        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)

        val rvTodoItems = activityMainBinding.rvTodoItems
        rvTodoItems.adapter = todoAdaptor
        rvTodoItems.layoutManager = LinearLayoutManager(this)

        activityMainBinding.btnAddTodo.setOnClickListener {
            val etTodoTitle = activityMainBinding.etTodoTitle
            val todoTitle = etTodoTitle.text.toString()
            if (todoTitle.isNotEmpty()) {
                val todo = Todo(todoTitle)
                todoAdaptor.addTodo(todo)
                etTodoTitle.text.clear()
            }
        }

        activityMainBinding.btnDeleteDoneTodos.setOnClickListener {
            todoAdaptor.deleteDoneTodos()
        }

        enableEdgeToEdge()
        setContentView(activityMainBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}