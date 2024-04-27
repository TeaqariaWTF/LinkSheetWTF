package fe.linksheet.experiment.ui.overhaul.composable.component.util

import androidx.annotation.StringRes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString

typealias OptionalTextContent = TextContent?

@Stable
sealed class TextContent {
    abstract val content: @Composable () -> Unit
}

@Stable
class Default(text: String) : TextContent() {
    override val content: @Composable () -> Unit = {
        Text(text = text)
    }

    companion object {
        fun text(text: String): Default {
            return Default(text)
        }

        fun textOrNull(text: String?): Default? {
            return if (text != null) Default(text) else null
        }
    }
}

@Stable
class Resource(@StringRes id: Int, vararg formatArgs: Any) : TextContent() {
    override val content: @Composable () -> Unit = {
        Text(text = stringResource(id = id, formatArgs = formatArgs))
    }

    companion object {
        fun textContent(@StringRes id: Int, vararg formatArgs: Any): Resource {
            return Resource(id, *formatArgs)
        }
    }
}


@Stable
class Annotated(annotatedString: AnnotatedString) : TextContent() {
    override val content: @Composable () -> Unit = {
        Text(text = annotatedString)
    }

    companion object {
        inline fun buildAnnotatedTextContent(builder: AnnotatedString.Builder.() -> Unit): Annotated {
            return Annotated(buildAnnotatedString(builder))
        }
    }
}


@Stable
class ComposableTextContent(override val content: @Composable () -> Unit) : TextContent() {
    companion object {
        fun content(content: @Composable () -> Unit): ComposableTextContent {
            return ComposableTextContent(content)
        }
    }
}
