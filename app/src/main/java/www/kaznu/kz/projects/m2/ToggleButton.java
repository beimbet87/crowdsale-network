package www.kaznu.kz.projects.m2;

public class ToggleButton {
    boolean isButton;

    public ToggleButton(boolean isButton) {
        this.isButton = isButton;
    }

    public boolean isButton() {
        return isButton;
    }

    public void setButton(boolean button) {
        isButton = button;
    }
}
