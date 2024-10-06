package com.example.testetravelapp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.buildSpannedString
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
        R.drawable.notredame

    )
    private var indexAtual = 0

    lateinit var botaoAnotacao: Button
    lateinit var converterMoeda: ImageView
    lateinit var calcularCombustivel: ImageView
    private lateinit var rodarLoop: Runnable
    private lateinit var lidarAnimacao: Handler

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val textoLink = findViewById<TextView>(R.id.linkTexto)

        val textoCompleto = textoLink.text.toString()
        val colocandoLink = SpannableString(textoCompleto)

        val textoClicavel = object : ClickableSpan(){
            override fun onClick(widget: View) {
               val executaIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ipeadata.gov.br/ExibeSerie.aspx?stub=1&serid=38590&module=M"))
                startActivity(executaIntent)
            }
        }

        colocandoLink.setSpan(textoClicavel, 20, 41, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        textoLink.text = colocandoLink
        textoLink.movementMethod = LinkMovementMethod.getInstance()

        val caixaImagens = findViewById<ImageView>(R.id.caixaBorda)
        lidarAnimacao = Handler(Looper.getMainLooper())
         rodarLoop = object : Runnable{
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

                        fadeIn.addListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator){

                                lidarAnimacao.postDelayed(rodarLoop, 4500)
                            }

                        })
                    }
                })
                fadeOut.start()

            }
        }
        lidarAnimacao.post(rodarLoop)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        converterMoeda = findViewById(R.id.converterMoeda)
        calcularCombustivel = findViewById(R.id.calcularCombustivel)
        botaoAnotacao = findViewById(R.id.botaoAnotacao)

        converterMoeda.setOnClickListener {
            val intent = Intent(applicationContext, CalcularDolar::class.java)

            startActivity(intent)

        }

        calcularCombustivel.setOnClickListener {
            val intent = Intent(applicationContext, CalcularGasolina::class.java)

            startActivity(intent)
        }

        botaoAnotacao.setOnClickListener {
            val intent = Intent(applicationContext, Anotacoes::class.java)

            startActivity(intent)

        }

    }
    override fun onDestroy() {
        super.onDestroy()
        lidarAnimacao.removeCallbacks(rodarLoop) // Remove o callback para evitar chamadas após a destruição
    }
}