package com.areeb.whatsappstatussaver.utils.constants

interface Constants {
    interface TARGET_DIRECTORY {
        companion object {
            const val TARGET_DIRECTORY_KEY =
                "content://com.android.externalstorage.documents/tree/primary%3AAndroid%2Fmedia/document/primary%3AAndroid%2Fmedia%2Fcom.whatsapp%2FWhatsApp%2FMedia%2F.Statuses"
        }

        interface SHARED_PEREFENCES {
            companion object {
                const val IS_FOLDER_SELECTED = "isFolderSelected"
                const val SHARED_PEREFENCES = "sharedPreferences"
                const val TREE_URI_PATH = "treeUriPath"
                const val STORAGE_PERMISSION = "storage_permission"
            }
        }

        interface SHARING {
            companion object {
                const val IMAGE_URI = "image_uri"
                const val FRAGMENT_IMAGE_URI = "fragment_image_uri"
            }
        }
    }
}
