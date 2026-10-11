package net.minheur.keepFocus;

import net.minheur.keepFocus.defs.FocusStylesheets;
import net.minheur.keepFocus.defs.Runs;
import net.minheur.potoflux.PotoFlux;
import net.minheur.potoflux.loader.PotoFluxLoadingContext;
import net.minheur.potoflux.loader.mod.Mod;
import net.minheur.potoflux.loader.mod.ModEventBus;
import net.minheur.potoflux.loader.mod.events.RegisterLangEvent;
import net.minheur.potoflux.logger.LogCategories;
import net.minheur.potoflux.logger.PtfLogger;
import net.minheur.keepFocus.defs.Tabs;
import net.minheur.keepFocus.defs.FocusTranslations;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

@Mod(modId = KeepFocus.MOD_ID, version = "1.3", compatibleVersionUrl = "https://technomastery.github.io/PotoFluxAppData/modVersions/keepFocus.json")
public class KeepFocus {
    public static final String MOD_ID = "keep_focus";

    public KeepFocus() {
        ModEventBus modEventBus = PotoFluxLoadingContext.get().getModEventBus();

        modEventBus.addListener(Tabs::register);
        modEventBus.addListener(Runs::register);
        modEventBus.addListener(this::onRegisterLang);
        modEventBus.addListener(FocusStylesheets::register);
    }

    private void onRegisterLang(@NotNull RegisterLangEvent event) {
        event.registerLang(new FocusTranslations());
    }

    public static @NotNull Path getModDir() {
        Path dir = PotoFlux.getModDataDir().resolve(MOD_ID);
        try {
            Files.createDirectories(dir);
        } catch (IOException ignored) {}
        return dir;
    }

    public static @Nullable String getVersion() {
        try {
            Properties props = new Properties();
            props.load(KeepFocus.class.getResourceAsStream("/modVersion.properties"));

            return props.getProperty("version");
        } catch (IOException e) {
            e.printStackTrace();
            PtfLogger.error("Could not get version for mod " + MOD_ID, LogCategories.MOD_LOADER);
            return null;
        }
    }
}
