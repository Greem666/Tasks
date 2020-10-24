package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.mail.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailScheduler {
    private static final String SUBJECT = "Tasks: Once a day email";
    private final SimpleEmailService simpleEmailService;
    private final TaskRepository taskRepository;
    private final AdminConfig adminConfig;

//    @Scheduled(cron = "0 0 10 * * *")
    @Scheduled(fixedDelay = 86400000)
    public void sendInformationEmail() {
        simpleEmailService.send(
                new Mail(
                        adminConfig.getAdminMail(),
                        SUBJECT,
                        messageFormatter()
                ),
                SimpleEmailService.ONCE_A_DAY_EMAIL);
    }

    private String messageFormatter() {
        long size = taskRepository.count();
        return String.format("Currently in database you've got: %d task%s.", size, size == 1 ? "" : "s");
    }
}
