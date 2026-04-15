package br.ufrn.dim0524.hub

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import br.ufrn.dim0524.hub.bascore.BascoreActivity
import br.ufrn.dim0524.hub.calculator.CalculatorActivity
import br.ufrn.dim0524.hub.lifecounter.LifeCounterActivity
import com.google.android.material.card.MaterialCardView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hub)

        val cardBascore = findViewById<MaterialCardView>(R.id.card_bascore)
        val cardCalculator = findViewById<MaterialCardView>(R.id.card_calculator)
        val cardLifeCounter = findViewById<MaterialCardView>(R.id.card_life_counter)


        cardBascore.setOnClickListener {
            val bascoreIntent = Intent(this, BascoreActivity::class.java)
            startActivity(bascoreIntent)
        }

        cardCalculator.setOnClickListener {
            val calculatorIntent = Intent(this, CalculatorActivity::class.java)
            startActivity(calculatorIntent)
        }

        cardLifeCounter.setOnClickListener {
            val lifeCounterIntent = Intent(this, LifeCounterActivity::class.java)
            startActivity(lifeCounterIntent)
        }
    }
}