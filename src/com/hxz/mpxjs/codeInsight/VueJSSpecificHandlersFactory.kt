// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.hxz.mpxjs.codeInsight

import com.intellij.codeInsight.controlflow.ControlFlow
import com.intellij.lang.ecmascript6.resolve.ES6PsiUtil.isEmbeddedBlock
import com.intellij.lang.ecmascript6.resolve.JSFileReferencesUtil
import com.intellij.lang.injection.InjectedLanguageManager
import com.intellij.lang.javascript.JavaScriptSpecificHandlersFactory
import com.intellij.lang.javascript.psi.JSControlFlowScope
import com.intellij.lang.javascript.psi.JSElement
import com.intellij.lang.javascript.psi.impl.JSReferenceExpressionImpl
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.impl.source.resolve.ResolveCache
import com.hxz.mpxjs.codeInsight.controlflow.VueControlFlowBuilder
import com.hxz.mpxjs.codeInsight.refs.VueJSReferenceExpressionResolver
import com.hxz.mpxjs.lang.html.VueFileType

class VueJSSpecificHandlersFactory : JavaScriptSpecificHandlersFactory() {
  override fun createReferenceExpressionResolver(referenceExpression: JSReferenceExpressionImpl,
                                                 ignorePerformanceLimits: Boolean): ResolveCache.PolyVariantResolver<JSReferenceExpressionImpl> =
    VueJSReferenceExpressionResolver(referenceExpression, ignorePerformanceLimits)

  override fun getControlFlow(scope: JSControlFlowScope): ControlFlow {
    return VueControlFlowBuilder().buildControlFlow(scope)
  }

}

