// Copyright 2000-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.hxz.mpxjs.lang

import com.hxz.mpxjs.lang.expr.VueJSParserTest
import com.hxz.mpxjs.lang.html.VueHighlightingLexerTest
import com.hxz.mpxjs.lang.html.VueIndexerTest
import com.hxz.mpxjs.lang.html.VueLexerTest
import com.hxz.mpxjs.lang.html.VueParserTest
import com.hxz.mpxjs.libraries.LibrariesTestSuite
import com.hxz.mpxjs.linters.tslint.VueESLintHighlightingTest
import com.hxz.mpxjs.linters.tslint.VueTypeScriptWithTslintTest
import com.hxz.mpxjs.pug.PugTemplateTest
import com.hxz.mpxjs.pug.VuePugFoldingTest
import com.hxz.mpxjs.service.VueTypeScriptServiceTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
  VueTestCommons::class,
  VueJSParserTest::class,
  VueLexerTest::class,
  VueHighlightingLexerTest::class,
  VueIndexerTest::class,
  VueParserTest::class,
  VueCompletionTest::class,
  VueCommenterTest::class,
  VueHighlightingTest::class,
  VueTypedHandlerTest::class,
  VueAttributeNameParserTest::class,
  VueResolveTest::class,
  VueFindUsagesTest::class,
  VueRenameTest::class,
  VueCssClassTest::class,
  VueParameterInfoTest::class,
  VueOptimizeImportTest::class,
  VueLiveTemplatesTest::class,
  VueIntentionsTest::class,
  VueInjectionTest::class,
  VueFormatterTest::class,
  VueRearrangerTest::class,
  VueExternalFilesLinkingTest::class,
  VueExtractComponentTest::class,
  VueDocumentationTest::class,
  VueWebTypesDocumentationTest::class,
  VueCreateTsVariableTest::class,
  VueCopyrightTest::class,
  VueAutoPopupTest::class,
  VueEmmetTest::class,
  VueTypeResolveTest::class,
  LibrariesTestSuite::class,
  PugTemplateTest::class,
  VuePugFoldingTest::class,
  VueModuleImportTest::class,
  VueCopyPasteTest::class,
  VueMoveModuleMemberTest::class,
  VueTypeScriptDuplicateTest::class,
  VueIntroduceVariableTest::class,
  VueTypeScriptLineMarkersTest::class,
  VueESLintHighlightingTest::class,
  VueTypeScriptServiceTest::class,
  VueMoveTest::class,
  VueTypeScriptWithTslintTest::class,
  VueTypeScriptHighlightingTest::class,
)
class VueTestSuite