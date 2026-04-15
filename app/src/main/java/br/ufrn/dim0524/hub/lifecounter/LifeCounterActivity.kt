package br.ufrn.dim0524.hub.lifecounter

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import br.ufrn.dim0524.hub.R
import br.ufrn.dim0524.hub.utils.hideSystemBars
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.card.MaterialCardView

class LifeCounterActivity : AppCompatActivity() {
    private var p1: Player = Player(20, Type.BLUE)
    private var p2: Player = Player(20, Type.RED)

    private lateinit var tvP1Life: TextView
    private lateinit var tvP2Life: TextView
    private lateinit var btnP1Plus: Button
    private lateinit var btnP1Minus: Button
    private lateinit var btnP2Plus: Button
    private lateinit var btnP2Minus: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        enableEdgeToEdge()
        setContentView(R.layout.activity_life_counter)

        hideSystemBars(window)

        tvP1Life = findViewById(R.id.tv_p1_life)
        tvP2Life = findViewById(R.id.tv_p2_life)
        btnP1Plus = findViewById(R.id.btn_p1_plus)
        btnP1Minus = findViewById(R.id.btn_p1_minus)
        btnP2Plus = findViewById(R.id.btn_p2_plus)
        btnP2Minus = findViewById(R.id.btn_p2_minus)

        val toggleGroup = findViewById<MaterialButtonToggleGroup>(R.id.toggle_life_options)

        toggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.btn_life_20 -> restartNewHp(20)
                    R.id.btn_life_30 -> restartNewHp(30)
                    R.id.btn_life_40 -> restartNewHp(40)
                }
            }
        }

        btnP1Plus.setOnClickListener { receiveLife(p1) }
        btnP1Minus.setOnClickListener { receiveAttack(p1) }
        btnP2Plus.setOnClickListener { receiveLife(p2) }
        btnP2Minus.setOnClickListener { receiveAttack(p2) }
        
        val colorButtons = listOf(R.id.btn_p1_color, R.id.btn_p2_color)
        colorButtons.forEach { id ->
            findViewById<Button>(id).setOnClickListener {
                cycleColor(player = if (id == R.id.btn_p1_color) p1 else p2)
            }
        }
    }

    private val typeToColorRes = mapOf(
        Type.BLUE to R.color.blue_lf,
        Type.RED to R.color.red_lf,
        Type.GREEN to R.color.green_lf,
        Type.BLACK to R.color.dark_lf,
        Type.WHITE to R.color.white_lf
    )

    private fun cycleColor(player: Player) {
        val types = Type.entries
        val nextType = types[(player.getType().ordinal + 1) % types.size]
        player.setType(nextType)
        
        typeToColorRes[nextType]?.let { colorRes ->
            updatePlayerTheme(player, colorRes)
        }
    }

    private fun updatePlayerTheme(player: Player, colorRes: Int) {
        val color = ContextCompat.getColor(this, colorRes)
        if (p1 == player) {
            findViewById<MaterialCardView>(R.id.card_p1).strokeColor = color
            findViewById<Button>(R.id.btn_p1_color).setTextColor(color)
            tvP1Life.setTextColor(color)
            btnP1Plus.setTextColor(color)
            btnP1Minus.setTextColor(color)
        } else {
            findViewById<MaterialCardView>(R.id.card_p2).strokeColor = color
            findViewById<Button>(R.id.btn_p2_color).setTextColor(color)
            tvP2Life.setTextColor(color)
            btnP2Plus.setTextColor(color)
            btnP2Minus.setTextColor(color)
        }
    }

    fun restartNewHp (hp: Int) {
        p1.setHp(hp)
        p2.setHp(hp)
        updateDisplay()
    }

    fun receiveAttack(player: Player) {
        player.receiveAttack()
        updateDisplay()
    }
    fun receiveLife(player: Player) {
        player.receiveLife()
        updateDisplay()
    }


    fun updateDisplay() {
        tvP1Life.text = p1.getHp().toString()
        tvP2Life.text = p2.getHp().toString()
    }
}