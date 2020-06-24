package de.marenthyu.ogc;

import de.marenthyu.ogc.connectors.twitch.PubSubConnector;
import de.marenthyu.ogc.gui.MainWindow;
import de.marenthyu.twitch.auth.AuthStore;
import de.marenthyu.ogc.module.ModuleManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final String CLIENT_ID = "qdv32d32e1jx10qepo67yxnvysvtrw";
    private static final ArrayList<String> scopes = new ArrayList<>(Arrays.asList("chat:read", "chat:edit", "channel_subscriptions", "channel:read:redemptions", "bits:read"));
    public static void main(String[] args) {

        final SplashScreen splash = SplashScreen.getSplashScreen();
        if (splash == null) {
            System.out.println("SplashScreen.getSplashScreen() returned null");
            return;
        }
        Graphics2D g = splash.createGraphics();
        if (g == null) {
            System.out.println("g is null");
            return;
        }
        renderSplashFrame(g, splash, "Loading Authorization Store");
        AuthStore.init(CLIENT_ID);
        renderSplashFrame(g, splash, "Authorization Store initialized");
        if (!AuthStore.hasUserToken()) {
            renderSplashFrame(g, splash, "Requesting new Authorization");
            AuthStore.requestNewUserToken(scopes);
        }
        while (!AuthStore.hasUserToken()) {
            renderSplashFrame(g, splash, "Waiting for authorization callback");
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        renderSplashFrame(g, splash, "Authorization acquired, loading Modules");
        ModuleManager.loadModules();
        renderSplashFrame(g, splash, "Modules loaded, initializing");
        ModuleManager.initializeModules();
        renderSplashFrame(g, splash, "Modules initialized, loading connectors");
        PubSubConnector pubSub = new PubSubConnector();
        new MainWindow();

    }

    static void renderSplashFrame(Graphics2D g, SplashScreen splash, String step) {
        final String[] comps = {"foo", "bar", "baz"};
        g.setComposite(AlphaComposite.Clear);
        g.fillRect(25,140,200,40);
        g.setPaintMode();
        g.setColor(Color.BLACK);
        g.drawString(step + "...", 25, 150);
        splash.update();
    }
}
