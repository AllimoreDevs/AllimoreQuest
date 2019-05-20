package taurasi.marc.allimorequest.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import taurasi.marc.allimorequest.ProcGen.DifficultyTier;

import java.util.ArrayList;
import java.util.List;

public class QuestDifficultyTabComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> suggestions = new ArrayList<>();
        DifficultyTier[] difficultyTiers = DifficultyTier.values();
        for (DifficultyTier difficultyTier : difficultyTiers) {
            suggestions.add(difficultyTier.name().toLowerCase());
        }
        return suggestions;
    }
}
