package com.example.calcularpagos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


abstract class Empleado {
    abstract fun calcularSueldoLiquido(): Double
}

class Honorarios(val montoHonorarios: Double) : Empleado() {
    override fun calcularSueldoLiquido(): Double {
        val retencion = 0.13 // Porcentaje de retención para empleados a honorarios (13%)
        return montoHonorarios * (1 - retencion)
    }
}

class Contrato(val sueldoBruto: Double) : Empleado() {
    override fun calcularSueldoLiquido(): Double {
        val retencion = 0.20 // Porcentaje de retención para empleados con contrato (20%)
        return sueldoBruto * (1 - retencion)
    }
}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PantallaInicio()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PantallaInicio() {
    val contexto = LocalContext.current as ComponentActivity

    Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

        Text(
                text = "Calcular Pagos",
                
                style = TextStyle(
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(30.dp))
            Button(modifier = Modifier
                .height(70.dp)
                .width(250.dp), onClick = {
                val intent = Intent(contexto, CalculoHonorariosActivity::class.java)
                contexto.startActivity(intent)
            }) {
                Text("Empleado Honorarios",
                fontSize = 17.sp)
            }
            Spacer(modifier = Modifier.height(30.dp))

            Button(
                modifier = Modifier
                    .height(70.dp)
                    .width(250.dp),
                onClick = {
                    val intent = Intent(contexto, CalculoContratoActivity::class.java)
                    contexto.startActivity(intent)
                }
            ) {
                Text("Empleados con Contrato",
                    fontSize = 16.sp)
            }
        }
    }

class CalculoHonorariosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PantallaCalculoHonorarios()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PantallaCalculoHonorarios() {
    var montoHonorarios by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }
    val contexto = LocalContext.current as ComponentActivity



    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Cálculo de Honorarios",
            style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            placeholder = { Text("Monto de Honorarios") },
            value = montoHonorarios,
            onValueChange = { montoHonorarios = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(onClick = {
            val monto = montoHonorarios.toDoubleOrNull() ?: 0.0
            val Honorarios = Honorarios(monto)
            val sueldoLiquido = Honorarios.calcularSueldoLiquido()
            resultado = "Sueldo líquido: ${sueldoLiquido}"
        }) {
            Text("Calcular Sueldo Líquido")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(resultado)
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { contexto.finish() }) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
            Text("Volver")
        }
    }
}
class CalculoContratoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contrato)

        val btnCalcular = findViewById<Button>(R.id.btnCalcularContrato)
        btnCalcular.setOnClickListener {
            val textResultado = findViewById<TextView>(R.id.tvResultado)
            val editTextSueldoBruto = findViewById<EditText>(R.id.etMontoContrato)
            val btnVolver = findViewById<Button>(R.id.btnVolver)
            btnVolver.setOnClickListener {
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
            }

            val sueldoBruto = editTextSueldoBruto.text.toString().toDoubleOrNull() ?: 0.0
            val contrato = Contrato(sueldoBruto)
            val resultadocalculo = contrato.calcularSueldoLiquido()
            textResultado.text = "Sueldo líquido: $resultadocalculo"

        }
    }
}










