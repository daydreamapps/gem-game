package com.daydreamapplications.gemgame

import com.google.common.truth.Truth

infix fun Any?.assertEquals(other: Any) {
    Truth.assertThat(this).isEqualTo(other)
}