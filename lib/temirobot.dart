import 'dart:async';
import 'package:flutter/services.dart';

class TemiRobot {
  static const platform = MethodChannel('temi-robot');

  Future<void> speakTemi(String speech) async {
    print("speaking");
    try {
      await platform.invokeMethod<bool>('speakTemi', {"speech": speech});
    } on PlatformException catch (e) {
      print(e);
    }
  }

  Future<void> goToTemi(String location) async {
    print("going to location");
    try {
      await platform.invokeMethod<bool>('goToTemi', {"location": location});
    } on PlatformException catch (e) {
      print(e);
    }
  }

  Future<List<String>?> getLocationsTemi() async {
    print("showing locations");
    try {
      // List<String> locations = ["home base", "kitchen", "meeting room", "HR"];
      List<String>? locations = await platform.invokeMethod<List<String>>('getLocationsTemi');
      return locations;
    } on PlatformException catch (e) {
      print(e);
      return null;
    }
  }
}
