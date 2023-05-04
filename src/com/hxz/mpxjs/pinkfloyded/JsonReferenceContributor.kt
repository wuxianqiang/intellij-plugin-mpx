package com.hxz.mpxjs.pinkfloyded
import com.intellij.json.psi.*
import com.intellij.lang.javascript.psi.JSObjectLiteralExpression
import com.intellij.lang.javascript.psi.JSProperty
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.*
import com.intellij.psi.util.elementType
import com.intellij.util.ProcessingContext

class JsonReferenceContributor : PsiReferenceContributor() {
    override fun registerReferenceProviders(psiReferenceRegistrar: PsiReferenceRegistrar) {
        psiReferenceRegistrar.registerReferenceProvider(PlatformPatterns.psiElement(JsonProperty::class.java), object :
            PsiReferenceProvider() {
            override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
                return PsiReference.EMPTY_ARRAY
            }
        })

        // 小程序的json配置文件中的usingComponents配置
        // 解析被引入的组件的路径
        psiReferenceRegistrar.registerReferenceProvider(PlatformPatterns.psiElement(JsonStringLiteral::class.java),
            object : PsiReferenceProvider() {
                override fun getReferencesByElement(
                    psiElement: PsiElement, processingContext: ProcessingContext
                ): Array<out PsiReference> {
                    psiElement as JsonStringLiteral
                    // 确定此元素是正确的usingComponents配置项的值
                    val parent = psiElement.parent
                    if (parent is JsonProperty && parent.value == psiElement) {
                        val usingComponentsProperty = parent.parent?.parent
                        if (usingComponentsProperty is JsonProperty && usingComponentsProperty.name == "usingComponents") {
                            // 找到usingComponents配置
                            val wrapObject = usingComponentsProperty.parent
                            if (wrapObject is JsonObject) {
                                return ComponentFileReferenceSet(psiElement).allReferences
                            }
                        }
                    }
                    return PsiReference.EMPTY_ARRAY
                }
            }
        )

    }
}
