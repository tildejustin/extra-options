package me.voidxwalker.options.extra.interfaces;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface SoundEngineEx {
    void extra_options$init(@Nullable String deviceSpecifier);

    boolean extra_options$isDeviceUnavailable();

    boolean extra_options$updateDeviceSpecifier();

    String extra_options$getCurrentDeviceName();

    default List<String> extra_options$getSoundDevices() {
        throw new IllegalStateException();
    }
}
