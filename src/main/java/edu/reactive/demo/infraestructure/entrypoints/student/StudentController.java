package edu.reactive.demo.infraestructure.entrypoints.student;

import edu.reactive.demo.domain.model.identification.Identification;
import edu.reactive.demo.domain.model.student.Student;
import edu.reactive.demo.domain.usecase.student.StudentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component
@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {

    private final StudentUseCase studentUseCase;
    private static final String QUEUE_NAME_STRING = "queueName";

    @GetMapping("/{id}")
    public Mono<Student> getStudent(@PathVariable Integer id) {
        return studentUseCase.getStudentById(id);
    }

    @GetMapping("/{documentType}/{documentNumber}")
    public Mono<Student> getStudentByDocumentType(@PathVariable String documentType,
                                                  @PathVariable String documentNumber) {
        return studentUseCase.getStudentByDocument(Identification.builder()
                .documentType(documentType)
                .documentNumber(documentNumber).build());
    }

    @PostMapping("")
    public Mono<Student> saveStudent(@RequestBody Student student) {
        return studentUseCase.saveStudent(student);
    }

    @PutMapping("/update/{id}")
    public Mono<Student> updateStudent(@RequestBody Student student, @PathVariable Integer id) {
        return studentUseCase.udpateStudent(student, id);

    }

    @DeleteMapping("")
    public Mono<Void> deleteStudent(@PathVariable Integer id) {
        return studentUseCase.deleteStudent(id);
    }

    @GetMapping("/all")
    public Flux<Student> getAllStudents() {
        return studentUseCase.findAllStudents();
    }

    @GetMapping("/topic-kakfa/{topic}")
    public Mono<List<Student>> getStudentFromKafkaTopic(@PathVariable String topic) {
        return studentUseCase.getAllStudentsFromKafka(topic);
    }

    @PostMapping("/aws/createQueue")
    public Mono<String> createQueueForStudents(@RequestBody Map<String, Object> requestBody) {
        return studentUseCase.createQueueForStudents((String) requestBody.get(QUEUE_NAME_STRING));
    }

    @PostMapping("/aws/postMessageQueue/{queueName}")
    public Mono<String> postStudentMessageInQueue(@RequestBody Student student, @PathVariable String queueName) {
        return studentUseCase.postStudentMessageInQueue(student, queueName);
    }

    @PostMapping("/aws/deleteStudentFromQueueByDocumentTypeAndDocumentNumber")
    public Mono<Student> deleteStudentFromQueueByDocumentTypeAndDocumentNumber(@RequestBody Map<String, Object> requestBody) {
        return studentUseCase.deleteStudentFromQueueByDocumentTypeAndDocumentNumber((String) requestBody.get(QUEUE_NAME_STRING),
                (Integer) requestBody.get("maxNumberMessages"),
                (Integer) requestBody.get("waitTimeSeconds"),
                (String) requestBody.get("documentType"),
                (String) requestBody.get("documentNumber"));
    }

    @PostMapping("/from-kakfa-to-sqs/{topic}")
    public Mono<List<Student>> getStudentFromKafkaTopicAndPublishInSQSQueue(@PathVariable String topic, @RequestBody Map<String, Object> requestBody) {
        return studentUseCase.getStudentFromKafkaTopicAndPublishInSQSQueueV2(topic, (String) requestBody.get(QUEUE_NAME_STRING));
    }

    @GetMapping("/sqs/{queueName}")
    public Mono<List<Student>> getStudentFromSQSQueue(@PathVariable String queueName,
                                                      @RequestParam(name = "maxNumberMessages") Integer maxNumberMessages,
                                                      @RequestParam(name = "waitTimeSeconds") Integer waitTimeSeconds) {
        return studentUseCase.receiveMessagesFromQueue(queueName, maxNumberMessages, waitTimeSeconds);
    }

}
