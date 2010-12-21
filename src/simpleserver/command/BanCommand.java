/*******************************************************************************
 * Copyright (c) 2010 SimpleServer authors (see CONTRIBUTORS)
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 ******************************************************************************/
package simpleserver.command;

import simpleserver.Player;
import simpleserver.Server;

public class BanCommand extends PlayerArgCommand {
  public BanCommand() {
    super("ban");
  }

  @Override
  protected void executeWithTarget(Player player, String message, String target) {
    String reason = extractArgument(message, 1);
    Server server = player.getServer();

    server.runCommand("ban", target);
    server.kick(target, reason);

    server.adminLog("User " + player.getName() + " banned player:\t " + target
        + "\t(" + reason + ")");
    server.runCommand("say", "Player " + target + " has been banned! ("
        + reason + ")");
  }

  @Override
  protected void noTargetSpecified(Player player, String message) {
    player.addMessage("\u00a7cNo player or reason specified.");
  }
}
