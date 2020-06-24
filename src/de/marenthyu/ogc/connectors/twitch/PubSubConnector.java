package de.marenthyu.ogc.connectors.twitch;

import de.marenthyu.ogc.connectors.GameEventHandler;
import de.marenthyu.ogc.connectors.ServiceConnector;
import de.marenthyu.twitch.auth.AuthStore;
import de.marenthyu.twitch.auth.oauth.OAuthToken;
import de.marenthyu.twitch.pubsub.PubSubClient;
import de.marenthyu.twitch.pubsub.bits.BitsEvent;
import de.marenthyu.twitch.pubsub.bits.BitsEventHandler;
import de.marenthyu.twitch.pubsub.channelpoints.ChannelPointsRedemptionHandler;
import de.marenthyu.twitch.pubsub.channelpoints.Redemption;
import de.marenthyu.twitch.pubsub.subscription.SubscriptionEvent;
import de.marenthyu.twitch.pubsub.subscription.SubscriptionEventHandler;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class PubSubConnector implements ServiceConnector<TwitchEvent> {
    PubSubClient client;
    public boolean hasBits = false, hasChannelPoints = false, hasSubscriptions = false;

    public PubSubConnector() {
        OAuthToken token = AuthStore.getToken();
        String channelID = token.getUserID();
        for (String scope:token.getScopes()) {
            switch (scope) {
                case "channel:read:redemptions":
                    hasChannelPoints = true;
                    break;
                case "channel_subscriptions":
                    hasSubscriptions = true;
                    break;
                case "bits:read":
                    hasBits = true;
                    break;
            }
        }
        ArrayList<String> topics = new ArrayList<>();
        if (hasChannelPoints) {
            topics.add("channel-points-channel-v1." + channelID);
        }
        if (hasSubscriptions) {
            topics.add("channel-subscribe-events-v1."  +channelID);
        }
        if (hasBits) {
            topics.add("channel-bits-events-v2." + channelID);
        }
        try {
            client = new PubSubClient(token, topics);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        client.addChannelPointsRedemptionHandler(new ChannelPointsRedemptionHandler("") {
            @Override
            public void matched(Redemption redemption) {
                System.out.println("Got redemption: " + redemption.toString());
            }
        });
        client.addBitsEventHandler(new BitsEventHandler() {
            @Override
            public void matched(BitsEvent event) {
                System.out.println("Got bits event: " + event.toString());
            }
        });
        client.addSubscriptionEventHandler(new SubscriptionEventHandler() {
            @Override
            public void matched(SubscriptionEvent event) {
                System.out.println("Got sub event: " + event.toString());
            }
        });
    }

    @Override
    public void registerEventHandler(TwitchEvent event, GameEventHandler handler) {

    }
}
