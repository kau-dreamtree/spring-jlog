package dreamtree.jlog.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
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

import dreamtree.jlog.dto.RoomCreateRequest;
import dreamtree.jlog.dto.RoomJoinRequest;
import dreamtree.jlog.dto.RoomResponse;
import dreamtree.jlog.service.RoomService;

@WebMvcTest(RoomController.class)
class RoomControllerTest {

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
            var request = new RoomCreateRequest("jlog-name");
            doReturn(roomCode).when(roomService).create(request);

            var expected = objectMapper.writeValueAsString(new RoomResponse(roomCode));
            mvc.perform(post("/api/room")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isCreated())
                    .andExpect(content().json(expected));
        }

        @Test
        @DisplayName("Request to create a room without body then respond 400.")
        void create_exception() throws Exception {
            mvc.perform(post("/api/room")
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

            mvc.perform(put("/api/room")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Request to join a room without body and respond 400.")
        void join_exception() throws Exception {
            mvc.perform(put("/api/room")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }
    }
}
