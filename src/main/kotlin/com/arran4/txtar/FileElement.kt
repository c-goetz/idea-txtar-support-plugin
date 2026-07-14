package com.arran4.txtar

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.openapi.util.TextRange
import com.intellij.psi.AbstractElementManipulator
import com.intellij.psi.ElementManipulators
import com.intellij.psi.LiteralTextEscaper
import com.intellij.psi.PsiLanguageInjectionHost
import com.intellij.psi.impl.source.tree.injected.InjectionBackgroundSuppressor

class FileElement(node: ASTNode) : ASTWrapperPsiElement(node),  PsiLanguageInjectionHost, InjectionBackgroundSuppressor {
    override fun isValidHost(): Boolean {
        return true
    }

    override fun updateText(text: String): PsiLanguageInjectionHost? {
        return ElementManipulators.handleContentChange(this, text)
    }

    override fun createLiteralTextEscaper(): LiteralTextEscaper<out PsiLanguageInjectionHost?> {
        return LiteralTextEscaper.createSimple(this)
    }
    internal class Manipulator: AbstractElementManipulator<FileElement>() {
        override fun handleContentChange(
            element: FileElement,
            range: TextRange,
            newContent: String?
        ): FileElement? {
            // no manipulation necessary
            return element
        }
    }
}