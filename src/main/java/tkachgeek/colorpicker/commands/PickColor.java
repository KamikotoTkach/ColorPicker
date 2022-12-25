package tkachgeek.colorpicker.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import net.kyori.adventure.util.HSVLike;
import tkachgeek.colorpicker.FavoriteColors;
import tkachgeek.commands.command.Executor;

import java.time.Duration;

public class PickColor extends Executor {
  
  public static String hsvToRgb(float hue, float saturation, float value) {
    //maybe todo: Ближайшие 4-5-6-7 битные цвета
    int h = (int) (hue * 6);
    float f = hue * 6 - h;
    float p = value * (1 - saturation);
    float q = value * (1 - f * saturation);
    float t = value * (1 - (1 - f) * saturation);
    
    switch (h) {
      case 0:
        return rgbToString(value, t, p);
      case 1:
        return rgbToString(q, value, p);
      case 2:
        return rgbToString(p, value, t);
      case 3:
        return rgbToString(p, q, value);
      case 4:
        return rgbToString(t, p, value);
      case 5:
        return rgbToString(value, p, q);
      default:
        throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + hue + ", " + saturation + ", " + value);
    }
  }
  
  public static String rgbToString(float r, float g, float b) {
    String rs = Integer.toHexString((int) (r * 256));
    String gs = Integer.toHexString((int) (g * 256));
    String bs = Integer.toHexString((int) (b * 256));
    return rs + gs + bs;
  }
  
  @Override
  public void executeForPlayer() {
    Component hue = Component.empty();
    Component brightness = Component.empty();
    Component saturation = Component.empty();
    Component preview = Component.empty();
    Component favoriteColors = Component.empty();
    for (String color : FavoriteColors.getInstance().getColors(player())) {
      favoriteColors = favoriteColors.append(Component.space()).append(Component.text("||||")
         .color(TextColor.fromHexString(color))
         .hoverEvent(HoverEvent.showText(Component.text(color)))
         .clickEvent(ClickEvent.copyToClipboard(color)));
    }
    int inputHue = 0;
    int inputSat = 100;
    int inputBrg = 100;
    if (argumentsAmount() > 0) {
      inputHue = arg(0).toInt();
      inputSat = arg(1).toInt();
      inputBrg = arg(2).toInt();
    }
    for (int i = 0; i <= 99; i++) {
      hue = hue.append(Component.text(inputHue - 2 == i || inputHue == i ? "." : "|")
         .color(TextColor.color(HSVLike.of(i / 100f, inputSat / 100f, inputBrg / 100f)))
         .clickEvent(ClickEvent.runCommand("/colorpicker " + i + " " + inputSat + " " + inputBrg)));
      saturation = saturation.append(Component.text(inputSat - 2 == i || inputSat == i ? "." : "|")
         .color(TextColor.color(HSVLike.of(inputHue / 100f, i / 100.0f, inputBrg / 100f)))
         .clickEvent(ClickEvent.runCommand("/colorpicker " + inputHue + " " + i + " " + inputBrg)));
      brightness = brightness.append(Component.text(inputBrg - 2 == i || inputBrg == i ? "." : "|")
         .color(TextColor.color(HSVLike.of(inputHue / 100f, inputSat / 100f, i / 100f)))
         .clickEvent(ClickEvent.runCommand("/colorpicker " + inputHue + " " + inputSat + " " + i)));
    }
    HSVLike selectedColor = HSVLike.of(inputHue / 100f, inputSat / 100f, inputBrg / 100f);
    String selectedColorHex = hsvToRgb(inputHue / 100f, inputSat / 100f, inputBrg / 100f);
    if (argumentsAmount() > 0) {
      
      player().sendTitlePart(TitlePart.TITLE, Component.text("Пример текста").color(TextColor.color(selectedColor)));
      player().sendTitlePart(TitlePart.SUBTITLE, Component.text("Пример текста").color(TextColor.color(selectedColor)));
      player().sendTitlePart(TitlePart.TIMES, Title.Times.of(Duration.ofSeconds(0), Duration.ofSeconds(1), Duration.ofSeconds(1)));
      for (int i = 0; i < 15; i++) {
        player().sendMessage(Component.empty());
      }
      
      for (int col = 0; col <= 99; col++) {
        preview = preview.append(Component.text("|"));
      }
      
      preview = preview.color(TextColor.color(selectedColor))
         .clickEvent(ClickEvent.copyToClipboard("Copy #" + selectedColorHex))
         .hoverEvent(HoverEvent.showText(Component.text("#" + selectedColorHex)));
      
      for (int row = 0; row < 4; row++) {
        player().sendMessage(preview);
      }
      
      player().sendMessage(Component.text("В избранное").color(TextColor.color(selectedColor)).clickEvent(ClickEvent.runCommand("/colorpicker save #" + selectedColorHex)));
    }
    
    player().sendMessage(Component.empty());
    
    player().sendMessage(Component.text("Цвет").color(TextColor.color(selectedColor)));
    player().sendMessage(hue);
    
    player().sendMessage(Component.text("Насыщенность").color(TextColor.color(selectedColor)));
    player().sendMessage(saturation);
    
    player().sendMessage(Component.text("Яркость").color(TextColor.color(selectedColor)));
    player().sendMessage(brightness);
    player().sendMessage(Component.empty());
    player().sendMessage(favoriteColors);
    }
}
