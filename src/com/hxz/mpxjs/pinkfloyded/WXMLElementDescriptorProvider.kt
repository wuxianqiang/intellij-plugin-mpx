package com.hxz.mpxjs.pinkfloyded

import com.hxz.mpxjs.index.findTopLevelVueTags
import com.hxz.mpxjs.lang.html.VueLanguage
import com.intellij.psi.impl.source.xml.XmlElementDescriptorProvider
import com.intellij.psi.xml.XmlTag
import com.intellij.xml.XmlElementDescriptor
import com.intellij.lang.javascript.psi.JSEmbeddedContent
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.xml.XmlFile
import com.intellij.psi.xml.XmlText

class WXMLElementDescriptorProvider : XmlElementDescriptorProvider {
    override fun getDescriptor(xmlTag: XmlTag): XmlElementDescriptor? {
        if (xmlTag.language !is VueLanguage) {
            return null
        }
        val tagName = xmlTag.name.ifBlank { return null }
        if (tagName == "script" || tagName == "style" || tagName == "template") {
            return null
        }
        val jsonProperty = findCustomComponentJsonProperty(xmlTag)
        if (jsonProperty != null) {
            return WxmlCustomComponentDescriptor(jsonProperty)
        }
        return null
    }

    private fun findCustomComponentJsonProperty(xmlTag: XmlTag): PsiElement? {
        val tagName = xmlTag.name
        val wxmlPsiFile = xmlTag.containingFile as XmlFile
        val res = findTopLevelVueTags(wxmlPsiFile, "script")
        if (res.size == 2) {
            val lastScript = res[1]
            val scriptJson = PsiTreeUtil.findChildOfType(lastScript, JSEmbeddedContent::class.java)
            val scriptApplicationJson = PsiTreeUtil.findChildOfType(lastScript, XmlText::class.java)
            // name="json"
            if (scriptJson?.text?.indexOf(tagName)?.equals(-1) == false) {
                return lastScript
            }
            if (scriptApplicationJson?.text?.indexOf(tagName)?.equals(-1) == false) {
                return lastScript
            }
        }
        return null
    }
}
