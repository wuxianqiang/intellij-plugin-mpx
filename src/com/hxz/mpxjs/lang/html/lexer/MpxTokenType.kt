// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.hxz.mpxjs.lang.html.lexer

import com.intellij.psi.tree.IElementType
import org.jetbrains.annotations.NonNls
import com.hxz.mpxjs.lang.html.VueLanguage

class MpxTokenType(@NonNls debugName: String) : IElementType(debugName, VueLanguage.INSTANCE)
