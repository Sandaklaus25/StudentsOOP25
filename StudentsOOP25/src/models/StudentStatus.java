package models;
/**
 * Enum representing the status of a student.
 */
public enum StudentStatus {
    ACTIVE,
    INTERRUPTED,
    GRADUATED;
    /**
     * Returns a human-readable description of the student status.
     * @return Description string of the status.
     */
    public String getDescription() {
        return switch (this) {
            case ACTIVE -> "active";
            case INTERRUPTED -> "interrupted";
            case GRADUATED -> "graduated";
        };
    }
    /**
     * Checks if the status is ACTIVE.
     * @return true if status is ACTIVE, false otherwise.
     */
    public boolean isActive() {
        return this == ACTIVE;
    }
    /**
     * Checks if the status is INTERRUPTED.
     * @return true if status is INTERRUPTED, false otherwise.
     */
    public boolean isInterrupted() {
        return this == INTERRUPTED;
    }
    /**
     * Checks if the status is GRADUATED.
     * @return true if status is GRADUATED, false otherwise.
     */
    public boolean hasGraduated() {
        return this == GRADUATED;
    }
    /**
     * Returns the string representation of the student status.
     * @return The description of the status.
     */
    @Override
    public String toString() {
        return getDescription();
    }
}
