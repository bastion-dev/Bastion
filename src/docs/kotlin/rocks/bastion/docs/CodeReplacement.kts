package rocks.bastion.docs

import java.io.File

import org.jetbrains.kotlin.maven.ExecuteKotlinScriptMojo

val mojo = ExecuteKotlinScriptMojo.INSTANCE

class Library(val sources: File, val boundary: Regex = Regex("// docs:([a-zA-Z0-9\\-_]+)")) {

    val snippets = sources.walk().filter { it.name.endsWith(".java", true) || it.name.endsWith(".groovy") }
            .map {
                val text = it.readText()
                val matches = boundary.findAll(text)
                matches.groupBy { it.groupValues[1] }
                        .mapValues { text.substring(it.value[0].range.last + 1, it.value[1].range.first).trimIndent() }
            }.reduce { left, right -> left + right }

    fun expand(key: String) = snippets[key]

}

class CodeReplacement(val source: File, val destination: File, val library: Library, val placeholder: Regex = Regex("\\[ex:([a-zA-Z0-9\\-_]+?)\\]")) {

    fun replace(): Unit {
        val text = source.readText()
        val replacedText = placeholder.replace(text) {
            library.expand(it.groupValues[1])!!
        }
        destination.writeText(replacedText)
    }

}

val library = Library(File(mojo.project.basedir, "src/test"))
val codeReplacement = CodeReplacement(File(mojo.project.basedir, "src/docs/asciidoc/overview.adoc"), File(mojo.project.basedir, "docs/overview.adoc"), library)
codeReplacement.replace()