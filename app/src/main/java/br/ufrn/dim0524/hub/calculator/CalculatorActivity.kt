package br.ufrn.dim0524.hub.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.BundleCompat
import br.ufrn.dim0524.hub.R

class CalculatorActivity : AppCompatActivity() {
    private lateinit var tvDisplay: TextView
    private lateinit var tvOperation: TextView

    private var calculator: Calculator = Calculator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        tvDisplay = findViewById(R.id.tv_display)
        tvOperation = findViewById(R.id.tv_operation)

        if (savedInstanceState != null) {
            calculator = BundleCompat.getParcelable(savedInstanceState, "calc", Calculator::class.java) ?: Calculator()
            updateDisplay()
        }

        findViewById<ImageButton>(R.id.btn_toggle).setOnClickListener {
            requestedOrientation = if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            } else {
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        }


        val digits = listOf(
            "0" to R.id.btn0, "1" to R.id.btn1, "2" to R.id.btn2, "3" to R.id.btn3,
            "4" to R.id.btn4, "5" to R.id.btn5, "6" to R.id.btn6, "7" to R.id.btn7,
            "8" to R.id.btn8, "9" to R.id.btn9, "." to R.id.btn_dot
        )
        digits.forEach { (digit, id) ->
            findViewById<Button>(id)?.setOnClickListener {
                calculator.appendDigit(digit)
                updateDisplay()
            }
        }

        val ops = listOf(
            "+" to R.id.btn_sum, "-" to R.id.btn_subtract,
            "x" to R.id.btn_multiply, "÷" to R.id.btn_divide,
            "x^2" to R.id.btn_pow
        )
        ops.forEach { (op, id) ->
            findViewById<Button>(id)?.setOnClickListener {
                calculator.onOperator(op)
                updateDisplay()
            }
        }

        val scientificOps = listOf(
            "sin" to R.id.btn_sin, "cos" to R.id.btn_cos, "tan" to R.id.btn_tan,
            "log" to R.id.btn_log, "ln" to R.id.btn_ln, "sqrt" to R.id.btn_sqrroot,
            "fac" to R.id.btn_fac
        )
        scientificOps.forEach { (op, id) ->
            findViewById<Button>(id)?.setOnClickListener {
                calculator.onUnaryOperator(op)
                updateDisplay()
            }
        }

        val constants = listOf(
            "π" to R.id.btn_pi, "e" to R.id.btn_euler
        )
        constants.forEach { (const, id) ->
            findViewById<Button>(id)?.setOnClickListener {
                calculator.setConstant(const)
                updateDisplay()
            }
        }

        findViewById<Button>(R.id.btn_equals)?.setOnClickListener {
            calculator.onEquals()
            updateDisplay()
        }

        findViewById<Button>(R.id.btn_clear)?.setOnClickListener {
            calculator.clear()
            updateDisplay()
        }

        findViewById<Button>(R.id.btn_backspace)?.setOnClickListener {
            calculator.backspace()
            updateDisplay()
        }
    }

    private fun updateDisplay() {
        val displayValue = calculator.display
        tvDisplay.text = if (displayValue.isEmpty() && calculator.operand != null) {
             calculator.formatDouble(calculator.operand!!)
        } else displayValue.ifEmpty {
            "0"
        }
        
        tvOperation.text = calculator.pendingOp
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelable("calc", calculator)
    }
}
