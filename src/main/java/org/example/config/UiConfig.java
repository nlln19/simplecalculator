package org.example.config;

/**
 * Centralized UI sizing constants
 */
public final class UiConfig {
    private final double width;
    private final double height;
    private final double buttonWidth;
    private final double buttonHeight;

    private UiConfig(double width, double height) {
        this.width = width;
        this.height = height;
        this.buttonWidth = width / 5.0;
        this.buttonHeight = height / 6.0;
    }

    public static UiConfig defaultConfig() {
        return new UiConfig(500, 600);
    }

    public double width() {
        return width;
    }

    public double height() {
        return height;
    }

    public double buttonWidth() {
        return buttonWidth;
    }

    public double buttonHeight() {
        return buttonHeight;
    }
}
