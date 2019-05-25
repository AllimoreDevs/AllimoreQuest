package taurasi.marc.allimorequest.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import taurasi.marc.allimorequest.Allimorequest;
import taurasi.marc.allimorequest.PlayerDataIndex;
import taurasi.marc.allimorequest.ProcGen.DifficultyManager;
import taurasi.marc.allimorequest.ProcGen.DifficultyTier;
import taurasi.marc.allimorequest.Professions.PlayerProfession;

import java.util.ArrayList;
import java.util.List;

public class QuestCommandTabComplete implements TabCompleter {
    private DifficultyManager difficultyManager;
    private PlayerDataIndex playerDataIndex;
    private CommandManager cmdManager;

    public QuestCommandTabComplete(CommandManager cmdManager, DifficultyManager difficultyManager, PlayerDataIndex playerDataIndex){
        this.cmdManager = cmdManager;
        this.difficultyManager = difficultyManager;
        this.playerDataIndex = playerDataIndex;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        AllimoreCommand[] subCommands = cmdManager.getSubCommands();
        if(args.length < 2){
            return GetSubcommandStrings(subCommands);
        }
        AllimoreCommand subcommand = cmdManager.getSubcommand(args[0]);
        if(subcommand == null || subcommand.arguementTabTypes == null){
            return null;
        }

        switch (subcommand.arguementTabTypes[Math.min(args.length - 2, subcommand.arguementTabTypes.length - 1)]){
            case QUEST_NAMES:
                return GetQuestNames((Player)commandSender);
            case PROFESSIONS:
                return GetProfessions();
            case DIFFICULTY_TIERS:
                return GetDifficultyTiers();
        }

        return null;
    }

    private ArrayList<String> GetSubcommandStrings(AllimoreCommand[] subCommands){
        ArrayList<String> suggestions = new ArrayList<>(subCommands.length);
        for(int i = 0; i < subCommands.length; i++){
            suggestions.add(subCommands[i].name);
        }
        return suggestions;
    }

    private ArrayList<String> GetQuestNames(Player player){
        return playerDataIndex.GetPlayerData(player).GetQuestNames();
    }

    private ArrayList<String> GetProfessions() {
        PlayerProfession[] playerProfessions = PlayerProfession.values();
        ArrayList<String> suggestions = new ArrayList<>(playerProfessions.length);
        for (PlayerProfession playerProfession : playerProfessions){
            suggestions.add(playerProfession.name().toLowerCase());
        }
        return suggestions;
    }

    private ArrayList<String> GetDifficultyTiers() {
        DifficultyTier[] difficultyTiers = difficultyManager.GetDifficultyTiersArray();
        ArrayList<String> suggestions = new ArrayList<>(difficultyTiers.length);
        for (DifficultyTier difficultyTier : difficultyTiers) {
            suggestions.add(difficultyTier.name.toLowerCase());
        }
        return suggestions;
    }
}
