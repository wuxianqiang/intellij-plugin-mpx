package com.hxz.mpxjs.lang.expr.highlighting

import com.hxz.mpxjs.lang.html.VueLanguage
import com.intellij.codeInsight.highlighting.HighlightErrorFilter
import com.intellij.psi.PsiErrorElement
import com.intellij.psi.util.parentOfType
import com.intellij.psi.xml.XmlAttribute
import com.intellij.psi.xml.XmlAttributeValue

class MpxJSErrorFilter : HighlightErrorFilter() {
    override fun shouldHighlightErrorElement(psiErrorElement: PsiErrorElement): Boolean {
        val containingFile = psiErrorElement.containingFile
        if (containingFile.language == VueLanguage.INSTANCE) {
            val xmlAttributeValue = psiErrorElement.parentOfType<XmlAttributeValue>() ?: return true
            val xmlAttribute = xmlAttributeValue.parentOfType<XmlAttribute>() ?: return true
            if (xmlAttribute.name == "style" || xmlAttribute.name == "wx:style") {
                return false
            }
        }
        return true
    }
}

