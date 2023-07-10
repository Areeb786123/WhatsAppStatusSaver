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
            }
        }
    }
}
