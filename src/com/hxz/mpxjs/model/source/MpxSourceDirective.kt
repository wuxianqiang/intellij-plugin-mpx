// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.hxz.mpxjs.model.source

import com.intellij.psi.PsiElement
import com.hxz.mpxjs.model.VueDirective
import com.hxz.mpxjs.model.VueEntitiesContainer

class VueSourceDirective(name: String, override val source: PsiElement) : VueDirective {

  override val defaultName: String = name
  override val parents: List<VueEntitiesContainer> = emptyList()

}
