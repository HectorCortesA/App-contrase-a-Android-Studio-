package com.hectorcortes.appcontrasena

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.*


class MainActivity : AppCompatActivity() {

        private lateinit var editTextPassword: EditText
        private lateinit var textViewStatus: TextView
        private lateinit var buttonVerify: Button
        private lateinit var progressBar: ProgressBar
        private var attemptsLeft = 3
        private val correctPassword = "1234"

        @SuppressLint("MissingInflatedId")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            editTextPassword = findViewById(R.id.editTextPassword)
            textViewStatus = findViewById(R.id.textViewStatus)
            buttonVerify = findViewById(R.id.buttonVerify)
            progressBar = findViewById(R.id.progressBar)
            buttonVerify.setOnClickListener {
                verifyPassword()
            }
        }

        private fun verifyPassword() {
            val enteredPassword = editTextPassword.text.toString()

            if (enteredPassword == correctPassword) {
                textViewStatus.text = "Acceso concedido"
                attemptsLeft = 3 // Restablecer intentos
                Log.i("PasswordCheck", "Acceso concedido")
            } else {
                attemptsLeft--
                Log.i("PasswordCheck", "Contraseña incorrecta. Intentos restantes: $attemptsLeft")

                if (attemptsLeft > 0) {
                    textViewStatus.text = "Contraseña incorrecta. Intentos restantes: $attemptsLeft"
                } else {
                    textViewStatus.text = "Bloqueado por 10 segundos"
                    lockUser()
                }
            }
        }

        private fun lockUser() {
            buttonVerify.isEnabled = false
            progressBar.visibility = View.VISIBLE

            val timer = object : CountDownTimer(10000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val secondsLeft = millisUntilFinished / 1000
                    progressBar.progress = (10 - secondsLeft).toInt()
                    Log.i("PasswordCheck", "Bloqueo restante: $secondsLeft segundos")
                }

                override fun onFinish() {
                    progressBar.visibility = View.GONE
                    progressBar.progress = 0
                    attemptsLeft = 3
                    textViewStatus.text = "Puede volver a intentar"
                    buttonVerify.isEnabled = true
                }
            }
            timer.start()
        }
    }
