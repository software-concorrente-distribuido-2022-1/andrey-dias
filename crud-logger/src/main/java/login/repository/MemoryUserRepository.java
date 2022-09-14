package login.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import login.domain.User;
import login.domain.UserType;

public class MemoryUserRepository implements UserRepository {
    private final List<User> users;

    public MemoryUserRepository() {
        this.users = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            this.users.add(new User((long) i, "teste" + i + "@teste.com", "blablabla", UserType.CLIENT));
        }
    }

    @Override
    public synchronized Optional<User> findByEmail(String email) {
        return users
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }
}
