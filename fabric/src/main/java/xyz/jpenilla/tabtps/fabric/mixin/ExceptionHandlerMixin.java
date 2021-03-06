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
package xyz.jpenilla.tabtps.fabric.mixin;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.kyori.adventure.platform.fabric.FabricAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.util.ComponentMessageThrowable;
import net.minecraft.network.chat.ComponentUtils;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import xyz.jpenilla.tabtps.common.command.ExceptionHandler;

@Mixin(value = ExceptionHandler.class, remap = false)
abstract class ExceptionHandlerMixin {
  @Redirect(method = "argumentParsing", at = @At(value = "INVOKE", target = "Lnet/kyori/adventure/util/ComponentMessageThrowable;getOrConvertMessage(Ljava/lang/Throwable;)Lnet/kyori/adventure/text/Component;"))
  private @Nullable Component convertOrGetMessage(final @Nullable Throwable throwable) {
    if (throwable instanceof CommandSyntaxException) {
      final net.minecraft.network.chat.Component minecraft = ComponentUtils.fromMessage(((CommandSyntaxException) throwable).getRawMessage());
      return FabricAudiences.nonWrappingSerializer().deserialize(minecraft);
    }
    return ComponentMessageThrowable.getOrConvertMessage(throwable);
  }
}
