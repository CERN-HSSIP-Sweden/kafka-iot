package simple_tests;

import com.google.common.io.Resources;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;
import java.util.Scanner;


public class SimpleProducerTest {
    public static void main(String[] args) {
        new SimpleProducerTest().run();
    }

    private void run() {
        try (InputStream propsInputStream =
                     Resources.getResource("producer-test1.props")
                    .openStream())
        {
            Properties props = new Properties();
            props.load(propsInputStream);
            KafkaProducer<String, String> producer = new KafkaProducer<>(props);

            Scanner sc = new Scanner(System.in);
            while (true) {
                System.out.print("\n" +
                        "[1] New message\n" +
                        "[2] Send messages\n" +
                        "[3] Exit\n" +
                        ": "
                );
                String input = sc.nextLine();

                if (input.equals("1")) {
                    System.out.print("Topic: ");
                    String topic = sc.nextLine();
                    System.out.print("Message: ");
                    String msg = sc.nextLine();
                    producer.send(new ProducerRecord<String, String>(topic, msg));
                }

                else if (input.equals("2")) {
                    producer.flush();
                    System.out.println("Messages sent!");
                } else if (input.equals("3")) {
                    sc.close();
                    producer.close();
                    break;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
