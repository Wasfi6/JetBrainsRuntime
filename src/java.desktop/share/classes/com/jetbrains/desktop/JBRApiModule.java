/*
 * Copyright 2000-2021 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jetbrains.desktop;

import com.jetbrains.internal.JBRApi;

import java.lang.invoke.MethodHandles;

/**
 * This class contains mapping between JBR API interfaces and implementation in {@code java.desktop} module.
 */
public class JBRApiModule {
    static {
        JBRApi.registerModule(MethodHandles.lookup(), JBRApiModule.class.getModule()::addExports)
                .service("com.jetbrains.ExtendedGlyphCache")
                    .withStatic("getSubpixelResolution", "getSubpixelResolution", "sun.font.FontUtilities")
                .service("com.jetbrains.JBRFileDialogService")
                    .withStatic("getFileDialog", "get", "com.jetbrains.desktop.JBRFileDialog")
                .proxy("com.jetbrains.JBRFileDialog", "com.jetbrains.desktop.JBRFileDialog")
                .service("com.jetbrains.CustomWindowDecoration", "java.awt.Window$CustomWindowDecoration")
                .service("com.jetbrains.RoundedCornersManager")
                    .withStatic("setRoundedCorners", "setRoundedCorners", "sun.lwawt.macosx.CPlatformWindow",
                                "sun.awt.windows.WWindowPeer")
                .service("com.jetbrains.DesktopActions")
                    .withStatic("setHandler", "setDesktopActionsHandler", "java.awt.Desktop")
                .clientProxy("java.awt.Desktop$DesktopActionsHandler", "com.jetbrains.DesktopActions$Handler")
                .service("com.jetbrains.ProjectorUtils")
                    .withStatic("overrideGraphicsEnvironment", "overrideLocalGraphicsEnvironment", "java.awt.GraphicsEnvironment")
                    .withStatic("setLocalGraphicsEnvironmentProvider", "setLocalGraphicsEnvironmentProvider", "java.awt.GraphicsEnvironment");
    }
}
