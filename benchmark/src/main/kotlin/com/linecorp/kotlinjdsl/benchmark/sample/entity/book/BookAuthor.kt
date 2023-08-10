package com.linecorp.kotlinjdsl.benchmark.entity.book

import com.linecorp.kotlinjdsl.benchmark.sample.annotation.CompositeId
import jakarta.persistence.*
import java.io.Serializable
import java.util.*

@Table(name = "book_author")
@IdClass(BookAuthor.BookAuthorId::class)
@Entity
class BookAuthor(
    @Id
    @Column(name = "author_id")
    val authorId: Long,
) {
    @Id
    @ManyToOne
    @JoinColumn(name = "isbn")
    lateinit var book: Book

    private val bookAuthorId get() = BookAuthorId(book.isbn, authorId)

    override fun equals(other: Any?): Boolean =
        Objects.equals(bookAuthorId, (other as? BookAuthor)?.bookAuthorId)

    override fun hashCode(): Int =
        Objects.hashCode(bookAuthorId)

    override fun toString(): String =
        "BookAuthor(bookAuthorId=$bookAuthorId)"

    @CompositeId
    data class BookAuthorId(
        val book: Isbn,
        val authorId: Long,
    ) : Serializable
}
