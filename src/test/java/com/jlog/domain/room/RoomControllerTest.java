package com.jlog.domain.room;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(RoomController.class)
class RoomControllerTest {

    private static final String BASE_URL = "/api/room";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RoomService roomService;

    @Nested
    @DisplayName("Request to create a room.")
    class Create {

        @Test
        @DisplayName("Request to create a room then respond 200 with a room code in the body.")
        void create() throws Exception {
            var roomCode = "test-room-code";
            var request = new RoomCreateRequest("username");
            doReturn(roomCode).when(roomService).create(request);

            var expected = objectMapper.writeValueAsString(new RoomCreateResponse(roomCode));
            mvc.perform(post(BASE_URL)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isCreated())
                    .andExpect(content().json(expected));
        }

        @Test
        @DisplayName("Request to create a room without body then respond 400.")
        void create_exception() throws Exception {
            mvc.perform(post(BASE_URL)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("Request to join a room.")
    class Join {

        @Test
        @DisplayName("Request to join a room and respond 200.")
        void join() throws Exception {
            var request = new RoomJoinRequest("12345678", "jlog-name");
            doNothing().when(roomService).join(request);

            mvc.perform(put(BASE_URL)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Request to join a room without body and respond 400.")
        void join_exception() throws Exception {
            mvc.perform(put(BASE_URL)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("Request to get balance")
    class Balance {

        @Test
        void balance() throws Exception {
            var response = new RoomBalanceResponse(1000L, "username");
            doReturn(response).when(roomService).getBalance("roomCode", "username");

            mvc.perform(get("/api/v1/rooms")
                    .param("roomCode", "roomCode")
                    .param("username", "username")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(response)));
        }
    }
}
