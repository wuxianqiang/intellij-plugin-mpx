package com.hxz.mpxjs.pinkfloyded

import com.intellij.psi.impl.source.xml.XmlElementDescriptorProvider
import com.intellij.psi.xml.XmlAttribute
import com.intellij.psi.xml.XmlTag
import com.intellij.xml.XmlAttributeDescriptor
import com.intellij.xml.XmlElementDescriptor
import com.intellij.xml.XmlElementsGroup
import com.intellij.xml.XmlNSDescriptor
import com.intellij.xml.impl.schema.AnyXmlAttributeDescriptor

abstract class WXMLBasicElementDescriptor : XmlElementDescriptor {
    override fun getContentType(): Int {
        return XmlElementDescriptor.CONTENT_TYPE_ANY
    }

    override fun getTopGroup(): XmlElementsGroup? {
        return null
    }

    override fun getNSDescriptor(): XmlNSDescriptor? {
        return null
    }

    override fun getElementDescriptor(child: XmlTag, context: XmlTag): XmlElementDescriptor? {
        return XmlElementDescriptorProvider.EP_NAME.findExtension(WXMLElementDescriptorProvider::class.java)
            ?.getDescriptor(child)
    }

    override fun getAttributeDescriptor(attributeName: String, context: XmlTag?): XmlAttributeDescriptor? {
        return AnyXmlAttributeDescriptor(attributeName)
    }

    override fun getAttributeDescriptor(attribute: XmlAttribute?): XmlAttributeDescriptor? {
        return this.getAttributeDescriptor(attribute?.name ?: return null, attribute.parent)
    }

    override fun getAttributesDescriptors(p0: XmlTag?): Array<XmlAttributeDescriptor> {
        return emptyArray()
    }
}
