package org.leux.theapi.enums;

public enum Hook {
    // Built-in
    VAULT(false),
    PLACEHOLDERAPI(false);

    // built-in variable to check if the hook is built-in or not
    private final boolean isBuiltIn;

    /**
     * Creates a new hook
     * @param paramBoolean if the hook is built-in or not
     */
    Hook(boolean paramBoolean) {
        this.isBuiltIn = paramBoolean;
    }

    /**
     * Returns if the hook is built-in or not
     * @return true if the hook is built-in, false otherwise
     */
    public boolean isBuiltIn() {
        return isBuiltIn;
    }
}
