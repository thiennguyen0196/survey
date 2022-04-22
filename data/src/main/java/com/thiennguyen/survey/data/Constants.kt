package com.thiennguyen.survey.data

class Constants {
    interface Authorization {
        companion object {
            const val HEADER_AUTHORIZATION = "Authorization"
        }
    }

    interface DateFormat {
        companion object {
            const val DAY_OF_WEEK = "EEEE, MMMM d"
        }
    }

    interface Page {
        companion object {
            const val PAGE_NUMBER = 1
            const val PAGE_SIZE = 2
        }
    }

    interface Retry {
        companion object {
            const val LIMIT = 3
        }
    }
}