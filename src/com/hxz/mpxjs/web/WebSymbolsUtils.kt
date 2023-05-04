// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.hxz.mpxjs.web

import com.intellij.html.webSymbols.elements.WebSymbolElementDescriptor
import com.intellij.lang.javascript.psi.JSLiteralExpression
import com.intellij.model.Symbol
import com.intellij.refactoring.rename.api.RenameTarget
import com.intellij.webSymbols.WebSymbol
import com.intellij.webSymbols.refactoring.WebSymbolRenameTarget
import com.hxz.mpxjs.codeInsight.toAsset
import com.hxz.mpxjs.model.*
import com.hxz.mpxjs.web.VueWebSymbolsQueryConfigurator.Companion.KIND_VUE_MODEL
import com.hxz.mpxjs.web.VueWebSymbolsQueryConfigurator.Companion.PROP_VUE_MODEL_EVENT
import com.hxz.mpxjs.web.VueWebSymbolsQueryConfigurator.Companion.PROP_VUE_MODEL_PROP
import com.hxz.mpxjs.web.symbols.VueComponentSymbol
import com.hxz.mpxjs.web.symbols.VueDirectiveSymbol
import com.hxz.mpxjs.web.symbols.VueScopeElementSymbol
import com.intellij.webSymbols.WebSymbol.Companion.NAMESPACE_HTML

fun WebSymbolElementDescriptor.getModel(): VueModelDirectiveProperties =
  runNameMatchQuery(NAMESPACE_HTML, KIND_VUE_MODEL, "").firstOrNull()
    ?.let {
      VueModelDirectiveProperties(prop = it.properties[PROP_VUE_MODEL_PROP] as? String,
                                  event = it.properties[PROP_VUE_MODEL_EVENT] as? String)
    }
  ?: VueModelDirectiveProperties()

fun VueScopeElement.asWebSymbol(name: String, forcedProximity: VueModelVisitor.Proximity): WebSymbol? =
  when (this) {
    is VueComponent -> VueComponentSymbol(toAsset(name, true), this, forcedProximity)
    is VueDirective -> VueDirectiveSymbol(name, this, forcedProximity)
    else -> null
  }

fun createRenameTarget(symbol: Symbol): RenameTarget? =
  if (symbol is VueScopeElementSymbol<*> && symbol.source is JSLiteralExpression)
    WebSymbolRenameTarget(symbol)
  else null

fun VueModelVisitor.Proximity.asWebSymbolPriority(): WebSymbol.Priority =
  when (this) {
    VueModelVisitor.Proximity.LOCAL -> WebSymbol.Priority.HIGHEST
    VueModelVisitor.Proximity.APP -> WebSymbol.Priority.HIGH
    VueModelVisitor.Proximity.PLUGIN, VueModelVisitor.Proximity.GLOBAL -> WebSymbol.Priority.NORMAL
    VueModelVisitor.Proximity.OUT_OF_SCOPE -> WebSymbol.Priority.LOW
  }
