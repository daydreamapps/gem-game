package com.daydreamapplications.gemgame.game.v2

import com.daydreamapplications.gemgame.assertEquals
import org.junit.Test

class GridTest {

    @Test
    fun `row builder`() {
        val grid = Grid.build<Char> {
            row('a', 'b')
            row('c', 'd')
        }
        grid.toString() assertEquals "a,b\nc,d"
    }

    @Test
    fun `column builder`() {
        val grid = Grid.build<Char> {
            column('a', 'c')
            column('b', 'd')
        }
        grid.toString() assertEquals "a,b\nc,d"
    }

    @Test
    fun forEachIndexed() {
        val set = mutableSetOf<Square<Char>>()
        val grid = Grid.build<Char> {
            column('a', 'c')
            column('b', 'd')
        }
        grid.forEachIndexed { x, y, c ->
            set.add(Square(x, y, c))
        }

        set assertEquals setOf(
            Square(0, 0, 'a'),
            Square(1, 0, 'b'),
            Square(0, 1, 'c'),
            Square(1, 1, 'd'),
        )
    }

    @Test
    fun `columns from columns`() {
        Grid.build<Char> {
            column('a', 'c')
            column('b', 'd')
        }.columns() assertEquals listOf(
            listOf('a', 'c'),
            listOf('b', 'd'),
        )
    }

    @Test
    fun `columns from rows`() {
        Grid.build<Char> {
            row('a', 'b')
            row('c', 'd')
        }.columns() assertEquals listOf(
            listOf('a', 'c'),
            listOf('b', 'd'),
        )
    }

    @Test
    fun `rows from rows`() {
        Grid.build<Char> {
            row('a', 'b')
            row('c', 'd')
        }.rows() assertEquals listOf(
            listOf('a', 'b'),
            listOf('c', 'd'),
        )
    }

    @Test
    fun `rows from columns`() {
        Grid.build<Char> {
            column('a', 'c')
            column('b', 'd')
        }.rows() assertEquals listOf(
            listOf('a', 'b'),
            listOf('c', 'd'),
        )
    }

    @Test
    fun columnAt() {
        val grid = Grid.build<Char> {
            column('a', 'c')
            column('b', 'd')
        }
        grid.column(0) assertEquals listOf('a', 'c')
        grid.column(1) assertEquals listOf('b', 'd')
    }

    @Test
    fun rowAt() {
        val grid = Grid.build<Char> {
            row('a', 'b')
            row('c', 'd')
        }
        grid.row(0) assertEquals listOf('a', 'b')
        grid.row(1) assertEquals listOf('c', 'd')
    }

    @Test
    fun set() {
        val grid = Grid.build<Char> {
            row('a', 'b')
            row('c', 'd')
        }
        grid.set(xIndex = 1, yIndex = 1, value = 'e')

        grid.toString() assertEquals "a,b\nc,e"
    }

    @Test
    fun get() {
        val grid = Grid.build<Char> {
            row('a', 'b')
            row('c', 'd')
        }
        grid.get(xIndex = 1, yIndex = 1) assertEquals 'd'
    }
}