package de.marenthyu.ogc.module;

import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Set;

public class ModuleManager {
    private static ArrayList<GameModule> modules = new ArrayList<>();
    private static boolean loaded = false;

    /**
     * Loads all modules, aka creates an Instance of all Classes that implement {@link GameModule}
     */
    public static void loadModules() {
        Reflections reflections = new Reflections();
        Set<Class<? extends GameModule>> classes = reflections.getSubTypesOf(GameModule.class);

        for (Class<? extends GameModule> c:classes) {
            try {
                GameModule test = c.newInstance();
                System.out.println(test.getDescription());
                modules.add(test);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        loaded = true;
    }

    /**
     * Initializes all loaded Modules by calling {@link GameModule#initialize()}.
     * Should be called AFTER {@link #loadModules()}
     */
    public static void initializeModules() {
        if (!loaded) {
            System.err.println("Attempted to initialize modules before loading. Ignoring init call.");
            return;
        }
        for (GameModule m:modules) {
            m.initialize();
        }
    }
}
