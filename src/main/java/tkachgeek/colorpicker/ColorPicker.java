package tkachgeek.colorpicker;

import org.bukkit.plugin.java.JavaPlugin;
import tkachgeek.colorpicker.commands.AddColorToFavorite;
import tkachgeek.colorpicker.commands.PickColor;
import tkachgeek.colorpicker.commands.RemoveColorFromFavorite;
import tkachgeek.commands.command.ArgumentSet;
import tkachgeek.commands.command.Command;
import tkachgeek.commands.command.arguments.ExactStringArg;
import tkachgeek.commands.command.arguments.HexColorArg;
import tkachgeek.commands.command.arguments.basic.IntegerArg;

public final class ColorPicker extends JavaPlugin {
  
  @Override
  public void onEnable() {
    JsonConfigManager.init(this);
    new Command("colorpicker", "", new PickColor()).arguments(
       new ArgumentSet(new PickColor(), "", new IntegerArg(0, 100), new IntegerArg(0, 100), new IntegerArg(0, 100)),
       new ArgumentSet(new AddColorToFavorite(), "", new ExactStringArg("save"), new HexColorArg()),
       new ArgumentSet(new RemoveColorFromFavorite(), "", new ExactStringArg("remove"), new HexColorArg())
    ).register(this);
  }
  
  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }
}
