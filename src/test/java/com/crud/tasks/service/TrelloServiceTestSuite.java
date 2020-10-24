package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.mail.Mail;
import com.crud.tasks.domain.trello.CreatedTrelloCardDto;
import com.crud.tasks.domain.trello.TrelloBoardDto;
import com.crud.tasks.domain.trello.TrelloCardDto;
import com.crud.tasks.domain.trello.TrelloListDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTestSuite {

    @InjectMocks
    private TrelloService trelloService;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private AdminConfig adminConfig;

    @Mock
    private SimpleEmailService emailService;

    @Test
    public void fetchTrelloBoards() {
        // Given
        List<TrelloListDto> trelloListDtos = new ArrayList<>();
        trelloListDtos.add(new TrelloListDto("2", "test list", false));

        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(new TrelloBoardDto("1", "test board", trelloListDtos));

        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoardDtos);

        // When
        List<TrelloBoardDto> retrievedTrelloBoardDtos = trelloService.fetchTrelloBoards();

        // Then
        assertEquals(1, retrievedTrelloBoardDtos.size());
        assertEquals("1", retrievedTrelloBoardDtos.get(0).getId());
        assertEquals("test board", retrievedTrelloBoardDtos.get(0).getName());
        assertEquals(1, retrievedTrelloBoardDtos.get(0).getLists().size());
        assertEquals("2", retrievedTrelloBoardDtos.get(0).getLists().get(0).getId());
        assertEquals("test list", retrievedTrelloBoardDtos.get(0).getLists().get(0).getName());
    }

    @Test
    public void createTrelloCard() {
        // Given
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "test card", "test desc", "top", "666");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto(
                "1", "test card", "http://test.com", null);

        when(trelloClient.createNewCard(any(TrelloCardDto.class))).thenReturn(createdTrelloCardDto);

        // When
        CreatedTrelloCardDto returnedTrelloCardDto = trelloService.createTrelloCard(trelloCardDto);

        // Then
        assertEquals("1", returnedTrelloCardDto.getId());
        assertEquals("test card", returnedTrelloCardDto.getName());
        assertEquals("http://test.com", returnedTrelloCardDto.getShortUrl());
        assertNull(returnedTrelloCardDto.getTrelloBadges());
        verify(emailService, times(1)).send(any(Mail.class), eq(SimpleEmailService.NEW_CARD_EMAIL));
    }
}