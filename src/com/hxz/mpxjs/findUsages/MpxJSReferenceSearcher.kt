// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.hxz.mpxjs.findUsages

import com.intellij.lang.ecmascript6.resolve.ES6PsiUtil
import com.intellij.lang.javascript.psi.JSField
import com.intellij.lang.javascript.psi.JSFunction
import com.intellij.lang.javascript.psi.JSParameter
import com.intellij.lang.javascript.psi.ecmal4.JSAttributeList
import com.intellij.lang.javascript.psi.ecmal4.JSClass
import com.intellij.lang.javascript.psi.ecmal4.JSQualifiedNamedElement
import com.intellij.lang.javascript.psi.util.JSUtils
import com.intellij.lang.typescript.psi.TypeScriptPsiUtil
import com.intellij.openapi.application.QueryExecutorBase
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.search.LocalSearchScope
import com.intellij.psi.search.QuerySearchRequest
import com.intellij.psi.search.SearchRequestCollector
import com.intellij.psi.search.searches.ReferencesSearch
import com.intellij.util.PairProcessor
import com.intellij.util.Processor
import com.intellij.util.castSafelyTo
import com.hxz.mpxjs.VueBundle
import com.hxz.mpxjs.codeInsight.toAsset
import com.hxz.mpxjs.context.isVueContext
import com.hxz.mpxjs.index.findModule
import com.hxz.mpxjs.model.VueModelManager
import com.hxz.mpxjs.model.VueRegularComponent
import com.hxz.mpxjs.refactoring.VueRefactoringUtils

class VueJSReferenceSearcher : QueryExecutorBase<PsiReference, ReferencesSearch.SearchParameters>(true) {

  override fun processQuery(queryParameters: ReferencesSearch.SearchParameters, consumer: Processor<in PsiReference>) {
    val element = queryParameters.elementToSearch
    val component = VueRefactoringUtils.getComponent(element)

    if (component != null) {
      // TODO migrate to use VueModelManager.findEnclosingComponent()
      val content = findModule(element) ?: return
      val defaultExport = ES6PsiUtil.findDefaultExport(content) as? PsiElement ?: return
      val collector = SearchRequestCollector(queryParameters.optimizer.searchSession)
      queryParameters.optimizer.searchQuery(
        QuerySearchRequest(ReferencesSearch.search(defaultExport, queryParameters.effectiveSearchScope), collector,
                           false, PairProcessor { reference, _ -> consumer.process(reference) }))
      //We are searching for <component-a> and <ComponentA> tags
      //Original component name can't be fromAsset (name: "component-a")
      val names = setOf(component.name, toAsset(component.name))
      for (name in names) {
        queryParameters.optimizer.searchWord(name, queryParameters.effectiveSearchScope, false,
                                             component)
      }
    }

    if (element is JSQualifiedNamedElement
        && element.accessType === JSAttributeList.AccessType.PRIVATE
        && (element is JSField
            || (element is JSFunction && element.context is JSClass)
            || (element is JSParameter && TypeScriptPsiUtil.isFieldParameter(element)))) {
      val name = element.name
      if (name != null && isVueContext(element)) {
        JSUtils.getMemberContainingClass(element)
          ?.let { VueModelManager.getComponent(it) }
          ?.castSafelyTo<VueRegularComponent>()
          ?.template
          ?.source
          ?.let {
            val searchScope = LocalSearchScope(arrayOf(it), VueBundle.message("vue.search.scope.template.name"), false)
            queryParameters.optimizer.searchWord(name, searchScope, true, element)
          }
      }
    }
  }

}
