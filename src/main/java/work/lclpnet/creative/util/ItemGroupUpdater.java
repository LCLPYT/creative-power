package work.lclpnet.creative.util;

public class ItemGroupUpdater {

    private ItemGroupUpdater() {}

    private static boolean dirty = false;

    public static void setDirty(boolean dirty) {
        ItemGroupUpdater.dirty = dirty;
    }

    public static boolean isDirty() {
        return dirty;
    }
}
