// Copyright 2000-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.hxz.mpxjs.lang.html

import com.intellij.javascript.web.html.WebFrameworkHtmlFileType

class VueFileType private constructor() : WebFrameworkHtmlFileType(VueLanguage.INSTANCE, "Mpx.js", "mpx") {
  companion object {
    @JvmField
    val INSTANCE: VueFileType = VueFileType()
  }
}
