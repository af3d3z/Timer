package com.example.timer.views

import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.timer.R
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date



@Composable
fun Timer(modifier: Modifier){
    val mediaPlayer = MediaPlayer.create(LocalContext.current, R.raw.alarm)
    var totalTime = 300L * 1000L
    var currentTime by remember {
        mutableStateOf(totalTime)
    }
    var value by remember {
        mutableStateOf(1f)
    }
    var isRunning by remember{
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = currentTime, key2 = isRunning) {
        if (currentTime > 0 && isRunning) {
            delay(100L)
            currentTime -= 100L
            value = currentTime / totalTime.toFloat()
        }else if(currentTime == 0L) {
            mediaPlayer.start()
        }
    }
    Column (modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(
            text = "Timer",
            fontSize = 50.sp,
            fontWeight = FontWeight.W800,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = modifier
        )
        Text(
            text = convertToTime(currentTime),
            fontSize = 45.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = modifier
        )
        Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)){
            Button(
                colors = ButtonColors(
                    contentColor = Color.White,
                    containerColor = Color.White,
                    disabledContentColor = Color.Gray,
                    disabledContainerColor = Color.Gray
                ),
                onClick = {
                    if (currentTime < 0L) {
                        currentTime = totalTime
                        isRunning = true
                    }else if(currentTime == 0L){
                        mediaPlayer.stop()
                        currentTime = totalTime
                        isRunning = !isRunning
                    }else {
                        isRunning = !isRunning
                    }
                }
            ) {
                Text(
                    text = if (currentTime >= 0 && isRunning) "Stop" else "Start",
                    color = Color.Black
                )
            }
            Button(
                colors = ButtonColors(
                    contentColor = Color.White,
                    containerColor = Color.White,
                    disabledContentColor = Color.Gray,
                    disabledContainerColor = Color.Gray
                ),
                onClick = {
                    Log.d(":::app", "currentTime: ${currentTime}")
                    currentTime += (60L * 1000L)
                }
            ) {
                Text(
                    text = "Add",
                    color = Color.Black
                )
            }
        }
    }
}

fun convertToTime(time: Long):String {
    val date = Date(time)
    val format = SimpleDateFormat("mm:ss")
    return format.format(date)
}




