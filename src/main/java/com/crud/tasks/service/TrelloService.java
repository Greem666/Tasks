package com.crud.tasks.service;

import com.crud.tasks.domain.mail.Mail;
import com.crud.tasks.domain.trello.CreatedTrelloCard;
import com.crud.tasks.domain.trello.TrelloBoardDto;
import com.crud.tasks.domain.trello.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import com.crud.tasks.trello.config.TrelloConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrelloService {

    @Autowired
    private TrelloClient trelloClient;

    @Autowired
    private SimpleEmailService emailService;

    public List<TrelloBoardDto> fetchTrelloBoards() {
        return trelloClient.getTrelloBoards();
    }

    public CreatedTrelloCard createTrelloCard(final TrelloCardDto trelloCardDto) {
        CreatedTrelloCard newCard = trelloClient.createNewCard(trelloCardDto);

        emailService.send(
                new Mail("",
                        "Tasks: New Trello card",
                        "New card: " + trelloCardDto.getName() + " has been created on your Trello account",
                        null)
        );

        return newCard;
    }
}
