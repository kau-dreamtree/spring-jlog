package com.jlog.domain;

public class Fixtures {

    public static Member member() {
        return member(0L);
    }

    public static Member member(long id) {
        return member(id, 0);
    }

    public static Member member(long id, long expense) {
        return new Member(id, "member" + id, expense);
    }

    public static Room room() {
        return room(1L);
    }

    public static Room room(long id) {
        return room(id, "room" + id);
    }

    public static Room room(long id, String code) {
        return room(id, code, id, id + 1L);
    }

    public static Room room(long id, String code, long member1Id, long member2Id) {
        var members = new Members(member(member1Id), member(member2Id));
        return new Room(id, code, members);
    }

    public static Log log() {
        return log(1L);
    }

    public static Log log(long id) {
        return log(id, 0, null);
    }

    public static Log log(long id, long expense) {
        return log(id, expense, null);
    }

    public static Log log(long id, long expense, String memo) {
        var member = member(id);
        var room = room(id);
        return new Log(id, room, member, expense, memo);
    }
}
