package com.arran4.txtar

import com.intellij.lang.Language
import com.intellij.lang.LanguageParserDefinitions
import com.intellij.lang.LanguageUtil
import com.intellij.lang.injection.general.Injection
import com.intellij.lang.injection.general.LanguageInjectionContributor
import com.intellij.lang.injection.general.SimpleInjection
import com.intellij.openapi.fileTypes.FileTypeManager
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType

class FileInjector : LanguageInjectionContributor {
    override fun getInjection(context: PsiElement): Injection? {
        val type = context.elementType ?: return null
        if (type != TxtarElementTypes.FILE_CONTENT) return null
        val header = context.prevSibling.takeIf { it.elementType == TxtarElementTypes.HEADER } ?: return null
        val text = header.text ?: return null
        val stripped = text.trim().removePrefix("-- ").removeSuffix(" --").trim()
        val idx = stripped.lastIndexOf('.')
        if (idx == -1) {
            return null
        }
        val extension = stripped.substring(idx + 1)
        val fileType = FileTypeManager.getInstance().getFileTypeByExtension(extension)
        val language =  LanguageUtil.getFileTypeLanguage(fileType) ?: return null
        return SimpleInjection(language, "", "", null)
    }
}