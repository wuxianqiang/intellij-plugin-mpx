// Copyright 2000-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.hxz.mpxjs.libraries.vuex.index

import com.intellij.lang.javascript.psi.JSImplicitElementProvider
import com.intellij.psi.stubs.StubIndexKey
import com.hxz.mpxjs.index.VueIndexBase

class VuexStoreIndex : VueIndexBase<JSImplicitElementProvider>(KEY) {
  companion object {
    val KEY: StubIndexKey<String, JSImplicitElementProvider> = StubIndexKey.createIndexKey("@mpxjs.store.index")
    val JS_KEY: String = createJSKey(KEY)
  }
}
