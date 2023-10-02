package com.bonesnetwork.bonesdropper;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;

public class LangManager {
    private static String currentLanguage;
    public static void setCurrentLanguage(String languageKey) {
        if(ConfigManager.langFiles.containsKey(languageKey)){
            currentLanguage = languageKey;
        } else {
            Bukkit.getLogger().severe("Invalid Language Key: "+languageKey);
        }
    }
    
    public static Component getLanguageComponent(String translationKey) {
        return MiniMessage.miniMessage().deserialize(ConfigManager.langFiles.get(currentLanguage).getString(translationKey));
    }
    
    public static String getLanguageString(String translationKey) {
        return ConfigManager.langFiles.get(currentLanguage).getString(translationKey);
    }
}
