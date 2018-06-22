# Flume configuration files
Since flume uses specific configuration files for each instance, we have two files here.

## kafka_input.conf
*kafka_input.conf* takes data from a kafka-topic and outputs it to the log. We haven't been able to push the data to influxdb yet.

## kafka_output.conf
*kafka_output.conf* puts data from netcat (tcp-port on localhost) and pushes it to a kafka-topic. This is what we used in the android-demo.
