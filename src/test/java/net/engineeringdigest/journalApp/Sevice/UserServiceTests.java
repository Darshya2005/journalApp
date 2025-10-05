package net.engineeringdigest.journalApp.Sevice;

import net.engineeringdigest.journalApp.Repository.UserRepository;
import net.engineeringdigest.journalApp.entity.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Disabled
    @ParameterizedTest
    @ValueSource(strings = {
        "Extnsbl",
            "Witcher",
            "Extnsbl41"
    })
    public void testFindByUsername(String name){
        assertNotNull(userRepository.findByUsername(name));
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,2,3",
            "2,10,12",
            "2,3,5",
    })
    public void test(int a,int b,int expected){
        assertEquals(expected,a+b);
    }
}
