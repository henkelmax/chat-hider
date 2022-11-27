package de.maxhenkel.chathider.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import de.maxhenkel.chathider.ChatHider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.gui.screens.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Gui.class)
public class GuiMixin {

    private static final Minecraft MC = Minecraft.getInstance();

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ChatComponent;render(Lcom/mojang/blaze3d/vertex/PoseStack;I)V"))
    private void renderChat(ChatComponent chat, PoseStack poseStack, int i) {
        if (isChatOpen()) {
            chat.render(poseStack, i);
            return;
        }
        if (ChatHider.chatHidden) {
            return;
        }
        chat.render(poseStack, i);
    }

    private boolean isChatOpen() {
        return MC.screen instanceof ChatScreen;
    }

}
