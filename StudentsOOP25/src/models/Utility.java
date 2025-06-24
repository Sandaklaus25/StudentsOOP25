package models;

/**
 * A utility class providing helper methods for validation and common checks.
 */
public class Utility {
    /**
     * Validates that none of the provided arguments are {@code null}.
     * <p>
     * Throws an {@link IllegalArgumentException} if any argument is {@code null}.
     * </p>
     *
     * @param args the arguments to validate
     * @throws IllegalArgumentException if any argument is {@code null}
     */
    public static void validateArgsForNulls(Object... args) {
        for (Object arg : args) {
            if (arg == null) {
                throw new IllegalArgumentException("All arguments must be non-null!");
            }
        }
    }
}
