package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.task.Task;
import com.crud.tasks.domain.task.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class  MailCreatorService {

    private final AdminConfig adminConfig;
    private final DbService dbService;
    private final TaskMapper taskMapper;
    @Qualifier("templateEngine")
    private final TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {

        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("button", "Visit website");
        context.setVariable("tasks_url", "http://localhost:8888/tasks_frontend");

        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("message", message);
        context.setVariable("preview_message", "New Trello card creation");

        context.setVariable("goodbye_phrase", "Kind regards,");
        context.setVariable("goodbye_message", "CrudApp Team");

        context.setVariable("company_name", adminConfig.getCompanyName());
        context.setVariable("company_phone", adminConfig.getCompanyPhone());
        context.setVariable("company_email", adminConfig.getAdminMail());
        context.setVariable("company_goal", adminConfig.getCompanyGoal());

        context.setVariable("show_button", false);
        context.setVariable("is_friend", false);

        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_functionality", functionality);

        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String buildOnceADayEmail(String message) {

        List<TaskDto> tasks = taskMapper.mapToTaskDtoList(dbService.getAllTasks());

        Context context = new Context();

        context.setVariable("preview_message", "Once a day email");

        context.setVariable("current_datetime",
                LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("dd-LLL-yyyy: kk:mm"))
        );
        context.setVariable("admin_config", adminConfig);

        context.setVariable("message", message);

        context.setVariable("button", "Manage tasks");
        context.setVariable("tasks_url", "http://localhost:8888/tasks_frontend");

        context.setVariable("tasks", tasks);

        context.setVariable("awesome_goodbye_phrase", "Stay awesome,");
        context.setVariable("sad_goodbye_phrase", "Do better next time,");
        context.setVariable("goodbye_message", "CrudApp Team");

        context.setVariable("company_details", adminConfig);

        return templateEngine.process("mail/tasks-in-database-mail", context);
    }
}
