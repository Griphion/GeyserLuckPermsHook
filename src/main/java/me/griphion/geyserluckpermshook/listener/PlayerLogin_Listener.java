package me.griphion.geyserluckpermshook.listener;

import me.griphion.geyserluckpermshook.GeyserLuckPermsHook;
import me.griphion.geyserluckpermshook.hook.GeyserHook;
import me.griphion.geyserluckpermshook.hook.LuckPermsHook;
import me.griphion.geyserluckpermshook.utils.ConfigFile;
import net.luckperms.api.model.data.DataMutateResult;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.InheritanceNode;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerLogin_Listener implements Listener {

  private final Group bedrockGroup;
  private InheritanceNode bedrockGroupNode;
  private ConfigFile configFile;

  public PlayerLogin_Listener() {
    this.configFile = new ConfigFile("config");
    this.bedrockGroup = LuckPermsHook.luckPerms.getGroupManager().getGroup(configFile.getConfig().getString("bedrock-group-name","bedrock"));
    if (bedrockGroup == null) {
      GeyserLuckPermsHook.getInstance().getLogger().warning("No se pudo obtener el grupo para los jugadores de bedrock");
      GeyserLuckPermsHook.getInstance().disablePlugin();
      return;
    }
    this.bedrockGroupNode = InheritanceNode.builder(bedrockGroup).build();

  }

  @EventHandler
  public void onPlayerLogin(PostLoginEvent event) {
    User user = LuckPermsHook.luckPerms.getPlayerAdapter(ProxiedPlayer.class).getUser(event.getPlayer());
    if (isBedrockPlayer(event.getPlayer())) {
      if (isInBedrockGroup(user)) return;
      if (user.data().add(bedrockGroupNode) == DataMutateResult.FAIL) {
        GeyserLuckPermsHook.getInstance().getLogger().warning("No se pudo establecer el grupo para el jugador de bedrock: '" + event.getPlayer().getDisplayName()+ "'");
        return;
      }
      LuckPermsHook.luckPerms.getUserManager().saveUser(user);
      GeyserLuckPermsHook.getInstance().getLogger().info("Se agrego al grupo de bedrock a: '" + event.getPlayer().getDisplayName() + "'");
    } else if (isInBedrockGroup(user)) {
      if (user.data().remove(bedrockGroupNode) == DataMutateResult.FAIL) {
        GeyserLuckPermsHook.getInstance().getLogger().warning("No se pudo eliminar el grupo para el jugador de bedrock: '" + event.getPlayer().getDisplayName()+ "'");
        return;
      }
      LuckPermsHook.luckPerms.getUserManager().saveUser(user);
      GeyserLuckPermsHook.getInstance().getLogger().info("Se elimino del grupo de bedrock a '" + event.getPlayer().getDisplayName() + "' porque entro desde Java.");
    }
  }


  private boolean isInBedrockGroup(User user) {
    if (user == null) return true;
    return user.getInheritedGroups(user.getQueryOptions()).stream().anyMatch(g -> g.equals(bedrockGroup));
  }

  private boolean isBedrockPlayer(ProxiedPlayer player) {
    return GeyserHook.geyser.getPlayerByUuid(player.getUniqueId()) != null;
  }
}
