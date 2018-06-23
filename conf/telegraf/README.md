# Telegraf configuration file
Telegraf uses a single configuration-file and lets the developer choose which "plugins" to use in different instances. In *telegraf.conf*, we define two outputs (kafka and influxdb) and one input (kafka). We also include an MQTT-input, which we haven't tested.

**mqtt --> kafka**
```
telegraf --input-filter mqtt_consumer --output-filter kafka
```

**kafka --> influxdb**
```
telegraf --input-filter kafka_consumer --output-filter influxdb
```
