package taurasi.marc.allimorequest.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import taurasi.marc.allimorequest.Allimorequest;
import taurasi.marc.allimorequest.PlayerQuestData;

import java.util.List;

public class QuestNameTabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if( !(sender instanceof Player) ) return null;
            Player player = (Player)sender;
            PlayerQuestData playerData = Allimorequest.PLAYER_DATA.GetPlayerData(player);

            return playerData.GetQuestNames();
    }
}
