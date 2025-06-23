package models;

public enum StudentStatus {
    ACTIVE,
    INTERRUPTED,
    GRADUATED;

    public String getDescription() {
        return switch (this) {
            case ACTIVE -> "active";
            case INTERRUPTED -> "interrupted";
            case GRADUATED -> "graduated";
        };
    }

    public boolean isActive() {
        return this == ACTIVE;
    }

    public boolean isInterrupted() {
        return this == INTERRUPTED;
    }

    public boolean hasGraduated() {
        return this == GRADUATED;
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
