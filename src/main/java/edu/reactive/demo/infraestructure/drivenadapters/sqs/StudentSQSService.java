package edu.reactive.demo.infraestructure.drivenadapters.sqs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.*;
import edu.reactive.demo.domain.model.student.Student;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
public class StudentSQSService {

    private final AmazonSQS clientSQS;

    public StudentSQSService(AmazonSQS clientSQS) {
        this.clientSQS = clientSQS;
    }

    public String createStandardQueue(String queueName) {
        CreateQueueRequest createQueueRequest = new CreateQueueRequest(queueName);
        return clientSQS.createQueue(createQueueRequest).getQueueUrl();
    }

    private String getQueueUrl(String queueName) {
        return clientSQS.getQueueUrl(queueName).getQueueUrl();
    }

    public String publishStandardQueueMessage(String queueName, Integer delaySeconds, Student student) {
        Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();

        messageAttributes.put("id",
                new MessageAttributeValue()
                        .withStringValue(Optional.ofNullable(student.getId()).orElse(-301).toString())
                        .withDataType("Number"));
        messageAttributes.put("documentType",
                new MessageAttributeValue()
                        .withStringValue(student.getDocumentType())
                        .withDataType("String"));
        messageAttributes.put("documentNumber",
                new MessageAttributeValue()
                        .withStringValue(student.getDocumentNumber())
                        .withDataType("String"));
        messageAttributes.put("name",
                new MessageAttributeValue()
                        .withStringValue(student.getName())
                        .withDataType("String"));
        messageAttributes.put("groupID",
                new MessageAttributeValue()
                        .withStringValue(Optional.ofNullable(student.getGroupID()).orElse(0).toString())
                        .withDataType("Number"));
        messageAttributes.put("guardianID",
                new MessageAttributeValue()
                        .withStringValue(Optional.ofNullable(student.getGuardianID()).orElse(0).toString())
                        .withDataType("Number"));
        messageAttributes.put("teacherID",
                new MessageAttributeValue()
                        .withStringValue(Optional.ofNullable(student.getTeacherID()).orElse(0).toString())
                        .withDataType("Number"));


        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(this.getQueueUrl(queueName))
                .withMessageBody(student.getDocumentType() + student.getDocumentNumber())
                .withDelaySeconds(delaySeconds)
                .withMessageAttributes(messageAttributes);

        return clientSQS.sendMessage(sendMessageRequest).getMessageId();
    }

    public void publishStandardQueueMessage(String queueName, Integer delaySeconds, List<Student> students) {
        for (Student student : students) {
            publishStandardQueueMessage(queueName, delaySeconds, student);
        }
    }


    public Mono<List<Student>> getStudentsFromSQSQueue(String queueName, Integer maxNumberMessages, Integer waitTimeSeconds) {
        List<Student> students = new ArrayList<>();
        for (Message message : receiveMessagesFromQueue(queueName, maxNumberMessages, waitTimeSeconds)) {
            if (!message.getMessageAttributes().isEmpty()) {
                Student student = Student.builder().id(Integer.valueOf(message.getMessageAttributes().get("id")
                                .getStringValue()))
                        .documentType(message.getMessageAttributes().get("documentType").getStringValue())
                        .documentNumber(message.getMessageAttributes().get("documentNumber").getStringValue())
                        .name(message.getMessageAttributes().get("name").getStringValue())
                        .guardianID(Integer.valueOf(message.getMessageAttributes().get("guardianID").getStringValue()))
                        .teacherID(Integer.valueOf(message.getMessageAttributes().get("teacherID").getStringValue()))
                        .groupID(Integer.valueOf(message.getMessageAttributes().get("groupID").getStringValue()))
                        .build();
                students.add(student);
            }
        }
        return Mono.just(students);
    }

    public List<Message> receiveMessagesFromQueue(String queueName, Integer maxNumberMessages, Integer waitTimeSeconds) {
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(this.getQueueUrl(queueName))
                .withMaxNumberOfMessages(maxNumberMessages)
                .withMessageAttributeNames(List.of("All"))
                .withWaitTimeSeconds(waitTimeSeconds);
        return clientSQS.receiveMessage(receiveMessageRequest).getMessages();
    }

    public Mono<Student> deleteStudentMessageInQueue(String queueName, Integer maxNumberMessages,
                                                     Integer waitTimeSeconds, String documentType, String documentNumber) {
        var studentMessages = receiveMessagesFromQueue(queueName, maxNumberMessages, waitTimeSeconds);
        for (Message message : studentMessages) {
            if (!message.getMessageAttributes().isEmpty() &&
                    (message.getMessageAttributes().get("documentType").getStringValue().equals(documentType)
                            && message.getMessageAttributes().get("documentNumber").getStringValue().equals(documentNumber))) {
                Student student = Student.builder().id(Integer.valueOf(message.getMessageAttributes().get("id")
                                .getStringValue()))
                        .documentType(message.getMessageAttributes().get("documentType").getStringValue())
                        .documentNumber(message.getMessageAttributes().get("documentNumber").getStringValue())
                        .name(message.getMessageAttributes().get("name").getStringValue())
                        .guardianID(Integer.valueOf(message.getMessageAttributes().get("guardianID").getStringValue()))
                        .teacherID(Integer.valueOf(message.getMessageAttributes().get("teacherID").getStringValue()))
                        .groupID(Integer.valueOf(message.getMessageAttributes().get("groupID").getStringValue()))
                        .build();
                clientSQS.deleteMessage(this.getQueueUrl(queueName), message.getReceiptHandle());
                return Mono.just(student);
            }
        }
        return Mono.empty();
    }
}
