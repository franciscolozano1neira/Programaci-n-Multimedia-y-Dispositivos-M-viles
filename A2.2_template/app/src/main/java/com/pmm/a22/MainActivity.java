package com.pmm.a22;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.pmm.a22.calculator.Calculator;

public class MainActivity extends AppCompatActivity {

    private final Calculator _calculator = new Calculator();

    private String resultado = "";
    private boolean calculado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculator);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void operandClick(View view) {

        if (calculado) clearClick(null);

        int operandButtonId = view.getId();
        System.out.println(operandButtonId);  // Mantener la impresión de la ID del botón pulsado
        String operand = "";

        // Asignar el valor correspondiente a cada botón según su ID
        switch (operandButtonId) {
            case R.id.button1: operand = "1"; break;
            case R.id.button2: operand = "2"; break;
            case R.id.button3: operand = "3"; break;
            case R.id.button4: operand = "4"; break;
            case R.id.button5: operand = "5"; break;
            case R.id.button6: operand = "6"; break;
            case R.id.button7: operand = "7"; break;
            case R.id.button8: operand = "8"; break;
            case R.id.button9: operand = "9"; break;
            case R.id.button0: operand = "0"; break;
            case R.id.buttonborrar: operand = "#"; break;
            case R.id.buttonigual: operand = "="; break;
            // Agrega más casos si es necesario
            default: break;
        }

        _calculator.setOperand(operand);
        resultado += operand;

        // Actualizar la pantalla de la calculadora
        EditText display = findViewById(R.id.editTextNumberDecimal);
        display.setText(resultado);
    }

    public void operatorClick(View view) {

        int operatorButtonId = view.getId();
        System.out.println(operatorButtonId);  // Mantener la impresión de la ID del operador pulsado
        Calculator.Operators operator = null;

        if (!_calculator.isNewOperation()) {
            // Calcular resultado si ya se ha ingresado el primer y segundo operando
            try {
                float resultadoCalculado = _calculator.calculate();
                resultado = String.valueOf(resultadoCalculado);
                EditText display = findViewById(R.id.editTextNumberDecimal);
                display.setText(resultado);
                _calculator.clear();  // Limpiar para nueva operación
            } catch (Calculator.MissingOperandException | Calculator.DivisionByZeroException e) {
                resultado = "Error";
                EditText display = findViewById(R.id.editTextNumberDecimal);
                display.setText(resultado);
            }
            return;
        }

        // Asignar operador según el ID del botón
        switch (operatorButtonId) {
            case R.id.buttonsumar:  // Botón para "+"
                operator = Calculator.Operators.ADD;
                break;
            case R.id.buttonrestar:  // Botón para "-"
                operator = Calculator.Operators.SUBSTRACT;
                break;
            case R.id.buttonmultiplicar:  // Botón para "*"
                operator = Calculator.Operators.MULTIPLY;
                break;
            case R.id.buttondividir:  // Botón para "/"
                operator = Calculator.Operators.DIVIDE;
                break;
            default:
                break;
        }

        _calculator.setOperator(operator);
        resultado += " " + operator.toString() + " ";

        // Actualizar la pantalla de la calculadora
        EditText display = findViewById(R.id.editTextNumberDecimal);
        display.setText(resultado);
    }

    public void clearClick(View view) {
        _calculator.clear();
        calculado = false;
        resultado = "";

        // Actualizar la pantalla de la calculadora
        EditText display = findViewById(R.id.editTextNumberDecimal);
        display.setText(resultado);
    }
}
