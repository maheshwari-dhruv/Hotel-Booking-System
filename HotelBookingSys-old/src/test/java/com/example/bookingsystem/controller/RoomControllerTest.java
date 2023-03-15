package com.example.bookingsystem.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

import com.example.bookingsystem.domain.Room;
import com.example.bookingsystem.service.RoomService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class RoomControllerTest {
    @InjectMocks
    private RoomController roomController;

    @Mock
    private RoomService roomService;

    @Test
    void testCreateRoom() throws Exception {
        Room room = new Room();
        room.setAdults(1);
        room.setBookings(new ArrayList<>());
        room.setChildren(1);
        room.setCost(34.5);
        room.setId(123L);
        room.setIsReserved(true);
        room.setType("Balcony");

        String content = (new ObjectMapper()).writeValueAsString(room);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v2/room/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(roomController)
                .build()
                .perform(requestBuilder);

        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void testGetAllRooms_noRoomFound() throws Exception {
        when(roomService.getAllRooms()).thenReturn(new ArrayList<>());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v2/room/view/all");
        MockMvcBuilders.standaloneSetup(roomController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetAllRooms_roomFound() throws Exception {
        Room room = new Room();
        room.setAdults(1);
        room.setBookings(new ArrayList<>());
        room.setChildren(1);
        room.setCost(34.5);
        room.setId(123L);
        room.setIsReserved(true);
        room.setType("Balcony");

        List<Room> rooms = new ArrayList<>();
        rooms.add(room);

        when(this.roomService.getAllRooms()).thenReturn(rooms);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v2/room/view/all");
        MockMvcBuilders.standaloneSetup(this.roomController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[{\"id\":123,\"isReserved\":true,\"cost\":34.5,\"adults\":1,\"children\":1,\"type\":\"Balcony\"}]"));
    }

    @Test
    void testUpdateRoom() throws Exception {
        Room room = new Room();
        room.setAdults(1);
        room.setBookings(new ArrayList<>());
        room.setChildren(1);
        room.setCost(10.0d);
        room.setId(123L);
        room.setIsReserved(true);
        room.setType("Type");
        when(this.roomService.updateRoom((Long) any(), (Room) any())).thenReturn(room);

        Room room1 = new Room();
        room1.setAdults(1);
        room1.setBookings(new ArrayList<>());
        room1.setChildren(1);
        room1.setCost(10.0d);
        room1.setId(123L);
        room1.setIsReserved(true);
        room1.setType("Type");
        String content = (new ObjectMapper()).writeValueAsString(room1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v2/room/update/{roomId}", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.roomController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":123,\"isReserved\":true,\"cost\":10.0,\"adults\":1,\"children\":1,\"type\":\"Type\"}"));
    }

    @Test
    void testDeleteRoom() throws Exception {
        when(this.roomService.deleteRoomById((Long) any())).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v2/room/delete/{roomId}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.roomController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testGetAllAvailable() throws Exception {
        when(this.roomService.getAllAvailableRooms()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v2/room/view/all/available");
        MockMvcBuilders.standaloneSetup(this.roomController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetAllReserved() throws Exception {
        when(this.roomService.getAllReservedRooms()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v2/room/view/all/reserved");
        MockMvcBuilders.standaloneSetup(this.roomController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetRoomByRoomId() throws Exception {
        Room room = new Room();
        room.setAdults(1);
        room.setBookings(new ArrayList<>());
        room.setChildren(1);
        room.setCost(34.5);
        room.setId(123L);
        room.setIsReserved(true);
        room.setType("Balcony");
        when(this.roomService.getRoomById(any())).thenReturn(room);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v2/room/view/id/{roomId}", 123L);
        MockMvcBuilders.standaloneSetup(this.roomController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":123,\"isReserved\":true,\"cost\":34.5,\"adults\":1,\"children\":1,\"type\":\"Balcony\"}"));
    }

    @Test
    void testGetRoomByRoomOccupancy() throws Exception {
        when(this.roomService.getRoomsByOccupancy(anyInt(), anyInt())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v2/room/view/all/occupancy/adults/{adults}/children/{children}", 1, 1);
        MockMvcBuilders.standaloneSetup(this.roomController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetRoomByRoomType() throws Exception {
        when(this.roomService.getRoomsByType(any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v2/room/view/all/type/{roomType}",
                "Balcony");
        MockMvcBuilders.standaloneSetup(this.roomController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetRoomsBetweenPrice() throws Exception {
        when(this.roomService.getRoomsBetweenPriceRange(any(), any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v2/room/view/all/price/{startingPrice}&{endingPrice}", 10.0, 100.0);
        MockMvcBuilders.standaloneSetup(this.roomController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}

