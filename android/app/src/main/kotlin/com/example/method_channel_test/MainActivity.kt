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
                    if (call.method == "speakTemi") {
                        val text = call.argument<String>("speech")
                        val isSuccessful = temiSpeak(text)
                        result.success(isSuccessful)
                    } catch (e:Exception) {
                        println(e)
                    } 
                }
                "goToTemi" -> {
                    try {
                        val isSuccessful = temiGoTo()
                        result.success(isSuccessful)
                    } catch (e:Exception) {
                        println(e)
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
        val ttsRequest = TtsRequest.create(text, true);
        robot.speak(ttsRequest)
        return True
    }

    private fun temiGoTo(): Boolean {
        robot.goTo("home base", false, null, null)
        return True
    }

    private fun temiGetLocations(): List<String> {
        List<String> locations = robot.getLocations()
        return locations
    }
}}