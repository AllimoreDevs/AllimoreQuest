package taurasi.marc.allimorequest.Utils;

import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SoundFX {
    public String name;
    public Sound sound;
    public float volume;
    public float pitch;

    public SoundFX(String name, Sound sound, float volume, float pitch){
        this.name = name;
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    public SoundFX(String name, FileConfiguration config, String startPath){
        this.name = name;

        startPath = String.format("%s%s.", startPath, name);
        sound = Utils.ConvertStringToSound(config.getString(startPath + "Clip"));
        volume = (float)config.getDouble(startPath + "Volume");
        pitch = (float)config.getDouble(startPath + "Pitch");
    }
    public void WriteSoundFX(FileConfiguration config, String startPath){
        startPath = String.format("%s%s.", startPath, name);
        config.set(startPath + "Clip", sound.name());
        config.set(startPath + "Volume", volume);
        config.set(startPath+ "Pitch", pitch);
    }


    public void PlaySound(Player player){
        player.playSound(player.getLocation(), sound, volume, pitch);
    }

}
