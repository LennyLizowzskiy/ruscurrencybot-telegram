package models.telegrambot.other

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val is_bot: Boolean? = false,
    val first_name: String,
    val last_name: String? = null,
    val username: String? = null,
    val language_code: String? = null,
    val can_join_groups: Boolean? = false,
    val can_read_all_group_messages: Boolean? = false,
    val supports_inline_queries: Boolean? = false
)