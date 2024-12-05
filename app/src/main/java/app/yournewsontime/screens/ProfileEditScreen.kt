package app.yournewsontime.screens

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.yournewsontime.data.repository.FirebaseAuthRepository
import app.yournewsontime.ui.components.DrawerContent
import app.yournewsontime.ui.components.Footer
import coil3.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileEditScreen(
    navController: NavController,
    authRepository: FirebaseAuthRepository
) {
    val context = LocalContext.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var isMenuOpen by remember { mutableStateOf(false) }

    LaunchedEffect(drawerState.isOpen) {
        isMenuOpen = drawerState.isOpen
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                navController,
                authRepository
            )
        },
        scrimColor = Color.Transparent
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Edit Profile",
                            color = Color.Gray.copy(alpha = 0.9f)
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.Gray
                            )
                        }
                    },
                    colors = TopAppBarColors(
                        titleContentColor = Color.Black,
                        containerColor = Color.Transparent,
                        scrolledContainerColor = Color.Transparent,
                        navigationIconContentColor = Color.Transparent,
                        actionIconContentColor = Color.Transparent
                    )
                )
            },
            bottomBar = {
                Footer(
                    navController = navController,
                    authRepository = authRepository,
                    modifier = Modifier.fillMaxWidth(),
                    onMenuClick = {
                        scope.launch {
                            if (drawerState.isClosed) {
                                drawerState.open()
                            } else {
                                drawerState.close()
                            }
                            drawerState.open()
                        }
                    },
                    isMenuOpen = isMenuOpen
                )
            }
        ) { padding ->
            ProfileEditBodyContent(
                navController = navController,
                authRepository = authRepository,
                context = context,
                padding = padding
            )
        }
    }
}

@Composable
fun ProfileEditBodyContent(
    navController: NavController,
    authRepository: FirebaseAuthRepository,
    context: Context,
    padding: PaddingValues
) {
    val scope = rememberCoroutineScope()
    val currentUser = authRepository.getCurrentUser()
    var profileImageUri by remember { mutableStateOf<String?>(null) }
    var profileImageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var showImagePickerDialog by remember { mutableStateOf(false) }

    LaunchedEffect(currentUser?.photoUrl) {
        profileImageUri = currentUser?.photoUrl?.toString()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (showImagePickerDialog) {
            ImagePickerDialog(
                onDismiss = { showImagePickerDialog = false },
                onSelectGallery = {
                    selectImageFromGallery(context) { uri ->
                        profileImageUri = uri?.toString()
                        // TODO: Save the updated URI to the database
                    }
                },
                onTakePhoto = {
                    takePhotoWithCamera(context) { bitmap ->
                        profileImageBitmap = bitmap
                        // TODO: Save the bitmap to database
                    }
                }
            )
        }

        if (profileImageBitmap != null) {
            Image(
                bitmap = profileImageBitmap!!.asImageBitmap(),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(128.dp)
                    .clip(CircleShape)
                    .clickable { showImagePickerDialog = true }
            )
        } else if (profileImageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(profileImageUri),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(128.dp)
                    .clip(CircleShape)
                    .clickable { showImagePickerDialog = true }
            )
        } else {
            Box(
                modifier = Modifier
                    .size(128.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
                    .clickable { showImagePickerDialog = true },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Upload",
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = currentUser?.email ?: "No email available",
            color = Color.Black
        )
    }
}

/**
 * Dialog to choose between gallery or camera.
 */
@Composable
fun ImagePickerDialog(
    onDismiss: () -> Unit,
    onSelectGallery: () -> Unit,
    onTakePhoto: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Choose an option") },
        text = { Text("How do you want to upload your profile picture?") },
        confirmButton = {
            Button(onClick = {
                onSelectGallery()
                onDismiss()
            }) {
                Text("Gallery")
            }
        },
        dismissButton = {
            Button(onClick = {
                onTakePhoto()
                onDismiss()
            }) {
                Text("Camera")
            }
        }
    )
}

/**
 * Opens the image picker for the user to select a profile picture.
 */
fun selectImageFromGallery(context: Context, onImageSelected: (Uri?) -> Unit) {
    val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    val activity = context as? Activity
    activity?.startActivityForResult(intent, 1001)
}

/**
 * Opens the camera to take a photo.
 */
fun takePhotoWithCamera(context: Context, onPhotoTaken: (Bitmap?) -> Unit) {
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    val activity = context as? Activity
    activity?.startActivityForResult(intent, 1002)
}

/**
 * Handles the result of the image picker or camera activity.
 */
fun Activity.handleImagePickerOrCameraResult(requestCode: Int, resultCode: Int, data: Intent?) {
    if (resultCode == Activity.RESULT_OK) {
        when (requestCode) {
            1001 -> { // Gallery
                val selectedImageUri = data?.data
                // TODO: Save the selected image locally or to the database
            }

            1002 -> { // Camera
                val photoBitmap = data?.extras?.get("data") as? Bitmap
                // TODO: Save the photo locally or to the database
            }
        }
    }
}