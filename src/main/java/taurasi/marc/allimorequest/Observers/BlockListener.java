package taurasi.marc.allimorequest.Observers;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import taurasi.marc.allimorequest.Allimorequest;

public class BlockListener implements Listener {
    private static String NOTICE_BOARD_TITLE_LINE = ChatColor.GREEN + "[Notice Board]";
    private static String NOTIICE_BOARD_RAW = "[Notice Board]";

    @EventHandler
    public void OnRightClickSign(PlayerInteractEvent event){
        if ( !(event.getAction() == Action.RIGHT_CLICK_BLOCK) ) return;
        if(IsNoticeBoard(event.getClickedBlock())){
            Allimorequest.PLAYER_DATA.GetPlayerData(event.getPlayer()).OpenBoardGUI();
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void OnBlockDestroyEvent(BlockBreakEvent event){
        if(IsNoticeBoard(event.getBlock()) && !(event.getPlayer().hasPermission("allimore.quest.placenoticeboards")) ){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void OnSignChange(SignChangeEvent event){
        if( !(event.getPlayer().hasPermission("allimore.quest.placenoticeboards")) ) return;

        if(event.getLine(0).equals(NOTIICE_BOARD_RAW)){
            event.setLine(0, NOTICE_BOARD_TITLE_LINE);
        }
    }

    public boolean IsNoticeBoard(Block block){
        return IsSign(block) && SignFirstLineMatch(block, NOTICE_BOARD_TITLE_LINE);
    }
    public boolean IsSign(Block block){
        return block != null && block.getState() instanceof Sign;
    }
    public boolean SignFirstLineMatch(Block block, String compareString){
        Sign signState = (Sign) block.getState();
        String line = signState.getLine(0);
        return line.equals(compareString);
    }
}
