package com.example.relativelayoutapplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.relativelayoutapplication.R
import com.example.relativelayoutapplication.util.getDataAtualBrazil
import java.time.LocalDate

class PesoActivity : AppCompatActivity() {

    lateinit var ivPerfil: ImageView
    lateinit var tvDataPesagem: TextView
    lateinit var etNovoPeso: EditText
    lateinit var spinnerNivel: Spinner
    lateinit var btnSalvar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_peso)

        supportActionBar!!.hide()

        ivPerfil = findViewById(R.id.iv_peso_perfil)
        tvDataPesagem = findViewById(R.id.tv_peso_data)
        etNovoPeso = findViewById(R.id.et_peso_seu_peso)
        spinnerNivel = findViewById(R.id.spn_niveis)
        btnSalvar = findViewById(R.id.btn_peso_salvar)

        tvDataPesagem.text = getDataAtualBrazil()

        btnSalvar.setOnClickListener {
            val arquivo = getSharedPreferences("usuario", MODE_PRIVATE)
            val pesagem = arquivo.getString("pesagem", "")
            val dataPesagem = arquivo.getString("data_pesagem", "")
            val nivel = arquivo.getString("nivel", "")

            val editor = arquivo.edit()
            editor.putString("pesagem", "$pesagem;${etNovoPeso.text.toString()}")
            editor.putString("dataPesagem", "$dataPesagem;${LocalDate.now().toString()}")
            editor.putString("nivel", "$nivel;${spinnerNivel.selectedItemPosition.toString()}")
            editor.apply()

            Toast.makeText(this, "Registro salvo!", Toast.LENGTH_SHORT).show()
            finish()

        }

        ivPerfil.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }



    }
}