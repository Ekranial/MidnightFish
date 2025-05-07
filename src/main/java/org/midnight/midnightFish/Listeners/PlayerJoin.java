package org.midnight.midnightFish.Listeners;

import net.kyori.adventure.resource.ResourcePackInfoLike;
import net.kyori.adventure.resource.ResourcePackRequest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerJoin implements Listener {

    @EventHandler
    public static void PlayerJoin(PlayerJoinEvent event) {

        UUID uuid = UUID.nameUUIDFromBytes("MFish".getBytes());
        System.out.println(uuid);
//        ResourcePackRequest.resourcePackRequest().pac

        event.getPlayer().addResourcePack(uuid, "https://www.dropbox.com/scl/fi/odoqlr7dloudyvaup4hla/MFish-1.21.4.zip?rlkey=ce4h5wb3xyh02zfhhv0jav1f7&st=czffc3vz&dl=1",
                null, null, false);
//        event.getPlayer().setResourcePack("https://www.dropbox.com/scl/fi/odoqlr7dloudyvaup4hla/MFish-1.21.4.zip?rlkey=ce4h5wb3xyh02zfhhv0jav1f7&st=czffc3vz&dl=1");
    }
}
