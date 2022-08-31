package work.lclpnet.creativepower;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import work.lclpnet.creativepower.core.Palette;
import work.lclpnet.creativepower.core.PaletteConvertible;
import work.lclpnet.creativepower.core.io.PacketSerializable;

import javax.annotation.Nullable;
import java.io.Serial;
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Collectors;

public class GenericStructure<T> extends HashMap<BlockPos, T> implements PacketSerializable,
        PaletteConvertible<BlockPos, T> {

    @Serial
    private static final long serialVersionUID = -3603111702143747097L;
    public static final Comparator<BlockPos> POS_COMPARATOR = (a, b) -> {
        ChunkPos ca = new ChunkPos(a), cb = new ChunkPos(b);
        int ma = ca.getRegionX() + ca.getRegionZ();
        int mb = cb.getRegionX() + ca.getRegionZ();

        int rmx_diff = ma - mb;
        if (rmx_diff != 0) return rmx_diff;

        ma = ca.x + ca.z;
        mb = cb.x + cb.z;

        return ma - mb;
    };
    public static final byte TOK_BEGIN_BLOCKS = 1,
            TOK_END_BLOCKS = 2,
            TOK_SKIP_BLOCKS = 3,
            TOK_SKIP_ROWS = 4;

    /**
     Reduce packet size by defining block positions implicitly.
     The contents are encompassed by a cuboid bounding box.
     The box is divided into individual y-layers (vertical axis) which are divided themselves into
     rows of z-layers, resulting in one-dimensional block rows.
     The idea is, that each row is serialized in ascending order.
     The index of each row is defined by z + y * l, where l is the length (block in z axis) of the bounding box.
     If one or more rows are empty, they are skipped, which is denoted by a special SKIP value followed by count.
     In each row, the positions are iterated in ascending order.
     If positions are empty, they are skipped, similarly how rows are skipped.

     This strategy may not be perfect, but should work overall.
     TODO: maybe enhance this with principal component analysis in the future...

     @param buf The buffer to write to.
     */
    @Override
    public void writeTo(PacketByteBuf buf) {
        final var palette = toPalette();
        final var mapping = palette.mapping();
        buf.writeInt(mapping.size());

        mapping.entrySet().stream()
                .sorted(Comparator.comparingInt(Entry::getValue))
                .forEachOrdered(entry -> {
                    // id = offset
                    buf.writeString(entry.getKey().toString());
                });

        final var content = palette.content();

        // calculate bounds
        final var bounds = calculateBounds();
        if (bounds == null) return;

        // write anchor (minimum position)
        final BlockPos min = new BlockPos(bounds.getMinX(), bounds.getMinY(), bounds.getMinZ());
        buf.writeBlockPos(min);

        // determine rows
        final int w = bounds.getBlockCountX(),
                h = bounds.getBlockCountY(),
                l = bounds.getBlockCountZ();

        var rows = content.entrySet().stream()
                .collect(Collectors.groupingByConcurrent(e -> {
                    var pos = e.getKey();
                    // build int index
                    return (pos.getZ() - min.getZ()) + (pos.getY() - min.getY()) * l;
                }));

        // serialize rows
        final int minX = min.getX();
        final int rowCount = l * h;

        int lastRowIdx = 0;

        for (int rowIdx : rows.keySet()) {
            // handle skipped rows
            {
                final int skippedRows = rowIdx - lastRowIdx;
                if (skippedRows > 0) {
                    buf.writeByte(TOK_SKIP_ROWS);
                    buf.writeVarInt(skippedRows);
                }

                lastRowIdx = rowIdx;
            }

            // rowEntries is never empty!
            final var rowEntries = rows.get(rowIdx);

            // sort rowEntries by x value
            rowEntries.sort(Comparator.comparingInt(e -> e.getKey().getX()));

            // serialize rowEntries

            boolean writingBlocks = false;

            int lastX = minX;
            for (var e : rowEntries) {
                final int x = e.getKey().getX() - minX;

                // handle skipped blocks
                {
                    final int skippedBlocks = x - lastX;
                    if (skippedBlocks > 0) {
                        // exit block writing mode, if active
                        if (writingBlocks) {
                            buf.writeByte(TOK_END_BLOCKS);
                        }

                        buf.writeByte(TOK_SKIP_BLOCKS);
                        buf.writeVarInt(skippedBlocks);
                    }
                    lastX = x;
                }

                // enter block writing mode, if not active already
                if (!writingBlocks) {
                    writingBlocks = true;
                    buf.writeByte(TOK_BEGIN_BLOCKS);
                }

                // write block mapping id
                buf.writeVarInt(e.getValue());
            }

            // exit block writing mode, if active
            if (writingBlocks) {
                buf.writeByte(TOK_END_BLOCKS);
            }

            // write remaining block skips
            int tailingBlocks = w - 1 - lastX;
            if (tailingBlocks > 0) {
                buf.writeByte(TOK_SKIP_BLOCKS);
                buf.writeVarInt(tailingBlocks);
            }
        }

        // write remaining row skips
        int tailingRows = rows.isEmpty() ? rowCount : (rowCount - 1 - lastRowIdx);
        if (tailingRows > 0) {
            buf.writeByte(TOK_SKIP_ROWS);
            buf.writeVarInt(tailingRows);
        }
    }

    @Nullable
    public BlockBox calculateBounds() {
        if (isEmpty()) return null;

        final var it = keySet().iterator();
        BlockPos pos = it.next();

        int x = pos.getX(), y = pos.getY(), z = pos.getZ();
        int minX = x, minY = y, minZ = z, maxX = x, maxY = y, maxZ = z;

        while (it.hasNext()) {
            pos = it.next();
            x = pos.getX();
            y = pos.getY();
            z = pos.getZ();

            if (x > maxX) maxX = x;
            else if (x < minX) minX = x;

            if (y > maxY) maxY = y;
            else if (y < minY) minY = y;

            if (z > maxZ) maxZ = z;
            else if (z < minZ) minZ = z;
        }

        return new BlockBox(minX, minY, minZ, maxX, maxY, maxZ);
    }

    @Override
    public Palette<BlockPos, T> toPalette() {
        return Palette.from(this);
    }
}
