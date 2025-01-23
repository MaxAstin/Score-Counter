package com.github.maxastin.scorecounter.features.camera

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.maxastin.scorecounter.R
import com.github.maxastin.scorecounter.common.ui.clickableWithoutIndication
import com.github.maxastin.scorecounter.common.ui.components.button.ScoreCounterDialogButton
import com.github.maxastin.scorecounter.common.ui.preview.BooleanProvider
import com.github.maxastin.scorecounter.common.ui.preview.LocalePreview
import com.github.maxastin.scorecounter.common.ui.theme.ScoreCounterTheme
import com.github.maxastin.scorecounter.common.util.getCameraProvider
import java.io.ByteArrayOutputStream
import androidx.compose.ui.tooling.preview.Preview as ComposePreview

@Composable
fun CameraScreen(navController: NavHostController) {
    val viewModel: CameraViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    var capturedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val onAction = remember {
        { action: Camera.Action ->
            viewModel.onAction(action)
        }
    }

    Content(
        state = state,
        bitmap = capturedBitmap,
        navController = navController,
        onAction = onAction,
        onImageCaptured = {
            capturedBitmap = it
            viewModel.onAction(Camera.Action.ProcessImage)
        },
        onCameraError = {
            viewModel.onAction(Camera.Action.CameraError)
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun Content(
    state: Camera.State,
    bitmap: Bitmap?,
    navController: NavHostController,
    onAction: (Camera.Action) -> Unit,
    onImageCaptured: (Bitmap) -> Unit,
    onCameraError: (Exception) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (state) {
        Camera.State.Camera -> {
            CameraComponent(
                onImageCaptured = onImageCaptured,
                onCameraError = onCameraError,
                modifier = modifier
            )
        }

        is Camera.State.Picture -> {
            Box(modifier = modifier) {
                bitmap?.let {
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(32.dp)
                        .align(Alignment.Center)
                        .clip(RoundedCornerShape(16.dp))
                        .background(color = ScoreCounterTheme.colors.background)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = spacedBy(16.dp)
                ) {
                    if (state.failed) {
                        Text(
                            text = stringResource(R.string.camera_processing_failed_title),
                            color = ScoreCounterTheme.colors.important,
                            style = ScoreCounterTheme.typography.titleMedium,
                        )
                        Text(
                            text = stringResource(R.string.camera_processing_failed_description),
                            color = ScoreCounterTheme.colors.onBackground,
                            style = ScoreCounterTheme.typography.bodyMedium,
                        )
                        Row(
                            horizontalArrangement = spacedBy(8.dp)
                        ) {
                            ScoreCounterDialogButton(
                                modifier = Modifier.weight(1f),
                                text = stringResource(R.string.common_cancel),
                                onClick = {
                                    navController.popBackStack()
                                }
                            )
                            ScoreCounterDialogButton(
                                modifier = Modifier.weight(1f),
                                text = stringResource(R.string.camera_retry),
                                backgroundColor = ScoreCounterTheme.colors.primary,
                                textColor = ScoreCounterTheme.colors.onSurface,
                                onClick = {
                                    onAction(Camera.Action.RetryClick)
                                }
                            )
                        }
                    } else {
                        CircularProgressIndicator(
                            modifier = Modifier.size(32.dp),
                            color = ScoreCounterTheme.colors.primary
                        )
                        Text(
                            text = stringResource(R.string.camera_processing),
                            color = ScoreCounterTheme.colors.onBackground,
                            style = ScoreCounterTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CameraComponent(
    onImageCaptured: (Bitmap) -> Unit,
    onCameraError: (Exception) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val previewView = remember {
        PreviewView(context).apply {
            implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            scaleType = PreviewView.ScaleType.FIT_CENTER
        }
    }
    val imageCapture = remember { ImageCapture.Builder().build() }

    LaunchedEffect(Unit) {
        val cameraProvider = context.getCameraProvider()
        val lensFacing = if (cameraProvider.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA)) {
            CameraSelector.LENS_FACING_BACK
        } else {
            return@LaunchedEffect
        }
        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(lensFacing)
            .build()
        val preview = Preview.Builder().build()

        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageCapture
            )
        } catch (exception: Exception) {
            onCameraError(exception)
        }

        preview.surfaceProvider = previewView.surfaceProvider
    }

    Box(modifier = modifier) {
        AndroidView(
            factory = { previewView },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(
            modifier = Modifier
                .padding(32.dp)
                .size(64.dp)
                .align(Alignment.BottomCenter)
                .clip(CircleShape)
                .background(color = ScoreCounterTheme.colors.onSurface)
                .clickableWithoutIndication {
                    imageCapture.takePictureAsBitmap(
                        context = context,
                        onError = {
                            onCameraError(it)
                        },
                        onImageCaptured = onImageCaptured
                    )
                }
        )
    }
}

private fun ImageCapture.takePictureAsBitmap(
    context: Context,
    onError: (Exception) -> Unit,
    onImageCaptured: (Bitmap) -> Unit
) {
    val outputStream = ByteArrayOutputStream()
    val outputOptions = ImageCapture.OutputFileOptions.Builder(outputStream).build()

    takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                val byteArray = outputStream.toByteArray()
                val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                onImageCaptured(bitmap)
            }

            override fun onError(exception: ImageCaptureException) {
                onError(exception)
            }
        }
    )
}

@ComposePreview
@Composable
private fun CameraContentPreview() {
    Content(
        modifier = Modifier.fillMaxSize(),
        bitmap = null,
        navController = rememberNavController(),
        state = Camera.State.Camera,
        onAction = {},
        onCameraError = {},
        onImageCaptured = {}
    )
}

@LocalePreview
@Composable
private fun PictureContentPreview(@PreviewParameter(BooleanProvider::class) failed: Boolean) {
    Content(
        modifier = Modifier.fillMaxSize(),
        bitmap = null,
        navController = rememberNavController(),
        state = Camera.State.Picture(failed = failed),
        onAction = {},
        onCameraError = {},
        onImageCaptured = {}
    )
}