//package com.sanem.share.ong.producers;
//
//import com.sanem.share.ong.dtos.auth.EmailDTO;
//import com.sanem.share.ong.infra.security.JwtUtils;
//import com.sanem.share.ong.models.User;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//@Component
//public class UserProducer {
//
//    private final RabbitTemplate rabbitTemplate;
//    private final JwtUtils jwtUtils;
//
//    public UserProducer(RabbitTemplate rabbitTemplate, JwtUtils jwtUtils) {
//        this.rabbitTemplate = rabbitTemplate;
//        this.jwtUtils = jwtUtils;
//    }
//
//    @Value("${broker.queue.email.name}")
//    private String routingKey;
//
//    public void sendConfirmationEmail(User user) {
//        String token = jwtUtils.generateConfirmationJwt(user);
//        String link = "http://localhost:8082/auth/confirm-account?token=" + token;
//
//        String subject = "Account Confirmation";
//        String body = "Hello, " + user.getFirstName() + ",\n\n" +
//                "Click the link below to confirm your account:\n" +
//                link + "\n\n" +
//                "This link expires in 1 hour.";
//
//        EmailDTO emailDTO = new EmailDTO(user.getId(), user.getCpf(), subject, body);
//
//        rabbitTemplate.convertAndSend("", routingKey, emailDTO);
//    }
//
//    public void sendForgotPassword(User user) {
//        var token = jwtUtils.generateJwt(user);
//
//        String resetPasswordUrl = "http://localhost:8088/reset-password?token=" + token;
//
//        String subject = "Forgot Password?";
//        String body = "Hello, " + user.getFirstName() + ",\n\n" +
//                "We received a request to reset your password. You can reset it by clicking on the link below:\n\n" +
//                resetPasswordUrl + "\n\n" +
//                "If you didn’t ask to reset your password, you can safely ignore this cpf.\n\n" +
//                "Best regards,\n";
//
//        EmailDTO emailDTO = new EmailDTO(user.getId(), user.getCpf(), subject, body);
//
//        rabbitTemplate.convertAndSend("", routingKey, emailDTO);
//    }
//
//    public void sendConfirmationEmailVerifier(User user) {
//
//        String link = "http://localhost:8082/auth/request-confirmation?id=" + user.getId();
//
//        String subject = "Account Confirmation";
//        String body = "Hello, " + user.getFirstName() + ",\n\n" +
//                "You didnt confirm your account yet.\n" +
//                "To request a confirmation again click in this link below:\n" +
//                link;
//
//        EmailDTO emailDTO = new EmailDTO(user.getId(), user.getCpf(), subject, body);
//
//        rabbitTemplate.convertAndSend("", routingKey, emailDTO);
//    }
//
//}
