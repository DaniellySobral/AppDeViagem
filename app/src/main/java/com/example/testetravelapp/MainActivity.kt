package com.example.testetravelapp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private val listaImagens = arrayOf(
        R.drawable.london,
        R.drawable.maldivas,
        R.drawable.porto,
        R.drawable.cabana,
        R.drawable.italia,
        R.drawable.japan,
        R.drawable.balao,
        R.drawable.lugares,
        R.drawable.montanha,
        R.drawable.notredame

    )

    private var indexAtual = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val caixaImagens = findViewById<ImageView>(R.id.caixaBorda)
        val lidarAnimacao = Handler(Looper.getMainLooper())
        val rodarLoop = object : Runnable{
            override fun run(){
                val fadeOut = ObjectAnimator.ofFloat(caixaImagens, "alpha", 1f, 0f)
                fadeOut.duration = 1000
                fadeOut.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        // Troca a imagem
                        caixaImagens.setImageResource(listaImagens[indexAtual])
                        indexAtual = (indexAtual + 1) % listaImagens.size

                        // Inicia a animação de fade in
                        val fadeIn = ObjectAnimator.ofFloat(caixaImagens, "alpha", 0f, 1f)
                        fadeIn.duration = 1500
                        fadeIn.start()
                    }
                })
                fadeOut.start()

               lidarAnimacao.postDelayed(this,4500)
            }
        }
        lidarAnimacao.post(rodarLoop)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


    }
}