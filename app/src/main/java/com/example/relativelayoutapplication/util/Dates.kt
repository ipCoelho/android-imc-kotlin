package com.example.relativelayoutapplication.util

import android.widget.Toast
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*

fun convertStringToLocalDate(brazilDate: String) : LocalDate {

    val dateFormatterFromBrazil = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    val localDateFormat = LocalDate.parse(brazilDate, dateFormatterFromBrazil)

    return localDateFormat

}

fun getDataAtualBrazil(): String {

    val hoje = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    val dataBrazil = hoje.format(formatter)

    return dataBrazil
}

fun convertLocalDateToBrazilDate() {

}

fun calcularIdade (dataNascimento: String) : Int {

    // Obter a data atual (hoje)
    val hoje = LocalDate.now()

    // Converter a data de nascimento em um LocalDate
    // Extrair a data em um array
    val nascimentoArray = dataNascimento.split("-").toTypedArray()
    val nascimento = LocalDate.of(nascimentoArray[0].toInt(),
                                  nascimentoArray[1].toInt(),
                                  nascimentoArray[2].toInt())

    // Obter a idade
    val idade = Period.between(nascimento, hoje).years

    return idade
}