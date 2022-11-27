package de.maxhenkel.chathider;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class ChatHider implements ClientModInitializer {

    public static final String MODID = "chathider";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public static KeyMapping TOGGLE_HIDE_CHAT;

    public static boolean chatHidden;

    @Override
    public void onInitializeClient() {
        TOGGLE_HIDE_CHAT = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.chathider.toggle_hide_chat", InputConstants.UNKNOWN.getValue(), "key.categories.misc"));

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (TOGGLE_HIDE_CHAT.consumeClick()) {
                chatHidden = !chatHidden;
                if (client.player != null) {
                    client.player.displayClientMessage(Component.translatable(chatHidden ? "message.chathider.chat_hidden" : "message.chathider.chat_visible"), true);
                }
            }
        });
    }
}
