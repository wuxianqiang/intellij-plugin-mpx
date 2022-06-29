// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.hxz.mpxjs.lang.html.lexer

import com.intellij.psi.xml.XmlTokenType

interface MpxTokenTypes : XmlTokenType {
  companion object {
    @JvmField
    val INTERPOLATION_START = MpxTokenType("MPX:INTERPOLATION_START")

    @JvmField
    val INTERPOLATION_END = MpxTokenType("MPX:INTERPOLATION_END")

    @JvmField
    val INTERPOLATION_EXPR = MpxTokenType("MPX:INTERPOLATION_EXPR")
  }
}
