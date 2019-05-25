package taurasi.marc.allimorequest.Commands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import taurasi.marc.allimorecore.AllimoreLogger;
import taurasi.marc.allimorequest.Commands.SubCommands.*;
import taurasi.marc.allimorequest.PlayerDataIndex;
import taurasi.marc.allimorequest.ProcGen.DifficultyManager;
import taurasi.marc.allimorequest.ProcGen.QuestFactory;

public class CommandManager implements CommandExecutor {
    public PlayerDataIndex playerDataIndex;

    private AllimoreCommand[] subCommands;
    private DifficultyManager difficultyManager;
    private QuestFactory questFactory;

    public CommandManager(PlayerDataIndex playerDataIndex, QuestFactory questFactory, DifficultyManager difficultyManager) {
        this.playerDataIndex = playerDataIndex;
        this.difficultyManager = difficultyManager;
        this.questFactory = questFactory;
        SetupSubCommands();
    }

    public void SetupSubCommands() {
        subCommands = new AllimoreCommand[]{
                new AbandonQuestCommand("Abandon", this),
                new CompleteQuestCommand("Complete", this),
                new QuestStatusCommand("Status", this),
                new ForceCompleteQuestCommand("ForceComplete", this),
                new GenerateQuestCommand("Generate", this),
                new QuestBoardCommand("NoticeBoard", this),
                new QuestJournalCommand("QuestJournal", this),
                new WriteDataCommand("WriteData", this)
        };
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(command.getName().equalsIgnoreCase("Quest"))) return false;
        if (!(sender instanceof Player)) {
            AllimoreLogger.LogInfo("Only players can run quest commands!");
            return false;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            AllimoreLogger.LogInfo("Too few arguments!", player);
            return false;
        }

        for (int i = 0; i < subCommands.length; i++) {
            if (subCommands[i].IsCommand(args[0])) {
                return subCommands[i].Run(player, args);
            }
        }

        return false;
    }

    public String ConstructStringFromArgs(String[] args, int skipArgs) {
        StringBuilder sb = new StringBuilder();
        for (int i = skipArgs; i < args.length; i++) {
            sb.append(args[i]);
            // If statement prevents a space form being appended at the end
            if (i < args.length - 1) sb.append(' ');
        }

        return sb.toString();
    }
    public String ConstructStringFromArgs(String[] args) {
        return ConstructStringFromArgs(args, 0);
    }

    public AllimoreCommand[] getSubCommands() {
        return subCommands;
    }
    public AllimoreCommand getSubcommand(String name) {
        for(int i = 0; i < subCommands.length; i++){
            if(subCommands[i].name.equalsIgnoreCase(name)){
                return subCommands[i];
            }
        }
        return null;
    }
    public DifficultyManager GetDifficultyManager(){
        return difficultyManager;
    }
    public QuestFactory GetQuestFactory(){
        return questFactory;
    }
}
