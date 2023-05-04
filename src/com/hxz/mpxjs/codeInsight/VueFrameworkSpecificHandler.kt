// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.hxz.mpxjs.codeInsight

import com.intellij.lang.javascript.frameworks.JSFrameworkSpecificHandler
import com.intellij.psi.PsiElement
import com.hxz.mpxjs.context.hasPinia

class VueFrameworkSpecificHandler : JSFrameworkSpecificHandler {
  override fun useMoreAccurateEvaluation(context: PsiElement): Boolean =
    hasPinia(context)
}