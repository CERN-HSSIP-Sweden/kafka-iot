package simple_tests;

import com.google.common.io.Resources;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class SimpleConsumerTest {
    public static void main(String[] args) { new SimpleConsumerTest().run(); }

    private void run() {
        KafkaConsumer<String, String> consumer;
        try (InputStream propsInputStream =
                     Resources.getResource("consumer-test1.props")
                     .openStream())
        {
            Properties props = new Properties();
            props.load(propsInputStream);
            consumer = new KafkaConsumer<>(props);

            Scanner sc = new Scanner(System.in);
            System.out.println("Write one topic per line to subscribe to:");

            ArrayList<String> topics = new ArrayList<>();
            while (true) {
                String input = sc.nextLine();
                if (input.equals("exit")) break;
                topics.add(input);
            }

            consumer.subscribe(topics);
            System.out.println("Now listening to topics ...");
            while (true) {
                ConsumerRecords<String, String> records =
                        consumer.poll(200);
                for (ConsumerRecord<String, String> record: records) {
                    System.out.println(record.timestamp() + ": "
                            + record.topic() + ": "
                            + record.value());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
