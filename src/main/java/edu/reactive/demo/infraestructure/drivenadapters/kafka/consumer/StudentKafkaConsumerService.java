package edu.reactive.demo.infraestructure.drivenadapters.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.reactive.demo.domain.model.student.Student;
import edu.reactive.demo.infraestructure.drivenadapters.kafka.config.KafkaConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentKafkaConsumerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final long DEFAULT_OFFSET = 0;
    private static final int DEFAULT_PARTITION = 0;
    private static final String JSON_DOUBLE_QUOTES = "\"";
    private static final String JSON_SINGLE_QUOTE = "'";
    private static final Boolean GET_ALL_MESSAGES = Boolean.TRUE;

    public StudentKafkaConsumerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public List<Student> getAllStudentsFromKafka(String topic) {
        KafkaConfig kafkaConfig = new KafkaConfig();
        kafkaTemplate.setConsumerFactory(kafkaConfig.consumerFactory());

        List<Student> allStudents = new ArrayList<>();
        long offset = DEFAULT_OFFSET;
        ConsumerRecord<String, String> message;
        while (GET_ALL_MESSAGES) {
            message = kafkaTemplate.receive(topic, DEFAULT_PARTITION, offset);
            if (message == null) {
                break;
            }
            try {
                allStudents.add(objectMapper.readValue(message.value()
                        .replace(JSON_SINGLE_QUOTE, JSON_DOUBLE_QUOTES), Student.class));
                offset = message.offset() + 1;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return allStudents;
    }
}
