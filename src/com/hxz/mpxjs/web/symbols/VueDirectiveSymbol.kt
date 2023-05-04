// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.hxz.mpxjs.web.symbols

import com.intellij.model.Pointer
import com.intellij.util.containers.Stack
import com.intellij.webSymbols.*
import com.intellij.webSymbols.query.WebSymbolsNameMatchQueryParams
import com.hxz.mpxjs.codeInsight.fromAsset
import com.hxz.mpxjs.model.VueDirective
import com.hxz.mpxjs.model.VueModelVisitor
import com.hxz.mpxjs.web.VueWebSymbolsQueryConfigurator
import com.hxz.mpxjs.web.asWebSymbolPriority

class VueDirectiveSymbol(matchedName: String, directive: VueDirective, private val vueProximity: VueModelVisitor.Proximity) :
  VueScopeElementSymbol<VueDirective>(fromAsset(matchedName), directive) {

  override val kind: SymbolKind
    get() = VueWebSymbolsQueryConfigurator.KIND_VUE_DIRECTIVES

  override val name: String
    get() = name

  override val priority: WebSymbol.Priority
    get() = vueProximity.asWebSymbolPriority()

  override fun getSymbols(namespace: SymbolNamespace,
                          kind: SymbolKind,
                          name: String?,
                          params: WebSymbolsNameMatchQueryParams,
                          scope: Stack<WebSymbolsScope>): List<WebSymbolsScope> = emptyList()
//    if ((namespace == null || namespace == WebSymbol.NAMESPACE_HTML)
//        && (kind == VueWebSymbolsQueryConfigurator.KIND_VUE_DIRECTIVE_ARGUMENT || (name != null && kind == VueWebSymbolsQueryConfigurator.KIND_VUE_DIRECTIVE_MODIFIERS))) {
//      listOf(VueAnySymbol(this.origin, WebSymbol.NAMESPACE_HTML, kind, name ?: "Vue directive argument"))
//    }
//    else emptyList()

  override fun createPointer(): Pointer<VueDirectiveSymbol> {
    val component = item.createPointer()
    val matchedName = this.name
    val vueProximity = this.vueProximity
    return Pointer {
      component.dereference()?.let { VueDirectiveSymbol(matchedName, it, vueProximity) }
    }
  }
}
