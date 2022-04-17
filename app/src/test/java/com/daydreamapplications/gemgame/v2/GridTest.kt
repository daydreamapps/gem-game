package com.daydreamapplications.gemgame.v2

import com.daydreamapplications.gemgame.assertEquals
import org.junit.Test


class GridTest {

    private fun testGrid() {

    }

    @Test
    fun `row builder`() {
        val grid = Grid.build<Char> {
            row('a', 'b')
            row('c', 'd')
        }
        grid.toString() assertEquals "a, b\nc, d"
    }

    @Test
    fun `column builder`() {
        val grid = Grid.build<Char> {
            column('a', 'c')
            column('b', 'd')
        }
        grid.toString() assertEquals "a, b\nc, d"
    }

    @Test
    fun `to iterable`() {
        val grid = Grid.build<Char> {
            column('a', 'c')
            column('b', 'd')
        }
        grid.toIterable() assertEquals listOf('a', 'c', 'b', 'd')
    }
}