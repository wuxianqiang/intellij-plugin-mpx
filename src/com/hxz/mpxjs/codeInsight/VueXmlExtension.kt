// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.hxz.mpxjs.codeInsight

import com.intellij.html.webSymbols.WebSymbolsXmlExtension
import com.intellij.html.webSymbols.elements.WebSymbolElementDescriptor
import com.intellij.lang.html.HTMLLanguage
import com.intellij.lang.javascript.psi.JSExpression
import com.intellij.lang.javascript.psi.resolve.JSResolveUtil
import com.intellij.openapi.fileTypes.FileTypeRegistry
import com.intellij.psi.PsiFile
import com.intellij.psi.xml.XmlTag
import com.hxz.mpxjs.codeInsight.attributes.VueAttributeNameParser
import com.hxz.mpxjs.context.isVueContext
import com.hxz.mpxjs.lang.html.VueFileType
import com.hxz.mpxjs.lang.html.VueLanguage
import com.hxz.mpxjs.model.VueComponent
import com.hxz.mpxjs.model.VueModelDirectiveProperties
import com.hxz.mpxjs.model.VueModelManager
import com.hxz.mpxjs.web.getModel
import com.intellij.psi.impl.source.xml.SchemaPrefix
import com.intellij.openapi.util.TextRange

class VueXmlExtension : WebSymbolsXmlExtension() {
  override fun isAvailable(file: PsiFile?): Boolean =
    file?.let {
      it.language is VueLanguage
      // Support extension in plain HTML with Vue.js lib, PHP, Twig and others
      || (HTMLLanguage.INSTANCE in it.viewProvider.languages && isVueContext(it))
    } == true

  override fun isRequiredAttributeImplicitlyPresent(tag: XmlTag?, attrName: String?): Boolean {
    if (attrName == null) return false

    val toAssetName = toAsset(attrName)
    val fromAssetName = fromAsset(attrName)

    return tag?.attributes?.find { attr ->
      if (attr.name == "v-bind") {
        return@find attr.valueElement
          ?.findJSExpression<JSExpression>()
          ?.let { JSResolveUtil.getElementJSType(it) }
          ?.asRecordType()
          ?.findPropertySignature(toAssetName) != null
      }
      val info = VueAttributeNameParser.parse(attr.name, tag)
      var name: String? = null
      if (info is VueAttributeNameParser.VueDirectiveInfo) {
        if (info.directiveKind == VueAttributeNameParser.VueDirectiveKind.MODEL) {
          name = info.arguments
                 ?: (tag.descriptor as? WebSymbolElementDescriptor)?.getModel()?.prop
                 ?: VueModelDirectiveProperties.getDefault(tag).prop
        }
        else if (info.directiveKind === VueAttributeNameParser.VueDirectiveKind.BIND
                 && info.arguments != null) {
          name = info.arguments
        }
      }
      return@find fromAsset(name ?: info.name) == fromAssetName
    } != null
  }

  override fun isSelfClosingTagAllowed(tag: XmlTag): Boolean =
    isVueComponentTemplateContext(tag)
    || super.isSelfClosingTagAllowed(tag)

  private fun isVueComponentTemplateContext(tag: XmlTag) =
    tag.containingFile.let {
      FileTypeRegistry.getInstance().isFileOfType(it.virtualFile, VueFileType.INSTANCE)
      || VueModelManager.findEnclosingContainer(it) is VueComponent
    }

  override fun getPrefixDeclaration(context: XmlTag, namespacePrefix: String?): SchemaPrefix? {
    if (namespacePrefix != null && (namespacePrefix == "wx"
              || namespacePrefix == "bind"
              || namespacePrefix == "catch"
              || namespacePrefix == "model"
              || namespacePrefix == "mark"
              || namespacePrefix == "mut-bind"
              || (context.containingFile.fileType == VueFileType.INSTANCE && namespacePrefix == "qq"))) {
      findAttributeSchema(context, namespacePrefix)
        ?.let { return it }
    }
    return super.getPrefixDeclaration(context, namespacePrefix)
  }

  private fun findAttributeSchema(context: XmlTag, namespacePrefix: String): SchemaPrefix? {
    return context.attributes
      .find { it.name.startsWith("$namespacePrefix:") }
      ?.let { SchemaPrefix(it, TextRange.create(0, namespacePrefix.length), namespacePrefix) }
  }

}
