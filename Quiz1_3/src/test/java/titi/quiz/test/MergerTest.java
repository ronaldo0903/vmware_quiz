package titi.quiz.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import titi.quiz.collections.Merger;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class MergerTest {
    private Collection<User> users1;
    private Collection<User> users2;
    private AgeComparator _comparator = new AgeComparator();

    @BeforeEach
    public void prepareData() {
        users1 = Arrays.asList(new User("Bob", 14), new User("Eason", 18), new User("TiTi", 32));
        users2 = Arrays.asList(new User("Brown", 14), new User("David", 20), new User("Steven", 42));
    }

    @Test
    public void testMergeUsersByName() {
        Collection<User> expectedUsers = Arrays.asList(new User("Bob", 14),
                new User("Brown", 14),
                new User("David", 20),
                new User("Eason", 18),
                new User("Steven", 42),
                new User("TiTi", 32));
        Collection<User> mergedUsers = new Merger(null).merge(users1, users2);
        Assertions.assertIterableEquals(expectedUsers, mergedUsers);

    }

    @Test
    public void testMergeUsersByAge() {
        Collection<User> expectedUsers = Arrays.asList(new User("Bob", 14),
                new User("Brown", 14),
                new User("Eason", 18),
                new User("David", 20),
                new User("TiTi", 32),
                new User("Steven", 42));

        Collection<User> mergedUsers = new Merger(_comparator).merge(users1, users2);
        Assertions.assertIterableEquals(expectedUsers, mergedUsers);
    }

    @Test
    public void testMergeWithOneEmpty() {
        Collection<User> mergedUsers = new Merger(_comparator).merge(users1, Collections.emptyList());
        Assertions.assertIterableEquals(users1, mergedUsers);
    }
}
