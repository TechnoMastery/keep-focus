package net.minheur.keepFocus.defs;

import net.minheur.keepFocus.KeepFocus;
import net.minheur.keepFocus.data.SaverHandler;
import net.minheur.potoflux.actionRuns.regs.ActionRun;
import net.minheur.potoflux.loader.mod.events.RegisterRunsEvent;
import net.minheur.potoflux.registry.RegistryList;
import net.minheur.potoflux.utils.SmartSupplier;
import net.minheur.potoflux.utils.ressourcelocation.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class Runs {

    private static final RegistryList<ActionRun> LIST_CLOSE = new RegistryList<>();
    private static final RegistryList<ActionRun> LIST_START_LOGIC = new RegistryList<>();

    public static final SmartSupplier<ActionRun> LOAD = LIST_START_LOGIC.add(() -> new ActionRun(
            new ResourceLocation(KeepFocus.MOD_ID, "load"), SaverHandler::load
    ));
    public static final SmartSupplier<ActionRun> SAVE = LIST_CLOSE.add(() -> new ActionRun(
            new ResourceLocation(KeepFocus.MOD_ID, "save"), SaverHandler::save
    ));

    public static void register(@NotNull RegisterRunsEvent event) {
        LIST_CLOSE.register(event.closeReg);
        LIST_START_LOGIC.register(event.startLogicReg);
    }

}
