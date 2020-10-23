package com.crud.tasks.controller;

import com.crud.tasks.domain.trello.CreatedTrelloCardDto;
import com.crud.tasks.domain.trello.TrelloBoardDto;
import com.crud.tasks.domain.trello.TrelloCardDto;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.facade.TrelloFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/trello/")
@CrossOrigin("*")
public class TrelloController {
    @Autowired
    private TrelloFacade trelloFacade;

    private static final String NECESSARY_PHRASE = "kodilla";

    @RequestMapping(method = RequestMethod.GET, value = "/boards")
    public List<TrelloBoardDto> getTrelloBoards() {
        return trelloFacade.fetchTrelloBoards();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/cards")
    public CreatedTrelloCardDto createNewCard(@RequestBody TrelloCardDto trelloCardDto) {
        return trelloFacade.createCard(trelloCardDto);
    }
}
