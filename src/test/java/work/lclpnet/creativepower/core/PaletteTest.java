package work.lclpnet.creativepower.core;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PaletteTest {

    @Test
    void testIds() {
        String tom = "Tom", alice = "Alice", bob = "Bob";

        final var pal = Palette.from(Map.of(
                tom, "Banana",
                alice, "Apple",
                bob, "Banana",
                "Mario", "Lime"
        ));

        final int tomId = pal.getId(tom);
        assertNotEquals(Palette.NIL, tomId);

        final int aliceId = pal.getId(alice);
        assertNotEquals(Palette.NIL, aliceId);
        assertNotEquals(tomId, aliceId);

        final int bobId = pal.getId(bob);
        assertEquals(tomId, bobId);

        final int unknownId = pal.getId("Someone not in set");
        assertEquals(Palette.NIL, unknownId);
    }

    @Test
    void testBijection() {
        final var orig = Map.of(
                "Tom", "Banana",
                "Alice", "Apple",
                "Bob", "Banana",
                "Mario", "Lime"
        );

        final var pal = Palette.from(orig);

        pal.content().keySet().forEach(key -> {
            final int id = pal.getId(key);
            assertNotEquals(Palette.NIL, id);

            final String val = pal.getMapping(id);
            assertNotNull(val);

            assertEquals(orig.get(key), val);
        });
    }
}
