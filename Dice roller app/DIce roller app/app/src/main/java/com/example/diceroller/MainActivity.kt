/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.diceroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import com.example.diceroller.ui.theme.DiceRollerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiceRollerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DiceRollerApp()
                }
            }
        }
    }
}

@Preview
@Composable
fun DiceRollerApp() {
    DiceWithButtonAndImage(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)
    )
}

@Composable
fun DiceWithButtonAndImage(modifier: Modifier = Modifier) {
    var result by remember { mutableStateOf(1) }
    var userInput by remember { mutableStateOf("") }
    var feedback by remember { mutableStateOf("") }
    var feedbackColor by remember { mutableStateOf(Color.Black) }
    var showFeedback by remember { mutableStateOf(false) }

    val imageResource = when(result) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }

    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
    ) {
        Text(text = "Adivinhe o número do dado!", fontSize = 20.sp)
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Caixa de texto para entrada do usuário
        OutlinedTextField(
            value = userInput,
            onValueChange = { newValue ->
                if (newValue.isEmpty() || newValue.toIntOrNull() in 1..6) {
                    userInput = newValue
                }
            },
            label = { Text("Digite um número (1-6)") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.padding(bottom = 16.dp),
            singleLine = true
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Imagem do dado
        Image(
            painter = painterResource(imageResource),
            contentDescription = result.toString(),
            modifier = Modifier.padding(bottom = 24.dp)
        )
        
        // Botão Roll
        Button(
            onClick = {
                result = (1..6).random()
                
                // Comparar com a entrada do usuário
                if (userInput.isNotEmpty()) {
                    val userGuess = userInput.toInt()
                    if (userGuess == result) {
                        feedback = "🎉 Acertou! Você adivinhou corretamente: $result"
                        feedbackColor = Color.Green
                    } else {
                        feedback = "❌ Errou! Você adivinhou $userGuess, mas o dado mostrou: $result"
                        feedbackColor = Color.Red
                    }
                    showFeedback = true
                } else {
                    feedback = "⚠️ Digite um número antes de lançar o dado!"
                    feedbackColor = Color.Blue
                    showFeedback = true
                }
            },
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            Text(text = "Roll", fontSize = 24.sp)
        }
        
        // Mensagem de feedback
        if (showFeedback) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = feedback,
                fontSize = 18.sp,
                color = feedbackColor,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}