// Copyright 2000-2021 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.hxz.mpxjs.libraries.cssModules

import com.intellij.lang.javascript.psi.JSRecordType
import com.intellij.lang.javascript.psi.types.JSTypeSourceFactory
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.xml.XmlAttribute
import com.intellij.psi.xml.XmlFile
import com.intellij.util.castSafelyTo
import com.intellij.xml.util.HtmlUtil
import com.hxz.mpxjs.codeInsight.MODULE_ATTRIBUTE_NAME
import com.hxz.mpxjs.index.findTopLevelVueTags
import com.hxz.mpxjs.model.VueInstanceOwner
import com.hxz.mpxjs.model.createImplicitPropertySignature
import com.hxz.mpxjs.model.source.VueContainerInfoProvider

class VueCssModulesInfoProvider : VueContainerInfoProvider {

  override fun getThisTypeProperties(instanceOwner: VueInstanceOwner,
                                     standardProperties: MutableMap<String, JSRecordType.PropertySignature>): Collection<JSRecordType.PropertySignature> {
    val context = instanceOwner.source as? PsiFile ?: instanceOwner.source?.context
    return context?.containingFile
             ?.castSafelyTo<XmlFile>()
             ?.let { findTopLevelVueTags(it, HtmlUtil.STYLE_TAG_NAME) }
             ?.mapNotNull { tag ->
               PsiTreeUtil.getStubChildrenOfTypeAsList(tag, XmlAttribute::class.java)
                 .firstOrNull { it.name == MODULE_ATTRIBUTE_NAME }
             }
             ?.map { attr ->
               createImplicitPropertySignature(
                 attr.value?.takeIf { it.isNotEmpty() } ?: "\$style",
                 CssModuleType(attr.context!!, instanceOwner.source),
                 attr.context!!,
                 false,
                 true)
             }
             ?.toList()
           ?: emptyList()
  }

}