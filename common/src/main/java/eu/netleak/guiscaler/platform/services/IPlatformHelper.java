package eu.netleak.guiscaler.platform.services;

public interface IPlatformHelper {

    /**
     * Gets the name of the current platform
     *
     * @return The name of the current platform.
     */
    String getPlatformName();

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    /**
     * Gets the name of the environment type as a string.
     *
     * @return The name of the environment type.
     */
    default String getEnvironmentName() {

        return isDevelopmentEnvironment() ? "development" : "production";
    }

    /**
     * Gets the current GUI scale setting.
     *
     * @return The current GUI scale value.
     */
    int getCurrentGuiScale();

    /**
     * Sets the GUI scale option value without triggering a resize. Vanilla's own resize pass
     * reads the option and applies it; we must not call resizeGui() ourselves (see {@code
     * CommonClass#applyAutoScale}).
     *
     * @param scale The GUI scale value to set.
     */
    void setGuiScaleOption(int scale);

    /**
     * Gets the current window width.
     *
     * @return The window width in pixels.
     */
    int getWindowWidth();

    /**
     * Gets the current window height.
     *
     * @return The window height in pixels.
     */
    int getWindowHeight();
}