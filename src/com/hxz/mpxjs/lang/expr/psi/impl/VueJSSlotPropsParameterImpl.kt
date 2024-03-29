// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.hxz.mpxjs.lang.expr.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.lang.javascript.psi.JSType
import com.intellij.lang.javascript.psi.impl.JSParameterImpl
import com.intellij.lang.javascript.psi.util.JSDestructuringUtil
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.search.LocalSearchScope
import com.intellij.psi.search.SearchScope
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.xml.XmlTag
import com.hxz.mpxjs.lang.expr.psi.VueJSSlotPropsParameter
import com.hxz.mpxjs.model.getSlotTypeFromContext
import com.hxz.mpxjs.types.asCompleteType

class VueJSSlotPropsParameterImpl(node: ASTNode) : JSParameterImpl(node), VueJSSlotPropsParameter {
  override fun hasBlockScope(): Boolean = true

  override fun getUseScope(): SearchScope {
    return declarationScope?.let { LocalSearchScope(it) } ?: LocalSearchScope.EMPTY
  }

  override fun getDeclarationScope(): PsiElement? =
    PsiTreeUtil.getContextOfType(this, XmlTag::class.java, PsiFile::class.java)

  override fun calculateType(): JSType? =
    JSDestructuringUtil.getTypeFromInitializer(this) {
      getSlotTypeFromContext(this)
    }?.asCompleteType()

}
