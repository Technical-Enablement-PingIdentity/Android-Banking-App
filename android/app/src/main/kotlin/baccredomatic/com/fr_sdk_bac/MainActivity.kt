package baccredomatic.com.fr_sdk_bac
//package com.example.app

import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.android.FlutterFragmentActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity: FlutterFragmentActivity() {
    private val CHANNEL = "forgerock.com/SampleBridge"

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        val frSampleBridgeChannel = FRAuthSampleBridge(this)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
                call, result ->
            // Note: this method is invoked on the main thread.
            when (call.method) {
                "frAuthStart" -> frSampleBridgeChannel.start(result)
                "login" -> frSampleBridgeChannel.login(result)
                "register" -> frSampleBridgeChannel.register(result)
                "logout" -> frSampleBridgeChannel.logout(result)
                "next" -> {
                    if (call.arguments is String) {
                        frSampleBridgeChannel.next(call.arguments as String, result)
                    } else {
                        result.error("500", "Arguments not parsed correctly", null)
                    }
                }
                "callEndpoint" -> {
                    if (call.arguments is ArrayList<*>) {
                        val args = call.arguments as ArrayList<String>
                        frSampleBridgeChannel.callEndpoint(args[0], args[1], args[2], args[3], result)
                    } else {
                        result.error("500", "Arguments not parsed correctly", null)
                    }
                }
                "getUserInfo" -> frSampleBridgeChannel.getUserInfo(result)
                "webAuthentication" -> {
                    val args = call.arguments as ArrayList<String>;
                    frSampleBridgeChannel.webAuthentication(result, args[0]);
                }
                else -> {
                    result.notImplemented()
                }
            }
        }
    }
}
