package taurasi.marc.allimorequest.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import taurasi.marc.allimorequest.Allimorequest;
import taurasi.marc.allimorequest.ProcGen.DifficultyTier;
import taurasi.marc.allimorequest.Professions.PlayerProfession;

import java.util.ArrayList;
import java.util.List;

public class GenerateQuestTabComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> suggestions = new ArrayList<>();

        if(args.length == 1){
            AddProfessions(suggestions);
        }
        else{
            AddDifficultyTiers(suggestions);
        }

        return suggestions;
    }

    private void AddProfessions(ArrayList<String> suggestions) {
        PlayerProfession[] playerProfessions = PlayerProfession.values();
        for (PlayerProfession playerProfession : playerProfessions){
            suggestions.add(playerProfession.name().toLowerCase());
        }
    }

    private void AddDifficultyTiers(ArrayList<String> suggestions) {
        DifficultyTier[] difficultyTiers = Allimorequest.DIFFICULTY_MANAGER.GetDifficultyTiersArray();
        for (DifficultyTier difficultyTier : difficultyTiers) {
            suggestions.add(difficultyTier.name.toLowerCase());
        }
    }
}
