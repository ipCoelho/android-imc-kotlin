package com.example.relativelayoutapplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.relativelayoutapplication.R
import com.example.relativelayoutapplication.util.calcularIdade
import com.example.relativelayoutapplication.util.convertBase64ToBitmap

class DashboardActivity : AppCompatActivity() {

    lateinit var tvNome: TextView
    lateinit var tvProfissao: TextView
    lateinit var tvImc: TextView
    lateinit var tvNcd: TextView
    lateinit var tvPeso: TextView
    lateinit var tvIdade: TextView
    lateinit var tvAltura: TextView
    lateinit var ivPerfil: ImageView
    lateinit var cardNovaPesagem: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        supportActionBar!!.hide()

        tvNome = findViewById(R.id.tv_dashboard_nome)
        tvProfissao = findViewById(R.id.tv_dashboard_profissao)
        tvImc = findViewById(R.id.tv_dashboard_imc)
        tvNcd = findViewById(R.id.tv_dashboard_dataNascimento)
        tvPeso = findViewById(R.id.tv_dashboard_peso)
        tvIdade = findViewById(R.id.tv_dashboard_idade)
        tvAltura = findViewById(R.id.tv_dashboard_altura)
        ivPerfil = findViewById(R.id.iv_dashboard_imgperfil)
        cardNovaPesagem = findViewById(R.id.cv_pesar_agora)

        cardNovaPesagem.setOnClickListener {
            val intent = Intent(this, PesoActivity::class.java)
            startActivity(intent)
        }

        carregarDashboard()

    }

    private fun carregarDashboard() {

        val arquivo = getSharedPreferences("usuario", MODE_PRIVATE)

        tvNome.text = arquivo.getString("nome", "")
        tvProfissao.text = arquivo.getString("profissao", "")
        tvAltura.text = arquivo.getFloat("altura", 0.0f).toString()
        tvIdade.text = calcularIdade(arquivo.getString("dataNascimento", "")!!).toString()

        val bitmap = convertBase64ToBitmap(arquivo.getString("fotoPerfil", "")!!)
        ivPerfil.setImageBitmap(bitmap)


    }
}