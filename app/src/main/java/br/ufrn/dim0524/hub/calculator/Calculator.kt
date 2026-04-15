package br.ufrn.dim0524.hub.calculator

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlin.math.*

@Parcelize
class Calculator : Parcelable {
    var display : String = "0"
    var pendingOp : String = ""
    var operand : Double? = null

    private fun operate(a: Double, b: Double, operation: String): Double {
        return when (operation) {
            "+" -> a + b
            "-" -> a - b
            "x" -> a * b
            "÷" -> a / b
            "x^2" -> a.pow(b)
            else -> b
        }
    }

    fun operateUnary(a : Double, operation: String) : Double {
        return when (operation) {
            "sin" -> sin(Math.toRadians(a))
            "cos" -> cos(Math.toRadians(a))
            "tan" -> tan(Math.toRadians(a))
            "log" -> log10(a)
            "ln" -> ln(a)
            "sqrt" -> sqrt(a)
            "fac" -> factorial(a)
            else -> a
        }
    }

    private fun factorial(n: Double): Double {
        if (n < 0.0 || n > 20.0) return Double.NaN
        val longN = n.toLong()
        if (longN.toDouble() != n) return Double.NaN
        var res = 1.0
        for (i in 1..longN) {
            res *= i
        }
        return res
    }

    fun setConstant(constant: String) {
        val value = when (constant) {
            "π" -> PI
            "e" -> E
            else -> 0.0
        }
        display = formatDouble(value)
    }

    fun appendDigit(digit: String) {
        if (digit == "." && display.contains(".")) return
        if (display == "0" && digit != ".") {
            display = digit
        } else {
            display += digit
        }
    }

    fun onOperator(op: String) {
        val value = display.toDoubleOrNull()
        if (value != null) {
            if (operand == null) {
                operand = value
            } else if (pendingOp.isNotEmpty()) {
                operand = operate(operand!!, value, pendingOp)
            }
            display = ""
        }
        pendingOp = op
    }

    fun onUnaryOperator(op: String) {
        val value = display.toDoubleOrNull()
        if (value != null) {
            val result = operateUnary(value, op)
            display = formatDouble(result)
        } else if (operand != null && display.isEmpty()) {
            val result = operateUnary(operand!!, op)
            operand = result
        }
    }

    fun onEquals() {
        val value = display.toDoubleOrNull()
        if (value != null && operand != null && pendingOp.isNotEmpty()) {
            val result = operate(operand!!, value, pendingOp)
            display = formatDouble(result)
            operand = null
            pendingOp = ""
        }
    }

    fun formatDouble(d: Double): String {
        return if (d == d.toLong().toDouble()) {
            d.toLong().toString()
        } else {
            String.format("%.8f", d).trimEnd('0').trimEnd('.')
        }
    }

    fun clear() {
        display = "0"
        pendingOp = ""
        operand = null
    }

    fun backspace() {
        display = if (display.length > 1) {
            display.dropLast(1)
        } else {
            "0"
        }
    }
}
