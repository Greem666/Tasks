package com.crud.tasks.controller;

import com.crud.tasks.domain.trello.CreatedTrelloCard;
import com.crud.tasks.domain.trello.TrelloBoardDto;
import com.crud.tasks.domain.trello.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/trello/")
public class TrelloController {
    @Autowired
    private TrelloClient trelloClient;

    private static final String NECESSARY_PHRASE = "kodilla";

    @RequestMapping(method = RequestMethod.GET, value = "getTrelloBoards")
    public void getTrelloBoards() {
        List<TrelloBoardDto> trelloBoardsDto = trelloClient.getTrelloBoards();
//        trelloBoardsDto.stream()
//                .filter(board -> board.getName().toLowerCase().contains(NECESSARY_PHRASE))
//                .map(board -> "Name: " + board.getName() + "\nId: " + board.getId() + "\n")
//                .forEach(System.out::println);

        trelloBoardsDto.forEach(boardDto -> {
            System.out.println(boardDto.getName() + " - " + boardDto.getId());
            System.out.println("This board contains the following lists:");
            boardDto.getLists().forEach(listDto -> System.out.println(listDto.getName() + " - " + listDto.getId() + " - " + listDto.isClosed()));
            });
    }

    @RequestMapping(method = RequestMethod.POST, value = "createTrelloCard")
    public CreatedTrelloCard createNewCard(@RequestBody TrelloCardDto trelloCardDto) {
        return trelloClient.createNewCard(trelloCardDto);
    }
}
