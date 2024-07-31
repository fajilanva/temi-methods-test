package com.example.method_channel_test

import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle;

import com.robotemi.sdk.Robot
import com.robotemi.sdk.TtsRequest

class MainActivity: FlutterActivity() {
    private val CHANNEL = "temi-robot"
    private lateinit var robot: Robot;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        robot = Robot.getInstance();
    }

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
            MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
            call, result ->
            when (call.method) {
                "speakTemi" -> {
                    try {
                        val text = call.argument<String>("speech") ?: "";
                        val isSuccessful = temiSpeak(text);
                        result.success(isSuccessful);
                    } catch ( e:Exception ) {
                        println(e);
                    } 
                }
                "goToTemi" -> {
                    try {
                        val text = call.argument<String>("location") ?: "";
                        val isSuccessful = temiGoTo(location)
                        result.success(isSuccessful)
                    } catch (e:Exception) {
                        println(e)
                    }
                }
                "getLocationsTemi" -> {
                    try {
                        val locations = temiGoTo()
                        result.success(locations)
                    } catch (e:Exception) {
                        println(e)
                    }
                }
            }
            
        }
    }

    private fun temiSpeak(text: String): Boolean {
        val ttsRequest = TtsRequest.create(text, false);
        robot.speak(ttsRequest);
        return true;
    }

    private fun temiGoTo(location: String): Boolean {
        robot.goTo(location, false, null, null)
        return True
    }

    private fun temiGetLocations(): MutableList<String> {
        val locations: MutableList<String> = mutableListOf();
        for(location in robot.locations) {
            locations.add(location);
        }
        return locations;
    }
}