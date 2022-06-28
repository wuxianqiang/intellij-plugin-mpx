// Copyright 2000-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.hxz.mpxjs.libraries.vuex.model.store

import com.intellij.lang.javascript.psi.JSCallExpression
import com.intellij.lang.javascript.psi.JSImplicitElementProvider
import com.intellij.lang.javascript.psi.JSObjectLiteralExpression
import com.intellij.lang.javascript.psi.stubs.JSImplicitElement
import com.intellij.lang.javascript.psi.util.JSStubBasedPsiTreeUtil
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StubIndex
import com.intellij.psi.util.CachedValueProvider
import com.intellij.psi.util.CachedValuesManager
import com.intellij.psi.util.PsiModificationTracker
import com.intellij.util.castSafelyTo
import com.hxz.mpxjs.codeInsight.getStubSafeCallArguments
import com.hxz.mpxjs.codeInsight.getTextIfLiteral
import com.hxz.mpxjs.context.isVueContext
import com.hxz.mpxjs.libraries.nuxt.model.NuxtModelManager
import com.hxz.mpxjs.libraries.vuex.VuexUtils.REGISTER_MODULE
import com.hxz.mpxjs.libraries.vuex.VuexUtils.STORE
import com.hxz.mpxjs.libraries.vuex.index.VuexStoreIndex

object VuexModelManager {

  fun getVuexStoreContext(element: PsiElement): VuexStoreContext? {
    if (!isVueContext(element)) return null
    var stores = getAllVuexStores(element.project)
    // Introduce extension point if another provider would need to be added
    NuxtModelManager.getApplication(element)?.vuexStore?.let {
      stores = stores + it
    }
    val registeredModules = getRegisteredModules(element.project)
    return if (stores.isNotEmpty() || registeredModules.isNotEmpty())
      VuexStoreContextImpl(stores, registeredModules, element)
    else
      null
  }

  private fun getAllVuexStores(project: Project): List<VuexStore> {
    return CachedValuesManager.getManager(project).getCachedValue(project) {
      CachedValueProvider.Result.create(
        StubIndex.getElements(VuexStoreIndex.KEY, STORE, project,
                              GlobalSearchScope.projectScope(project),
                              JSImplicitElementProvider::class.java)
          .asSequence()
          .filterIsInstance<JSCallExpression>()
          .filter { call -> call.indexingData?.implicitElements?.find { it.userString == VuexStoreIndex.JS_KEY } != null }
          .map { VuexStoreImpl(it) }
          .toList(), PsiModificationTracker.MODIFICATION_COUNT)
    }
  }

  private fun getRegisteredModules(project: Project): List<VuexModule> {
    return CachedValuesManager.getManager(project).getCachedValue(project) {
      CachedValueProvider.Result.create(
        StubIndex.getElements(VuexStoreIndex.KEY, REGISTER_MODULE, project,
                              GlobalSearchScope.projectScope(project),
                              JSImplicitElementProvider::class.java)
          .asSequence()
          .filterIsInstance<JSCallExpression>()
          .mapNotNull { createRegisteredModule(it) }
          .toList(), PsiModificationTracker.MODIFICATION_COUNT)
    }
  }

  private fun createRegisteredModule(call: JSCallExpression): VuexModule? {
    val implicitElement = call.indexingData?.implicitElements?.find { it.userString == VuexStoreIndex.JS_KEY }
                          ?: return null

    val arguments = getStubSafeCallArguments(call)
    val nameElement = arguments.getOrNull(0)
                      ?: return null
    val path = getTextIfLiteral(nameElement)
               ?: return null

    val initializer = arguments.getOrNull(1)
                        ?.castSafelyTo<JSObjectLiteralExpression>()
                      ?: resolveFromImplicitElement(implicitElement)
                      ?: return null
    return VuexModuleImpl(path, initializer, nameElement)
  }

  private fun resolveFromImplicitElement(implicitElement: JSImplicitElement): PsiElement? {
    return JSStubBasedPsiTreeUtil.resolveLocally(implicitElement.typeString ?: return null,
                                                 implicitElement.context ?: return null)
  }

  private class VuexStoreContextImpl(override val rootStores: List<VuexStore>,
                                     override val registeredModules: List<VuexModule>,
                                     override val element: PsiElement) : VuexStoreContext

}
