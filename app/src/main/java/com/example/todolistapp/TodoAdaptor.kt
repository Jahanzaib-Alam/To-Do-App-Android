package com.example.todolistapp

import android.content.Context
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistapp.databinding.ItemTodoBinding

class TodoAdaptor(
    private val todos: MutableList<Todo>
) : RecyclerView.Adapter<TodoAdaptor.TodoViewHolder>() {

    class TodoViewHolder(val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root)

    // Specify what a specific item in our list looks like
    // This method returns an instance of TodoViewHolder
    // ViewGroup is always a layout in Android
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    fun addTodo(todo: Todo) {
        todos.add(todo)
        notifyItemInserted(todos.size - 1)
    }

    fun deleteDoneTodos() {
        todos.removeAll { todo ->
            todo.isChecked
        }
        notifyDataSetChanged()
    }


    private fun toggleStrikeThrough(tvTodoTitle: TextView, isChecked: Boolean) {
        if (isChecked) {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    // This will bind the data from our todos list to the views of our list
    // Which view of our to do item XML do we want to set the text to and do we want to check the checkbox
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val curTodo = todos[position]

        val tvTodoTitleBinding = holder.binding
        val tvTodoTitle = tvTodoTitleBinding.tvTodoTitle
        val cbDone = tvTodoTitleBinding.cbDone
        tvTodoTitle.text = curTodo.title
        cbDone.isChecked = curTodo.isChecked
        toggleStrikeThrough(tvTodoTitle, curTodo.isChecked)
        cbDone.setOnCheckedChangeListener {_, isChecked ->
            toggleStrikeThrough(tvTodoTitle, isChecked)
            curTodo.isChecked = !curTodo.isChecked
        }
    }

    override fun getItemCount(): Int {
        return todos.size
    }
}