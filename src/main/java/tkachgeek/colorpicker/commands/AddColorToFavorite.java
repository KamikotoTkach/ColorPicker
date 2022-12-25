package tkachgeek.colorpicker.commands;

import tkachgeek.colorpicker.FavoriteColors;
import tkachgeek.commands.command.Executor;

public class AddColorToFavorite extends Executor {
  @Override
  public void executeForPlayer() {
    FavoriteColors.getInstance().addColor(player(),arg(1).toString());
    FavoriteColors.getInstance().store();
  }
}
