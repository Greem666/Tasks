package com.crud.tasks.trello.client;

import com.crud.tasks.domain.TrelloBoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Component
public class TrelloClient {

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;

    @Value("${trello.app.key}")
    private String trelloAppKey;

    @Value("${trello.app.token}")
    private String trelloAppToken;

    @Value("${trello.app.username}")
    private String trelloAppUsername;

    @Autowired
    private RestTemplate restTemplate;

    public List<TrelloBoardDto> getTrelloBoards() {
        Optional<TrelloBoardDto[]> boardsResponse = Optional.ofNullable(
                                    restTemplate.getForObject(buildGetAllUserBoardsURL(), TrelloBoardDto[].class)
        );

        return Arrays.asList(boardsResponse.orElse(new TrelloBoardDto[0]));
    }

    private URI buildGetAllUserBoardsURL() {
        return UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "members/" + trelloAppUsername + "/boards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloAppToken)
                .queryParam("fields", "name,id")
                .build().encode().toUri();
    }
}
