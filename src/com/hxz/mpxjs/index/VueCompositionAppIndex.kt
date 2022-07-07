package com.hxz.mpxjs.index

import com.intellij.lang.javascript.psi.JSImplicitElementProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.stubs.StubIndexKey

class VueCompositionAppIndex : VueIndexBase<PsiElement>(KEY, JS_KEY) {
    companion object {
        val KEY: StubIndexKey<String, PsiElement> =
            StubIndexKey.createIndexKey<String, PsiElement>("mpx.composition.app.index")

        val JS_KEY: String = createJSKey(KEY)
    }
}
