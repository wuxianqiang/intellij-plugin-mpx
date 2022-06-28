// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.hxz.mpxjs.pug

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.hxz.mpxjs.lang.getVueTestDataPath

class VuePugFoldingTest : BasePlatformTestCase() {
  fun testPugInTemplate() {
    myFixture.testFolding(getVueTestDataPath() + "/pug/" + getTestName(false) + ".pug")
  }
}