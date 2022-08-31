package work.lclpnet.creativepower.core.io;

import net.minecraft.network.PacketByteBuf;

import java.io.IOException;

public interface PacketSerializable {

    void writeTo(PacketByteBuf buf) throws IOException;
}
