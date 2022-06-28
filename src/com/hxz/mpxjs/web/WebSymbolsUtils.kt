// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.hxz.mpxjs.web

import com.intellij.javascript.web.codeInsight.html.elements.WebSymbolElementDescriptor
import com.hxz.mpxjs.model.VueModelDirectiveProperties
import com.hxz.mpxjs.model.VueModelDirectiveProperties.Companion.DEFAULT_EVENT
import com.hxz.mpxjs.model.VueModelDirectiveProperties.Companion.DEFAULT_PROP
import com.hxz.mpxjs.web.VueWebSymbolsAdditionalContextProvider.Companion.KIND_VUE_MODEL
import com.hxz.mpxjs.web.VueWebSymbolsAdditionalContextProvider.Companion.PROP_VUE_MODEL_EVENT
import com.hxz.mpxjs.web.VueWebSymbolsAdditionalContextProvider.Companion.PROP_VUE_MODEL_PROP

fun WebSymbolElementDescriptor.getModel(): VueModelDirectiveProperties =
  runNameMatchQuery(listOf(KIND_VUE_MODEL)).firstOrNull()
    ?.let {
      VueModelDirectiveProperties(prop = it.properties[PROP_VUE_MODEL_PROP] as? String ?: DEFAULT_PROP,
                                  event = it.properties[PROP_VUE_MODEL_EVENT] as? String ?: DEFAULT_EVENT)
    }
  ?: VueModelDirectiveProperties()