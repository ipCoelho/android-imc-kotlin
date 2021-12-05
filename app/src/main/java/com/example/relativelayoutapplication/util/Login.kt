package com.example.relativelayoutapplication.util

import android.content.Context
import android.widget.TextView
import com.example.relativelayoutapplication.R
import com.example.relativelayoutapplication.ui.LoginActivity

fun autenticar(email: String, senha: String, context: Context) : Boolean {

    val arquivo = context.getSharedPreferences("usuario", Context.MODE_PRIVATE)

    // return true, if e else é redundante
    return email == arquivo.getString("email", "") && senha == arquivo.getString("senha", "")

}