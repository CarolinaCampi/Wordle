import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordleTest {

    @Test
    public void testRemoveAccents() {
        Spanish game = new Spanish();
        assertEquals("habia", game.removeAccents("había"));
        assertEquals("nandu", game.removeAccents("ñandu"));
    }
}
