package net.bdew.wurm.archeogroup;

import javassist.ClassPool;
import org.gotti.wurmunlimited.modloader.classhooks.HookManager;
import org.gotti.wurmunlimited.modloader.interfaces.PreInitable;
import org.gotti.wurmunlimited.modloader.interfaces.WurmClientMod;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ArcheoGroupMod implements WurmClientMod, PreInitable {
    private static final Logger logger = Logger.getLogger("ArcheoGroupMod");

    public static void logException(String msg, Throwable e) {
        if (logger != null)
            logger.log(Level.SEVERE, msg, e);
    }

    @Override
    public void preInit() {
        try {
            ClassPool classPool = HookManager.getInstance().getClassPool();

            classPool.getCtClass("com.wurmonline.client.game.inventory.InventoryMetaItem")
                    .getMethod("updateDisplayName", "()V")
                    .insertAfter(
                            "if (this.groupName.contains(\"fragment\") && this.groupName.contains(\"[\")) {" +
                                    "this.groupName = this.groupName.substring(0, this.groupName.indexOf('[') - 1) + this.groupName.substring(this.groupName.indexOf(']')+1);" +
                                    "}"
                    );

            logger.fine("Loaded");
        } catch (Throwable e) {
            logException("Error loading mod", e);
        }
    }
}
