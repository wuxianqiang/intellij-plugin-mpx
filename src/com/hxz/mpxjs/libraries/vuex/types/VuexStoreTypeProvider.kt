// Copyright 2000-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.hxz.mpxjs.libraries.vuex.types

import com.intellij.lang.javascript.psi.JSParameter
import com.intellij.lang.javascript.psi.JSTypeUtils
import com.intellij.lang.javascript.psi.ecma6.TypeScriptField
import com.intellij.lang.javascript.psi.ecma6.TypeScriptParameter
import com.intellij.lang.javascript.psi.resolve.JSTypeEvaluator
import com.intellij.psi.PsiElement
import com.hxz.mpxjs.context.isVueContext
import com.hxz.mpxjs.libraries.vuex.VuexUtils.GETTERS
import com.hxz.mpxjs.libraries.vuex.VuexUtils.ROOT_GETTERS
import com.hxz.mpxjs.libraries.vuex.VuexUtils.ROOT_STATE
import com.hxz.mpxjs.libraries.vuex.VuexUtils.STATE
import com.hxz.mpxjs.libraries.vuex.VuexUtils.VUEX_PACKAGE
import com.hxz.mpxjs.libraries.vuex.VuexUtils.isActionContextParameter
import com.hxz.mpxjs.libraries.vuex.model.store.VuexStaticNamespace
import com.hxz.mpxjs.libraries.vuex.model.store.VuexStoreNamespace
import com.hxz.mpxjs.libraries.vuex.model.store.getNamespaceForGettersOrState

object VuexStoreTypeProvider {

  fun addTypeFromResolveResult(evaluator: JSTypeEvaluator, result: PsiElement): Boolean {
    if (result is TypeScriptField) {
      val typeConstructor = getTypeConstructor(result.name) ?: return false
      if (!isStoreField(result) || !isVueContext(result)) return false
      evaluator.addType(typeConstructor(result, VuexStaticNamespace.EMPTY))
      return true
    }
    else if (result is JSParameter) {
      if (result is TypeScriptParameter && result.jsType
          .let { it != null && !JSTypeUtils.isAnyType(it) })
        return false
      if (isActionContextParameter(result)) {
        if (!isVueContext(result)) return false
        evaluator.addType(VuexActionContextType(result))
        return true
      }
      val name = result.name
      val typeConstructor = getTypeConstructor(name) ?: return false
      val namespace: VuexStoreNamespace = getNamespaceForGettersOrState(result, name!!) ?: return false
      if (!isVueContext(result)) return false
      evaluator.addType(typeConstructor(result, namespace))
      return true
    }
    return false
  }

  private fun isStoreField(result: TypeScriptField): Boolean =
    result.namespace?.qualifiedName == "Store"
    && result.containingFile.virtualFile?.parent?.parent?.name == VUEX_PACKAGE

  private fun getTypeConstructor(name: String?) = when (name) {
    STATE, ROOT_STATE -> ::VuexContainerStateType
    GETTERS, ROOT_GETTERS -> ::VuexContainerGettersType
    else -> null
  }
}
