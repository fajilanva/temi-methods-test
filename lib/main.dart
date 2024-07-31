import 'package:flutter/material.dart';
import 'package:method_channel_test/temirobot.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});
  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  final TextEditingController _controller = TextEditingController();
  List<String> locations = [];
  TemiRobot robot = TemiRobot();

  void getLocations() async {
    List<String> newLocations = (await robot.getLocationsTemi())!;
    setState(() {
      locations = newLocations;
    });
  }

  @override
  Widget build(BuildContext context) {
    double height = MediaQuery.of(context).size.height;
    double width = MediaQuery.of(context).size.width;
    return Scaffold(
        appBar: AppBar(
          backgroundColor: Theme.of(context).colorScheme.inversePrimary,
          title: Text(widget.title),
        ),
        body: Center(
          child: Padding(
            padding: const EdgeInsets.all(10),
            child: Column(
              mainAxisSize: MainAxisSize.min,
              children: [
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Container(
                      width: 200,
                      child: TextFormField(
                          controller: _controller,
                          decoration: const InputDecoration(
                              border: OutlineInputBorder())),
                    ),
                    const SizedBox(
                      width: 10,
                    ),
                    ElevatedButton(
                        onPressed: () => robot.speakTemi(_controller.text),
                        child: const Text('Speak')),
                  ],
                ),
                const SizedBox(
                  height: 10,
                ),
                ElevatedButton(
                    onPressed: () => robot.goToTemi("home base"),
                    child: const Text('Go to Home Base')),
                const SizedBox(
                  height: 10,
                ),
                ElevatedButton(
                    onPressed: getLocations,
                    child: const Text("Get All Locations")),
                const SizedBox(
                  height: 10,
                ),
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    for (String location in locations)
                      ElevatedButton(
                          onPressed: () => robot.goToTemi(location),
                          child: Text(location))
                  ],
                )
              ],
            ),
          ),
        ));
  }
}
