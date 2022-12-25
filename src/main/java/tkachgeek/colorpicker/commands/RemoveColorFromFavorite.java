package tkachgeek.colorpicker.commands;

import tkachgeek.colorpicker.FavoriteColors;
import tkachgeek.commands.command.Executor;

public class RemoveColorFromFavorite extends Executor {
  @Override
  public void executeForPlayer() {
    FavoriteColors.getInstance().removeColor(player(), arg(1).toString());
    FavoriteColors.getInstance().store();
  
  }
}
