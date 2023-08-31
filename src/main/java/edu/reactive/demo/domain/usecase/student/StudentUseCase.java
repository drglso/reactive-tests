package edu.reactive.demo.domain.usecase.student;

import edu.reactive.demo.domain.model.identification.Identification;
import edu.reactive.demo.domain.model.student.Student;
import edu.reactive.demo.infraestructure.drivenadapters.kafka.consumer.StudentKafkaConsumerService;
import edu.reactive.demo.infraestructure.drivenadapters.repository.student.StudentRepository;
import edu.reactive.demo.infraestructure.drivenadapters.sqs.StudentSQSService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StudentUseCase {
    private final StudentRepository studentRepository;
    private final StudentKafkaConsumerService studentKafkaConsumerService;
    private final StudentSQSService studentSQSService;
    private static final Integer DEFAULT_DELAY = 2;

    public Mono<Student> getStudentById(Integer id) {
        return studentRepository.findById(id)
                .doOnError(throwable -> {
                    new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR).getMostSpecificCause();
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Student not found").getMostSpecificCause()));
    }

    public Mono<Student> saveStudent(Student student) {
        return studentRepository.save(student)
                .doOnError(throwable -> {
                    new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR).getMostSpecificCause();
                });
    }

    public Mono<Student> getStudentByDocument(Identification identification) {
        return studentRepository.findStudentByDocument(identification.getDocumentType(),
                        identification.getDocumentNumber())
                .doOnError(throwable -> {
                    new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR).getMostSpecificCause();
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Student not found").getMostSpecificCause()));
    }

    public Mono<Void> deleteStudent(Integer id) {
        return studentRepository.deleteById(id)
                .doOnError(throwable -> {
                    new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR).getMostSpecificCause();
                });
    }

    public Flux<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    public Mono<Student> udpateStudent(Student studentToUpdate, Integer id) {
        return studentRepository.findById(id)
                .flatMap(existingStudent ->
                        studentRepository.save(existingStudent.update(studentToUpdate, id)))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Student not found").getMostSpecificCause()))
                .doOnError(throwable -> {
                    new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR).getMostSpecificCause();
                });
    }

    public Mono<List<Student>> getAllStudentsFromKafka(String topic) {
        return Mono.just(studentKafkaConsumerService.getAllStudentsFromKafka(topic));
    }

    public Mono<String> createQueueForStudents(String queueName) {
        return Mono.just(studentSQSService.createStandardQueue(queueName));
    }

    public Mono<String> postStudentMessageInQueue(Student student, String queueName) {
        return Mono.just(studentSQSService.publishStandardQueueMessage(queueName, DEFAULT_DELAY, student));
    }

    public Mono<Student> deleteStudentFromQueueByDocumentTypeAndDocumentNumber(String queueName,
                                                                               Integer maxNumberMessages,
                                                                               Integer waitTimeSeconds,
                                                                               String documentType,
                                                                               String documentNumber) {
        return studentSQSService.deleteStudentMessageInQueue(queueName, maxNumberMessages, waitTimeSeconds,
                documentType, documentNumber);
    }

    public Mono<List<Student>> getStudentFromKafkaTopicAndPublishInSQSQueueV2(String topic, String queueName) {
        return getAllStudentsFromKafka(topic)
                .flatMap(students -> {
                    studentSQSService.createStandardQueue(queueName);
                    studentSQSService.publishStandardQueueMessage(queueName, DEFAULT_DELAY, students);
                    return Mono.just(students);
                })
                .switchIfEmpty(Mono.defer(() -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No students were found in the kafka topic").getMostSpecificCause())))
                .doOnError(throwable -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
                        .getMostSpecificCause());
    }
}
