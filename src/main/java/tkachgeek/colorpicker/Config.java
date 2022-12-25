package tkachgeek.colorpicker;

public abstract class Config {
  transient String path;
  transient boolean saveOnDisabling = true;
  
  void setSaveOnDisabling(boolean bool) {
    saveOnDisabling = bool;
  }
  
  abstract public void store();
  abstract public String toString();
}
