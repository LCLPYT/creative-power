package work.lclpnet.creativepower.core;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import org.junit.jupiter.api.Test;
import work.lclpnet.creativepower.GenericStructure;

import java.io.Serial;

public class GenericStructureTest {

    @Test
    void testWriteTo() {
        var struct = new MockStructure();
        struct.put(new BlockPos(0, 0, 0), "Stone");
        struct.put(new BlockPos(1, 0, 0), "Stone");
        struct.put(new BlockPos(1, 2, 0), "Dirt");

        final var buffer = new PacketByteBuf(Unpooled.buffer());
        struct.writeTo(buffer);
        buffer.release();
    }

    private static class MockStructure extends GenericStructure<String> {
        @Serial
        private static final long serialVersionUID = -3934267076210390636L;
    }
}
