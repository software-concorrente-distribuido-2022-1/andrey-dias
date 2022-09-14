package crud.dto;

import lombok.Data;

@Data
public class AuthenticatedRequest<T> {
    private final Long userId;
    private final String token;
    private final T payload;

    public AuthenticatedRequest(Long userId, String token, T payload) {
        this.userId = userId;
        this.token = token;
        this.payload = payload;
    }
}
