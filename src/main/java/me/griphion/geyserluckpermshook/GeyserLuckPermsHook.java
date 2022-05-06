package me.griphion.geyserluckpermshook;

import me.griphion.geyserluckpermshook.hook.GeyserHook;
import me.griphion.geyserluckpermshook.hook.LuckPermsHook;
import me.griphion.geyserluckpermshook.listener.PlayerLogin_Listener;
import net.md_5.bungee.api.plugin.Plugin;

public final class GeyserLuckPermsHook extends Plugin {
  private static GeyserLuckPermsHook INSTANCE;
  public static GeyserLuckPermsHook getInstance(){
    return INSTANCE;
  }
  @Override
  public void onEnable() {
    INSTANCE = this;

    LuckPermsHook.hook();
    if (LuckPermsHook.luckPerms == null){
      return;
    }

    GeyserHook.hook();
    if (GeyserHook.geyser == null){
      return;
    }

    getProxy().getPluginManager().registerListener(this, new PlayerLogin_Listener());

    getLogger().info("Hook activado");
  }

  @Override
  public void onDisable() {
    getLogger().info("Hook desactivado");
  }


  public void disablePlugin() {
    getProxy().getPluginManager().unregisterListeners(this);
    getProxy().getPluginManager().unregisterCommands(this);
    onDisable();
  }

}
