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
package simpleserver.options;

import java.util.Properties;

public class MinecraftOptions extends AbstractOptions {
  private final Options simpleServerOptions;

  public MinecraftOptions(Options options) {
    super("server.properties");

    simpleServerOptions = options;
  }

  @Override
  public void save() {
    load();
    options.setProperty("online-mode", simpleServerOptions.get("onlineMode"));
    options.setProperty("server-ip", "127.0.0.1");
    options.setProperty("server-port", simpleServerOptions.get("internalPort"));
    options.setProperty("max-players", simpleServerOptions.get("maxPlayers"));
    options.setProperty("level-name", simpleServerOptions.get("levelName"));

    super.save();
  }

  @Override
  protected String getComment() {
    return "Generated by SimpleServer\nDO NOT EDIT THIS FILE!";
  }

  @Override
  protected void loadDefaults() {
    defaultOptions = new Properties();
  }

  @Override
  protected void missingFile() {
    // skip
  }
}
