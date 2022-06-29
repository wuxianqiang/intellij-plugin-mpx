package com.hxz.mpxjs.lang.expr.highlighting

import com.hxz.mpxjs.lang.html.VueLanguage
import com.intellij.codeInsight.highlighting.HighlightErrorFilter
import com.intellij.lang.html.HTMLLanguage
import com.intellij.psi.xml.XmlToken
import com.intellij.psi.xml.XmlTokenType
import com.intellij.xml.psi.XmlPsiBundle
import org.jetbrains.annotations.NotNull
import com.intellij.lang.injection.InjectedLanguageManager
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
            //val valueTextRange = xmlAttributeValue.valueTextRange
            if (xmlAttribute.name == "style") {
                return false
            }
        }
        return true
    }
}
