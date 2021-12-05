package com.example.relativelayoutapplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.relativelayoutapplication.R
import com.example.relativelayoutapplication.util.autenticar

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar!!.hide()

        val tvCriarConta = findViewById<TextView>(R.id.tv_criar_conta)
        val buttonEntrar = findViewById<Button>(R.id.btn_entrar)

        val editEmail = findViewById<EditText>(R.id.et_login_email)
        val editSenha = findViewById<EditText>(R.id.et_login_senha)

        buttonEntrar.setOnClickListener {
            val autenticou = autenticar(
                editEmail.text.toString(),
                editSenha.text.toString(),
                this)
            
            if (autenticou) {
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Usuário ou senha incorretos", Toast.LENGTH_SHORT).show()
            }

        }

        tvCriarConta.setOnClickListener {
            val abrirNovoUsuarioActivity = Intent(this, NovoUsuarioActivity::class.java)

            startActivity(abrirNovoUsuarioActivity)

        }

    }

}