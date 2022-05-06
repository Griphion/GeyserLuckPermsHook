package me.griphion.geyserluckpermshook.hook;

import me.griphion.geyserluckpermshook.GeyserLuckPermsHook;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;

public class LuckPermsHook {
  public static LuckPerms luckPerms;

  public static void hook(){
    if (GeyserLuckPermsHook.getInstance().getProxy().getPluginManager().getPlugin("LuckPerms") != null) {
      luckPerms = LuckPermsProvider.get();
    } else {
      GeyserLuckPermsHook.getInstance().getLogger().warning("No se pudo establecer la conexion con LuckPerms!");
      GeyserLuckPermsHook.getInstance().disablePlugin();
    }
  }
}
