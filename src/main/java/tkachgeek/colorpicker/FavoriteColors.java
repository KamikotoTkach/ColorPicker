package tkachgeek.colorpicker;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class FavoriteColors extends JsonConfig {
  static FavoriteColors instance = null;
  HashMap<UUID, Set<String>> recentColors = new HashMap<>();
  
  public FavoriteColors() {
  }
  
  public static FavoriteColors getInstance() {
    if (instance == null) instance = JsonConfigManager.load("favoriteColors", FavoriteColors.class);
    return instance;
  }
  
  public Set<String> getColors(Player player) {
    if (!recentColors.containsKey(player.getUniqueId())) {
      recentColors.put(player.getUniqueId(), new HashSet<>());
    }
    return recentColors.get(player.getUniqueId());
  }
  
  public void addColor(Player player, String color) {
    getColors(player).add(color);
  }
  
  public void removeColor(Player player, String color) {
    getColors(player).remove(color);
  }
}
