package com.hxz.mpxjs.pinkfloyded

import com.intellij.json.psi.JsonProperty
import com.intellij.json.psi.JsonStringLiteral
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReference
import com.intellij.psi.impl.source.xml.XmlDescriptorUtil
import com.intellij.psi.xml.XmlTag
import com.intellij.xml.XmlAttributeDescriptor
import com.intellij.xml.XmlElementDescriptor
import com.intellij.xml.impl.schema.AnyXmlAttributeDescriptor

class WxmlCustomComponentDescriptor(private val element: PsiElement) : WXMLBasicElementDescriptor() {
    override fun getDefaultValue(): String? {
        return null
    }

    override fun getName(context: PsiElement?): String {
        return this.name
    }

    override fun getName(): String {
//      return this.element.name
        return "view"
    }

    override fun getElementsDescriptors(context: XmlTag?): Array<XmlElementDescriptor> {
        return emptyArray()
    }

    override fun init(element: PsiElement?) {

    }

    override fun getDefaultName(): String {
        return this.name
    }


    override fun getQualifiedName(): String {
        return this.name
    }


    override fun getDeclaration(): PsiElement {
        return this.element
    }

    override fun getAttributeDescriptor(attributeName: String, context: XmlTag?): XmlAttributeDescriptor? {
//        return super.getAttributeDescriptor(attributeName, context) ?: WXMLUtils.getCustomComponentAttributeDescriptors(
//            this
//        ).find {
//            it.name == attributeName
//        } ?: AnyXmlAttributeDescriptor(attributeName)
          return AnyXmlAttributeDescriptor(attributeName)
    }

}
