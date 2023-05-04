// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.hxz.mpxjs.liveTemplate

import com.intellij.codeInsight.template.TemplateContextType
import com.intellij.psi.PsiFile
import com.hxz.mpxjs.VueBundle

class VueTemplateLiveTemplateContextType : TemplateContextType(VueBundle.message("vue.live.template.context.template")) {
  override fun isInContext(file: PsiFile, offset: Int): Boolean {
    return VueBaseLiveTemplateContextType.evaluateContext(file, offset, forTagInsert = true)
  }
}
