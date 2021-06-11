/*
 * This file is part of TabTPS, licensed under the MIT License.
 *
 * Copyright (c) 2020-2021 Jason Penilla
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package xyz.jpenilla.tabtps.fabric.service;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import net.minecraft.server.level.ServerPlayer;
import org.checkerframework.checker.nullness.qual.NonNull;
import xyz.jpenilla.tabtps.common.service.UserService;
import xyz.jpenilla.tabtps.fabric.FabricUser;
import xyz.jpenilla.tabtps.fabric.TabTPSFabric;

public final class FabricUserService extends UserService<ServerPlayer, FabricUser> {
  private final TabTPSFabric tabTPSFabric;

  public FabricUserService(final @NonNull TabTPSFabric platform) {
    super(platform, FabricUser.class);
    this.tabTPSFabric = platform;
  }

  @Override
  protected @NonNull UUID uuid(final @NonNull ServerPlayer base) {
    return base.getUUID();
  }

  @Override
  protected @NonNull FabricUser create(final @NonNull ServerPlayer base) {
    return FabricUser.from(this.tabTPSFabric, base);
  }

  @Override
  protected @NonNull Collection<ServerPlayer> platformPlayers() {
    return Collections.unmodifiableCollection(this.tabTPSFabric.server().getPlayerList().getPlayers());
  }
}
