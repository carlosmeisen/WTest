package com.wtest.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Postal Code model
 *
 * @property id
 * @property postalCodeNum
 * @property postalCodeExt
 * @property postalDesignation
 */
@Entity
    (tableName = "postalcode_table")
data class PostalCode(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var postalCodeNum: String,
    var postalCodeExt: String,
    var postalDesignation: String,

    ) : Serializable {
    constructor() : this(
        0,
        "",
        "",
        "",
    )
}