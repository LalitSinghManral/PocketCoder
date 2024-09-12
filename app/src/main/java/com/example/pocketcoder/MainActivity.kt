package com.example.pocketcoder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pocketcoder.ui.theme.PocketCoderTheme


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.TextField

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.unit.dp
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PocketCoderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PromptAndResponse()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PocketCoderTheme {
        Greeting("Android")
    }
}

@Composable
fun PromptAndResponse() {
    var editprompt by remember { mutableStateOf("") }
    var textresponse by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = editprompt,
            onValueChange = { editprompt = it },
            label = { Text("Enter Prompt") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                // Trigger the response based on the prompt
                val prompt = editprompt
                val generativeModel =
                    GenerativeModel(
                        // Specify a Gemini model appropriate for your use case
                        modelName = "gemini-1.5-flash",
                        // Access your API key as a Build Configuration variable (see "Set up your API key" above)
                        apiKey = "AIzaSyDnMOQfLwSHm3QhrGdVXU9NKXwiyviAkVA")
                runBlocking {
                val response = generativeModel.generateContent(prompt)
                print(response.text)
                    textresponse = response.text.toString()
                }

            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Send")
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Text(
                    text = textresponse,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}