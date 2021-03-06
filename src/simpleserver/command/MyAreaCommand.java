/*
 * Copyright (c) 2010 SimpleServer authors (see CONTRIBUTORS)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package simpleserver.command;

import simpleserver.Color;
import simpleserver.Player;
import simpleserver.config.PermissionConfig;

public class MyAreaCommand extends AbstractCommand implements PlayerCommand {
  public MyAreaCommand() {
    super("myarea [start|end|save|unsave|rename]",
          "Manage your personal area");
  }

  private boolean areaSizeOk(Player player) {
    return (Math.abs(player.areastart.x() - player.areaend.x()) <= 50)
          && (Math.abs(player.areastart.y() - player.areaend.y()) <= 50);
  }

  public void execute(Player player, String message) {
    PermissionConfig perm = player.getServer().permissions;
    String arguments[] = extractArguments(message);

    if (arguments.length == 0) {
      player.addTMessage(Color.RED, "Error! Command requires argument!");
      return;
    }

    if (arguments[0].equals("start")) {
      player.areastart = player.position();
      player.areastart = player.areastart.setY((byte) 0); // no height limit
      player.addTMessage(Color.GRAY, "Start coordinate set.");
    } else if (arguments[0].equals("end")) {
      player.areaend = player.position();
      player.areaend = player.areaend.setY((byte) 0); // no height limit
      player.addTMessage(Color.GRAY, "End coordinate set.");
    } else if (arguments[0].equals("save")) {
      if (perm.playerHasArea(player)) {
        player.addTMessage(Color.RED, "New area can not be saved before you remove your old one!");
        return;
      }
      if (!perm.getCurrentArea(player).equals("")) {
        player.addTMessage(Color.RED, "You can not create your area within an existing area!");
        return;
      }
      if (player.areastart == null || player.areaend == null) {
        player.addTMessage(Color.RED, "Define start and end coordinates for your area first!");
        return;
      }
      if (!areaSizeOk(player)) {
        player.addTMessage(Color.RED, "Your area is allowed to have a maximum size of 50x50!");
        return;
      }

      perm.createPlayerArea(player);
      player.addTMessage(Color.GRAY, "Your area has been saved!");
    } else if (arguments[0].equals("unsave")) {
      if (!perm.playerHasArea(player)) {
        player.addTMessage(Color.RED, "You currently have no personal area which can be removed!");
        return;
      }

      perm.removePlayerArea(player);
      player.addTMessage(Color.GRAY, "Your area has been removed!");
    } else if (arguments[0].equals("rename")) {
      if (!perm.playerHasArea(player)) {
        player.addTMessage(Color.RED, "You currently have no personal area which can be renamed!");
        return;
      }

      String label = extractArgument(message, 1);
      if (label != null) {
        if (perm.hasAreaWithName(label)) {
          player.addTMessage(Color.RED, "An area with that name already exists!");
        } else {
          perm.renamePlayerArea(player, label);
          player.addTMessage(Color.GRAY, "Your area has been renamed!");
        }
      } else {
        player.addTMessage(Color.RED, "Please supply an area name.");
      }
    } else {
      player.addTMessage(Color.RED, "You entered an invalid argument.");
    }
  }
}
