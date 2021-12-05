package com.example.relativelayoutapplication.ui

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.core.graphics.drawable.toBitmap
import com.example.relativelayoutapplication.R
import com.example.relativelayoutapplication.model.Usuario
import com.example.relativelayoutapplication.util.convertBitmapToBase64
import com.example.relativelayoutapplication.util.convertStringToLocalDate
import java.time.LocalDate
import java.util.*

const val CODE_IMAGE = 666

class NovoUsuarioActivity : AppCompatActivity() {

    lateinit var editEmail: EditText
    lateinit var editSenha: EditText
    lateinit var editNome: EditText
    lateinit var editProfissao: EditText
    lateinit var editAltura: EditText
    lateinit var editData: EditText
    lateinit var radioFeminino: RadioButton
    lateinit var radioMasculino: RadioButton
    lateinit var tvTrocarFoto: TextView
    lateinit var ivFotoPerfil: ImageView
    var imageBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_usuario)

        editEmail = findViewById(R.id.et_email)
        editSenha = findViewById(R.id.et_senha)
        editNome = findViewById(R.id.et_nome)
        editProfissao = findViewById(R.id.et_profissao)
        editAltura = findViewById(R.id.et_altura)
        editData = findViewById(R.id.et_data)
        radioFeminino = findViewById(R.id.radioButtonFeminino)
        radioMasculino = findViewById(R.id.radioButtonMasculino)
        tvTrocarFoto = findViewById(R.id.tv_trocar_foto)
        ivFotoPerfil = findViewById(R.id.iv_foto_perfil)

        // Carregar bitmap padrão caso o usuário não escolha uma foto
        imageBitmap = BitmapFactory.decodeResource(resources, R.drawable.person_icon)

        // se for vetor
        // imageBitmap = resources.getDrawable(R.drawable.person_icon).toBitmap()

        supportActionBar!!.title = "Perfil"

        // abrir a galeria de fotos para escolher foto de perfil
        tvTrocarFoto.setOnClickListener {
            abrirGaleria()
        }

        val calendario = Calendar.getInstance()

        // determinar os dados (dia, mês e ano) no calendário
        val ano = calendario.get(Calendar.YEAR)
        val mes = calendario.get(Calendar.MONTH)
        val dia = calendario.get(Calendar.DAY_OF_MONTH)

        // abrir o componente DatePicker
        val etDataNascimento = findViewById<EditText>(R.id.et_data)

        etDataNascimento.setOnClickListener {
            val dp = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, _ano, _mes, _dia ->

                    var diaFinal = _dia
                    var mesFinal = _mes + 1

                    var diaString = "$diaFinal"
                    var mesString = "$mesFinal"

                    if (mesFinal < 10) {
                        mesString = "0$mesFinal"
                    }

                    if (diaFinal < 10) {
                        diaString = "0$diaFinal"
                    }

                    Log.i("xpto", _dia.toString())
                    Log.i("xpto", _mes.toString())

                    etDataNascimento.setText("$diaString/$mesString/$_ano")

                }, ano, mes, dia)

            dp.show()

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, imagem: Intent?) {
        super.onActivityResult(requestCode, resultCode, imagem)

        if (requestCode == CODE_IMAGE && resultCode == -1) {

            // recuperar a imagem do stream (fluxo)
            val fluxoImagem = contentResolver.openInputStream(imagem!!.data!!)

            // converter os bits em um bitmap
            imageBitmap = BitmapFactory.decodeStream(fluxoImagem)

            // colocar o bitmap no ImageView
            ivFotoPerfil.setImageBitmap(imageBitmap)

        }

    }

    private fun abrirGaleria() {

        // abrir a galeria de imagens do dispositivo
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"

        // abrir a activity responsável por exibir as imagens, ela retornará o conteúdo selecionado para o nosso app
        startActivityForResult(Intent.createChooser(intent, "Escolha uma foto"), CODE_IMAGE)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (validarCampos()) {
            // Vai receber o retorno da data em pt-br
            val nascimento = convertStringToLocalDate(editData.text.toString())

            // Criar o objeto Usuário
            val usuario = Usuario(
                    1,
                    editNome.text.toString(),
                    editEmail.text.toString(),
                    editSenha.text.toString(),
                    0,
                    editAltura.text.toString().toDouble(),
                    LocalDate.of(
                            nascimento.year,
                            nascimento.monthValue,
                            nascimento.dayOfMonth
                    ),
                    editProfissao.text.toString(),
                    if (radioFeminino.isChecked) 'F' else 'M',
                    convertBitmapToBase64(imageBitmap!!)

            )

            // Salvar o registro em um SharedPreferences

            // A instrução abaixo irá criar um arquivo SharedPreferences se não existir. Se existir, será aberto para edição
            val dados = getSharedPreferences("usuario", Context.MODE_PRIVATE)

            // Vamos criar o objeto que permitirá a edição dos dados do arquivo SharedPreferences
            val editor = dados.edit()
            editor.putInt("id", usuario.id)
            editor.putString("nome", usuario.nome)
            editor.putString("email", usuario.email)
            editor.putString("senha", usuario.senha)
            editor.putInt("peso", usuario.peso)
            editor.putFloat("altura", usuario.altura.toFloat())
            editor.putString("dataNascimento", usuario.dataNascimento.toString())
            editor.putString("profissao", usuario.profissao)
            editor.putString("sexo", usuario.sexo.toString())
            editor.putString("fotoPerfil", usuario.fotoPerfil)
            editor.apply()
        }

        Toast.makeText(this, "Usuário cadastrado!", Toast.LENGTH_SHORT).show()

        return true

    }


    fun validarCampos() : Boolean {
        var valido = true

        if (imageBitmap == null) {



        }

        if (editEmail.text.isEmpty()) {
            editEmail.error = "O e-mail é obrigatório!"
            valido = false
        }

        if (editSenha.text.isEmpty()) {
            editSenha.error = "A senha é obrigatória!"
            valido = false
        }

        if (editSenha.text.isEmpty()) {
            editSenha.error = "A senha é obrigatória!"
            valido = false
        }

        if (editNome.text.isEmpty()) {
            editNome.error = "O nome é obrigatório!"
            valido = false
        }

        if (editProfissao.text.isEmpty()) {
            editProfissao.error = "A profissão é obrigatória!"
            valido = false
        }

        if (editAltura.text.isEmpty()) {
            editAltura.error = "A altura é obrigatória!"
            valido = false
        }

//        if (editData.text.isEmpty()) {
//            editData.error = "A data de nascimento é obrigatória!"
//            valido = false
//        }

        if (radioMasculino.text.isEmpty() && radioFeminino.text.isEmpty()) {
            radioMasculino.error = "O sexo é obrigatório!"
            valido = false
        }

        return valido

    }

}