package dreamtree.jlog;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class MyTest {

    @Test
    void test() {
        Set<String> people = new HashSet<>();
        Optional<String> jisu = Optional.of("jisu");
        Optional<String> nil = Optional.ofNullable(null);
        people.add(jisu.get());
        people.add(nil.get());
        System.out.println(people);
    }

    private static class Person {
        String name;
        int age;

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }
}
