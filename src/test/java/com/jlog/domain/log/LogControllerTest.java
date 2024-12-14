package com.jlog.domain.log;

import static java.time.LocalDateTime.now;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlog.domain.member.Member;
import com.jlog.domain.member.Members;
import com.jlog.domain.room.Room;

@WebMvcTest(LogController.class)
class LogControllerTest {

    private static final String BASE_URL = "/api/log";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LogService logService;

    @Nested
    @DisplayName("Request to create a log")
    class Create {

        @Test
        @DisplayName("Request to create a log responds 201")
        void create() throws Exception {
            var request = new LogRequest(null, "room_1234", "member1", 1000L, null);

            mvc.perform(post(BASE_URL)
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isCreated());
        }

        @Test
        @DisplayName("Request without body responds 400")
        void create_exception() throws Exception {
            mvc.perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("Request to get logs")
    class Get {

        @Test
        @DisplayName("Request to get logs with outpay responds 200 with body")
        void getLogsWithOutpay() throws Exception {
            var member1 = new Member("zeus");
            var member2 = new Member("lizzy");
            var members = new Members(member1, member2);
            var room = new Room(1L, "room_1234", members);
            var logResponses = List.of(
                    new LogResponse(1L, room.getCode(), member1.getName(), 1000L, "memo1", now(), now()),
                    new LogResponse(2L, room.getCode(), member2.getName(), 2000L, "memo2", now(), now())
            );
            var expect = LogsWithOutpayResponse.of(room, logResponses);
            doReturn(expect).when(logService).findAll(any(), any());

            var parameters = "?room_code=room_1234&username=zeus";
            mvc.perform(get(BASE_URL + parameters)
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(content().json(objectMapper.writeValueAsString(expect)));
        }

        @ParameterizedTest
        @ValueSource(strings = {"?room_code=room_1234", "?username=zeus", ""})
        @DisplayName("Request with invalid parameters responds 400")
        void getLogsWithOutpay_exception(String parameters) throws Exception {
            mvc.perform(get(BASE_URL + parameters)
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @DisplayName("Request to get log slice responds 200 with body")
        @ParameterizedTest
        @MethodSource
        void getLogs(String parameters) throws Exception {
            var member1 = new Member("zeus");
            var member2 = new Member("lizzy");
            var members = new Members(member1, member2);
            var room = new Room(1L, "room_1234", members);
            var logResponses = List.of(
                    new LogResponseV1(1L, room.getCode(), member1.getName(), 1000L, "memo1", now(), now()),
                    new LogResponseV1(2L, room.getCode(), member2.getName(), 2000L, "memo2", now(), now())
            );
            var expect = new SliceImpl<>(logResponses);
            doReturn(expect).when(logService).findByRoom(any(), any(), any());

            mvc.perform(get(parameters)
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(content().json(objectMapper.writeValueAsString(expect)));
        }

        static Stream<String> getLogs() {
            var param1 = "/api/v1/logs?room_code=room_1234&username=zeus";
            var param2 = "/api/v1/logs?room_code=room_1234&username=zeus&page=0";
            var param3 = "/api/v1/logs?room_code=room_1234&username=zeus&page=0&size=10&sort=createdAt";
            var param4 = "/api/v1/logs?room_code=room_1234&username=zeus&page=0&size=10&sort=createdAt,desc";
            return Stream.of(param1, param2, param3, param4);
        }
    }

    @Nested
    @DisplayName("Request to update a log")
    class Update {

        @Test
        @DisplayName("Request to update a log responds 200")
        void update() throws Exception {
            var request = new LogRequest(null, "room_1234", "member1", 1000L, "new memo");
            mvc.perform(put(BASE_URL)
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Request without body responds 400")
        void update_exception() throws Exception {
            mvc.perform(put(BASE_URL)
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("Request to delete a log")
    class Delete {

        @Test
        @DisplayName("Request to delete a log responds 204")
        void delete_success() throws Exception {
            var request = new LogRequest(1L, "room_1234", "username", 1000L, "memo");

            mvc.perform(delete(BASE_URL)
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("Request without body responds 400")
        void delete_exception() throws Exception {
            mvc.perform(delete(BASE_URL)
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }
    }
}
