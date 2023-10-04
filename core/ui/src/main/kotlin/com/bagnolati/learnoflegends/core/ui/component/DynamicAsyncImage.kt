package com.bagnolati.learnoflegends.core.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter.State.Empty
import coil.compose.AsyncImagePainter.State.Error
import coil.compose.AsyncImagePainter.State.Loading
import coil.compose.AsyncImagePainter.State.Success
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.bagnolati.learnoflegends.core.ui.R

/**
 * A wrapper around [AsyncImage] that allow placeholder and show loader on loading image.
 */
@Composable
fun DynamicAsyncImage(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    contentDescription: String?,
    alpha: Float = 1f,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Crop,
    placeholder: Painter = painterResource(R.drawable.ic_default_placeholder),
    placeholderColorFilter: ColorFilter? = null
) {
    SubcomposeAsyncImage(
        modifier = modifier,
        model = imageUrl,
        alpha = alpha,
        alignment = Alignment.TopCenter,
        contentDescription = contentDescription
    ) {
        val state = painter.state
        if (imageUrl.isNullOrBlank() || state is Empty || state is Error) {
            Image(
                painter = placeholder,
                contentDescription = null,
                alignment = Alignment.Center,
                contentScale = contentScale,
                alpha = alpha,
                colorFilter = placeholderColorFilter
            )
        } else {
            when (painter.state) {
                is Empty, is Error -> {}
                is Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            strokeWidth = 2.dp
                        )
                    }
                }

                is Success -> {
                    SubcomposeAsyncImageContent(
                        contentScale = ContentScale.Crop,
                        alignment = alignment
                    )
                }
            }
        }
    }
}
