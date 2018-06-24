# Android Noise Sensor
This application takes the microphone amplitude once per second and sends it to localhost:4444 via tcp, formatted using the InfluxDB line-syntax.

# Usage
## Prerequisites
Start the entire data pipeline. This includes both InfluxDB and kafka, as well as their connectors. In this example, we've used flume between our phone and kafka, and telegraf between kafka and InfluxDB. You can also start grafana, but it isn't required until you want to actually display your data.

## Install the application
Build the app in Android Studio (or install the apk from *./target*) and install it on your phone. Connect the phone to your computer and run
```
$ adb reverse tcp:4444 tcp:4444
```
This command forwards the tcp:4444-port on your phone to tcp:4444 on your computer. This enables us to (in our case) send data from our application to kafka via flume. *Note* that you need to run this command every time you unplug your phone, but not if you only restart the application.

Now, run the NoiseSensor app on your phone while leaving it plugged in, and data **should** start to flow to InfluxDB.
