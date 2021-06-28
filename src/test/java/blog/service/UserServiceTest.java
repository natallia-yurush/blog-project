package blog.service;

import by.nyurush.blog.entity.User;
import by.nyurush.blog.repository.UserRepository;
import by.nyurush.blog.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    UserServiceImpl userService;

    @Test
    public void findByEmailTest() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Natallia");
        user.setLastName("Yurush");
        user.setEmail("natallia.yurush@gmail.com");
        user.setPassword("admin");
        user.setCreatedAt(LocalDate.now());

        when(userRepository.findByEmail("natallia.yurush@gmail.com")).thenReturn(Optional.of(user));
        User actualUser = userService.findByEmail("natallia.yurush@gmail.com");

        assertEquals(user, actualUser);

    }

}
