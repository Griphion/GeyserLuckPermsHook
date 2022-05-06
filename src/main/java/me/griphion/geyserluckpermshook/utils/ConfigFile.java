package me.griphion.geyserluckpermshook.utils;

import me.griphion.geyserluckpermshook.GeyserLuckPermsHook;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Level;

public class ConfigFile {

    private final String fileName;
    private Configuration config = null;
    private File configFile = null;

    public ConfigFile(String fileName){
      this.fileName = fileName;
    }

    public void reloadConfig(){
      if(this.configFile == null) {
        loadConfigFromJar();
      }

      try {
        config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
      } catch (IOException e) {
        GeyserLuckPermsHook.getInstance().getLogger().warning("No se pudo cargar la configuracion, se utilizaran los valores predeterminados!");
        e.printStackTrace();
      }

    }

    public void loadConfigFromJar() {
     if (!GeyserLuckPermsHook.getInstance().getDataFolder().exists())
       GeyserLuckPermsHook.getInstance().getDataFolder().mkdir();

     configFile = new File(GeyserLuckPermsHook.getInstance().getDataFolder(),this.fileName + ".yml");

     if (!configFile.exists()) {
        try (InputStream in = GeyserLuckPermsHook.getInstance().getResourceAsStream(this.fileName + ".yml")) {
          Files.copy(in, configFile.toPath());
         } catch (IOException e) {
          GeyserLuckPermsHook.getInstance().getLogger().log(Level.SEVERE, "Error al cargar la config del .jar!", e);
       }
     }
    }

    public Configuration getConfig(){
      if(this.config == null) reloadConfig();
      return this.config;
    }

    /*
    public void saveConfig(){
      if(this.config == null || this.configFile == null) return;
      try {
        ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, new File(GeyserLuckPermsHook.getInstance().getDataFolder(), this.fileName));
      } catch (IOException e) {
        config = null;
        GeyserLuckPermsHook.getInstance().getLogger().log(Level.SEVERE, "No se pudo guardar la Config de " + this.configFile, e);
      }
    }
     */

}