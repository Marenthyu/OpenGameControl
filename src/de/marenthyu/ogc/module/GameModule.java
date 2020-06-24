package de.marenthyu.ogc.module;

/**
 * An implementation of this Interface causes this class to be initialized upon load,
 * then {@link #initialize()} is called to allow for necessary Setup tasks.
 */
public interface GameModule {
    /**
     * @return A Human-readable name of the Module to let the user choose it from the list of available Modules.
     */
    public String getName();
    /**
     * @return A Human-readable description to tell what this Game Module is capable of.
     */
    public String getDescription();

    /**
     * Called after load by the {@link ModuleManager} to allow for necessary setUp Tasks
     */
    public void initialize();
}
