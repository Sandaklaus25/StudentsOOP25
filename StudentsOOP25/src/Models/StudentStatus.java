package Models;

public enum StudentStatus {
    active,
    interrupted,
    graduated;

    public String getDescription() {
        return switch (this) {
            case active -> "записан";
            case interrupted -> "прекъснал";
            case graduated -> "завършил";
        };
    }

    public boolean isActive() {
        return this == active;
    }

    public boolean isInterrupted() {
        return this == interrupted;
    }

    public boolean hasGraduated() {
        return this == graduated;
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
