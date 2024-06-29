package dreamtree.jlog.dto;

public record LogRequest(
        Long id,
        String roomCode,
        String username,
        Long expense,
        String memo
) {
    public LogRequest(String roomCode, String username) {
        this(null, roomCode, username, null, null);
    }

    public LogRequest(long id, String roomCode, String username) {
        this(id, roomCode, username, null, null);
    }
}
