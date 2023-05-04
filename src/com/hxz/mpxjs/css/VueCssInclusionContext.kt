// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.hxz.mpxjs.css

import com.intellij.psi.PsiElement
import com.intellij.psi.css.resolve.CssInclusionContext
import com.hxz.mpxjs.lang.html.VueLanguage

class VueCssInclusionContext : CssInclusionContext() {
  override fun processAllCssFilesOnResolving(context: PsiElement): Boolean {
    return context.containingFile?.language == VueLanguage.INSTANCE
  }
}
