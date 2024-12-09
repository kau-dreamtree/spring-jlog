package com.jlog.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.jlog.repository.FakeLogRepository;
import com.jlog.repository.FakeMemberRepository;
import com.jlog.repository.FakeRoomRepository;
import com.jlog.repository.LogRepository;
import com.jlog.repository.MemberRepository;
import com.jlog.repository.RoomRepository;

class LogServiceTest {

    private LogService sut;

    private LogRepository logRepository;
    private RoomRepository roomRepository;
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository = new FakeMemberRepository();
        roomRepository = new FakeRoomRepository();
        logRepository = new FakeLogRepository();
        sut = new LogService(logRepository, roomRepository, memberRepository);
    }

    @Test
    void create() {
    }

    @Test
    void findAll() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}
