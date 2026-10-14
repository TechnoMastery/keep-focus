package net.minheur.keepFocus.data;

import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

public record SessionStage(@NotNull Duration duration, @NotNull String objective) {}
