package com.crud.tasks.mapper;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.domain.TrelloList;
import com.crud.tasks.domain.trello.TrelloBoardDto;
import com.crud.tasks.domain.trello.TrelloCardDto;
import com.crud.tasks.domain.trello.TrelloListDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloMapperTestSuite {

    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    public void shouldMapCardToCardDto() {
        // Given
        TrelloCard trelloCard = new TrelloCard("test", "test_description", "123", "1");

        // When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);

        // Then
        assertEquals("test", trelloCardDto.getName());
        assertEquals("test_description", trelloCardDto.getDescription());
        assertEquals("123", trelloCardDto.getPos());
        assertEquals("1", trelloCardDto.getListId());
    }

    @Test
    public void shouldMapListToListDto() {
        // Given
        List<TrelloList> trelloList = Arrays.asList(
                new TrelloList("1", "test1", true),
                new TrelloList("2", "test2", false));

        // When
        List<TrelloListDto> trelloListDto = trelloMapper.mapToListDto(trelloList);

        // Then
        assertEquals(2, trelloListDto.size());

        assertEquals("1", trelloListDto.get(0).getId());
        assertEquals("test1", trelloListDto.get(0).getName());
        assertTrue(trelloListDto.get(0).isClosed());

        assertEquals("2", trelloListDto.get(1).getId());
        assertEquals("test2", trelloListDto.get(1).getName());
        assertFalse(trelloListDto.get(1).isClosed());
    }

    @Test
    public void shouldMapBoardToBoardDto() {
        // Given
        List<TrelloList> trelloList1 = Collections.singletonList(
                new TrelloList("1", "test1", true));
        List<TrelloList> trelloList2 = Arrays.asList(
                new TrelloList("2", "test2", false),
                new TrelloList("3", "test3", true));

        List<TrelloBoard> trelloBoards = Arrays.asList(
                new TrelloBoard("1", "test1", trelloList1),
                new TrelloBoard("2", "test2", trelloList2));

        // When
        List<TrelloBoardDto> trelloBoardDtos = trelloMapper.mapToBoardsDto(trelloBoards);

        // Then
        assertEquals(2, trelloBoardDtos.size());

        assertEquals("1", trelloBoardDtos.get(0).getId());
        assertEquals("test1", trelloBoardDtos.get(0).getName());

        assertEquals(1, trelloBoardDtos.get(0).getLists().size());
        assertEquals("1", trelloBoardDtos.get(0).getLists().get(0).getId());
        assertEquals("test1", trelloBoardDtos.get(0).getLists().get(0).getName());
        assertTrue(trelloBoardDtos.get(0).getLists().get(0).isClosed());

        assertEquals("2", trelloBoardDtos.get(1).getId());
        assertEquals("test2", trelloBoardDtos.get(1).getName());

        assertEquals(2, trelloBoardDtos.get(1).getLists().size());
        assertEquals("2", trelloBoardDtos.get(1).getLists().get(0).getId());
        assertEquals("test2", trelloBoardDtos.get(1).getLists().get(0).getName());
        assertFalse(trelloBoardDtos.get(1).getLists().get(0).isClosed());

        assertEquals("3", trelloBoardDtos.get(1).getLists().get(1).getId());
        assertEquals("test3", trelloBoardDtos.get(1).getLists().get(1).getName());
        assertTrue(trelloBoardDtos.get(1).getLists().get(1).isClosed());
    }

    @Test
    public void shouldMapCardDtoToCard() {
        TrelloCardDto trelloCardDto = new TrelloCardDto("test", "test_description", "123", "1");

        // When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);

        // Then
        assertEquals("test", trelloCard.getName());
        assertEquals("test_description", trelloCard.getDescription());
        assertEquals("123", trelloCard.getPos());
        assertEquals("1", trelloCard.getListId());
    }

    @Test
    public void shouldMapListDtoToList() {
        List<TrelloListDto> trelloListDto = Arrays.asList(
                new TrelloListDto("1", "test1", true),
                new TrelloListDto("2", "test2", false));

        // When
        List<TrelloList> trelloList = trelloMapper.mapToList(trelloListDto);

        // Then
        assertEquals(2, trelloList.size());

        assertEquals("1", trelloList.get(0).getId());
        assertEquals("test1", trelloList.get(0).getName());
        assertTrue(trelloList.get(0).isClosed());

        assertEquals("2", trelloList.get(1).getId());
        assertEquals("test2", trelloList.get(1).getName());
        assertFalse(trelloList.get(1).isClosed());
    }

    @Test
    public void shouldMapBoardDtoToBoard() {
        // Given
        List<TrelloListDto> trelloListDto1 = Collections.singletonList(
                new TrelloListDto("1", "test1", true));
        List<TrelloListDto> trelloListDto2 = Arrays.asList(
                new TrelloListDto("2", "test2", false),
                new TrelloListDto("3", "test3", true));

        List<TrelloBoardDto> trelloBoardsDtos = Arrays.asList(
                new TrelloBoardDto("1", "test1", trelloListDto1),
                new TrelloBoardDto("2", "test2", trelloListDto2));

        // When
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardsDtos);

        // Then
        assertEquals(2, trelloBoards.size());

        assertEquals("1", trelloBoards.get(0).getId());
        assertEquals("test1", trelloBoards.get(0).getName());

        assertEquals(1, trelloBoards.get(0).getList().size());
        assertEquals("1", trelloBoards.get(0).getList().get(0).getId());
        assertEquals("test1", trelloBoards.get(0).getList().get(0).getName());
        assertTrue(trelloBoards.get(0).getList().get(0).isClosed());

        assertEquals("2", trelloBoards.get(1).getId());
        assertEquals("test2", trelloBoards.get(1).getName());

        assertEquals(2, trelloBoards.get(1).getList().size());
        assertEquals("2", trelloBoards.get(1).getList().get(0).getId());
        assertEquals("test2", trelloBoards.get(1).getList().get(0).getName());
        assertFalse(trelloBoards.get(1).getList().get(0).isClosed());

        assertEquals("3", trelloBoards.get(1).getList().get(1).getId());
        assertEquals("test3", trelloBoards.get(1).getList().get(1).getName());
        assertTrue(trelloBoards.get(1).getList().get(1).isClosed());
    }
}
