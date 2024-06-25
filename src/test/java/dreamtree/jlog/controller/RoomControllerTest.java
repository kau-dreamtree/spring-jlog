package dreamtree.jlog.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import dreamtree.jlog.dto.RoomCreateRequest;
import dreamtree.jlog.dto.RoomJoinRequest;
import dreamtree.jlog.service.RoomService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RoomController.class)
class RoomControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RoomService roomService;

    @MockBean
    private JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Nested
    @DisplayName("방을 생성한다.")
    class Create {

        @Test
        void create() throws Exception {
            var request = new RoomCreateRequest("jlog-name");
            var content = objectMapper.writeValueAsString(request);
            when(roomService.create(request)).thenReturn("test-code");
            mvc.perform(post("/api/room")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content))
                    .andExpect(status().isCreated());
        }

        @Test
        void create_exception() throws Exception {
            mvc.perform(post("/api/room"))
                    .andExpect(status().is4xxClientError());
        }
    }

    @Nested
    @DisplayName("방에 참여한다.")
    class Join {

        @Test
        void join() throws Exception {
            var request = new RoomJoinRequest("12345678", "jlog-name");
            var content = objectMapper.writeValueAsString(request);
            doNothing().when(roomService).join(request);
            mvc.perform(put("/api/room")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content))
                    .andExpect(status().isOk());
        }

        @Test
        void join_exception() throws Exception {
            mvc.perform(put("/api/room"))
                    .andExpect(status().is4xxClientError());
        }
    }
}
