package com.bonesnetwork.bonesdropper.Command;

import com.bonesnetwork.bonesdropper.BonesDropper;
import com.bonesnetwork.bonesdropper.ConfigManager;
import com.bonesnetwork.bonesdropper.LangManager;
import com.bonesnetwork.bonesdropper.Util.DropperUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DropperCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args[0].equalsIgnoreCase("debugmode")) {
            BonesDropper.debugMode = !BonesDropper.debugMode;
        } else if (args[0].equalsIgnoreCase("arenas")) {
            if(args[1].equalsIgnoreCase("list")) {
                for(String name: DropperUtil.getAllDroppers()) {
                    sender.sendMessage(name);
                }
            } else if(args[1].equalsIgnoreCase("create")) {
                Pattern pattern = Pattern.compile("[^A-Za-z0-9]+", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(args[2]);
                if(matcher.find()) {
                    sender.sendMessage(LangManager.getLanguageComponent("command.error.create_dropper_invalid_syntax"));
                }
                DropperUtil.createDropper(args[2]);
                try {
                    ConfigManager.mainConfig.reload();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if(args[1].equalsIgnoreCase("remove")) {
                if(DropperUtil.getDropperExists(args[2])) {
                    DropperUtil.removeDropper(args[2]);
                } else {
                    sender.sendMessage(LangManager.getLanguageComponent("command.error.dropper_not_exist"));
                }
            } else if(args[1].equalsIgnoreCase("config")) {
                if(DropperUtil.getDropperExists(args[2])) {
                    if(args[3].equalsIgnoreCase("setspawn")) {
                        Bukkit.getLogger().info("cum");
                        if(sender instanceof Player) {
                            DropperUtil.setDropperSpawn(args[2], ((Player) sender).getLocation());
                            sender.sendMessage(LangManager.getLanguageComponent("command.setting_dropper_spawn"));
                        } else {
                            sender.sendMessage(LangManager.getLanguageComponent("command.error.not_allow_console"));
                        }
                    } else if(args[3].equalsIgnoreCase("settitle")) {
                        DropperUtil.setDropperTitle(args[2], args[4]);
                        sender.sendMessage(LangManager.getLanguageComponent("command.setting_dropper_title"));
                    }
                    try {
                        ConfigManager.mainConfig.save();
                        ConfigManager.mainConfig.reload();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    sender.sendMessage(LangManager.getLanguageComponent("command.error.dropper_not_exist"));
                }
            }
        }
    
        return true;
    }
    
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            list.add("help");
            list.add("debugmode");
            list.add("arenas");
            //list.add("setmainlobby");
            list.add("join");
            list.add("joinme");
            list.add("stats");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("arenas")) {
            list.add("list");
            list.add("create");
            list.add("remove");
            list.add("config");
        } else if (args.length == 3 && (args[1].equalsIgnoreCase("remove") || args[1].equalsIgnoreCase("config"))) {
            list.addAll(DropperUtil.getAllDroppers());
        } else if (args.length == 4 && args[1].equalsIgnoreCase("config")) {
            list.add("setspawn");
            list.add("settitle");
        }
        
        return list;
    }
}
