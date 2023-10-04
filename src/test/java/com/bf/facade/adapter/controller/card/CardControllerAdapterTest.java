package com.bf.facade.adapter.controller.card;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.bf.facade.adapter.rest.exception.BadRequestRestCardException;
import com.bf.facade.adapter.rest.exception.EmptyOrNullBodyRestClientException;
import com.bf.facade.application.port.in.CreateCard;
import com.bf.facade.application.port.in.GetCard;
import com.bf.facade.config.ErrorCode;
import com.bf.facade.mocks.CardMock;
import com.bf.facade.utils.UtilsByTest;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@DisplayName("CardControllerAdapterTest")
@WebMvcTest(CardControllerAdapter.class)
@ExtendWith(MockitoExtension.class)
class CardControllerAdapterTest {
    private static final String GET_CARD= "/api/v1.0/card/{cardNumber}";
    private static final String GET_INTERNAL_CARD= "/api/v1.0/card/internal/{cardNumber}";

    @Autowired
    private MockMvc restRequest;

    @MockBean
    private GetCard getCard;

    @MockBean
    private CreateCard createCard;

    @Test
    @DisplayName("When Get card is success")
    void getCardSuccessExternal() throws Exception {
        when(getCard.getCard(CardMock.STRING_VALUE1)).thenReturn(CardMock.getCardDomain());
        restRequest.perform(MockMvcRequestBuilders.get(GET_CARD, CardMock.STRING_VALUE1))
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("When Get card is fail bad request")
    void getCardBadRequestExternal() throws Exception {
        when(getCard.getCard(anyString())).thenThrow(new BadRequestRestCardException(ErrorCode.CARD_NOT_FOUND));
        restRequest.perform(MockMvcRequestBuilders.get(GET_CARD, CardMock.STRING_VALUE1))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("When Get card is fail EmptyOrNullBodyRestClientException")
    void getCardEmptyOrNullBodyExternal() throws Exception {
        when(getCard.getCard(anyString())).thenThrow(new EmptyOrNullBodyRestClientException(ErrorCode.CARD_NOT_FOUND));
        restRequest.perform(MockMvcRequestBuilders.get(GET_CARD, CardMock.STRING_VALUE1))
                .andExpect(status().isNotFound());
    }
    @Test
    @DisplayName("When Get card internal is success")
    void getCardSuccessInternal() throws Exception {
        when(getCard.getCard(CardMock.STRING_VALUE1)).thenReturn(CardMock.getCardDomain());
        restRequest.perform(MockMvcRequestBuilders.get(GET_INTERNAL_CARD, CardMock.STRING_VALUE1))
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("When card is create success")
    void createCardSucessInternal() throws Exception {
        when(createCard.createCard(CardMock.getCardDomain())).thenReturn(0);
        restRequest.perform(MockMvcRequestBuilders.post(String.format(CardMock.CREATE_CARD))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(UtilsByTest.JsonToString(CardMock.getCardRest())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(UtilsByTest.JsonToString(CardMock.getRestResponseCreateCard())));
    }
}