package me.griphion.geyserluckpermshook.hook;

import me.griphion.geyserluckpermshook.GeyserLuckPermsHook;
import org.geysermc.connector.GeyserConnector;

public class GeyserHook {
  public static GeyserConnector geyser;

  public static void hook(){
    geyser = GeyserConnector.getInstance();

    if (GeyserHook.geyser == null){
      GeyserLuckPermsHook.getInstance().getLogger().warning("No se pudo establecer la conexion con Geyser!");
      GeyserLuckPermsHook.getInstance().disablePlugin();
    }
  }
}
