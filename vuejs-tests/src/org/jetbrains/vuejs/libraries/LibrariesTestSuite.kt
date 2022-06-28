// Copyright 2000-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.hxz.mpxjs.libraries

import com.hxz.mpxjs.libraries.cssModules.CssModulesTest
import com.hxz.mpxjs.libraries.eslint.VueESLintImportCodeStyleTest
import com.hxz.mpxjs.libraries.nuxt.NuxtTestSuite
import com.hxz.mpxjs.libraries.templateLoader.TemplateLoaderCompletionTest
import com.hxz.mpxjs.libraries.vueLoader.VueLoaderTest
import com.hxz.mpxjs.libraries.vuelidate.VuelidateTest
import com.hxz.mpxjs.libraries.vuex.VuexTestSuite
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
  NuxtTestSuite::class,
  VuexTestSuite::class,
  VueLoaderTest::class,
  TemplateLoaderCompletionTest::class,
  VuelidateTest::class,
  CssModulesTest::class,
  VueESLintImportCodeStyleTest::class
)
class LibrariesTestSuite
