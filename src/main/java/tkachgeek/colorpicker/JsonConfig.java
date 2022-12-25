package tkachgeek.colorpicker;

public abstract class JsonConfig extends Config {
  
  @Override
  public void store() {
    JsonConfigManager.store(path, this);
  }
  
  @Override
  public String toString() {
    return JsonConfigManager.toString(this);
  }
}
