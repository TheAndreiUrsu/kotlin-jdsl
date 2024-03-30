package com.linecorp.kotlinjdsl.support.hibernate.reactive

import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRendered
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderedParams
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderer
import io.mockk.every
import io.mockk.excludeRecords
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockkObject
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.hibernate.reactive.stage.Stage
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class JpqlStageStatelessSessionUtilsTest : WithAssertions {
    @MockK
    private lateinit var session: Stage.StatelessSession

    @MockK
    private lateinit var renderer: JpqlRenderer

    @MockK
    private lateinit var context: RenderContext

    @MockK
    private lateinit var query1: JpqlQuery<*>

    @MockK
    private lateinit var stageQuery1: Stage.Query<String>

    private val renderedQuery1 = "query"
    private val renderedParam1 = "queryParam1" to "queryParamValue1"
    private val renderedParam2 = "queryParam2" to "queryParamValue2"

    private val queryParam1 = "queryParam1" to "queryParamValue1"
    private val queryParam2 = "queryParam2" to "queryParamValue2"

    @BeforeEach
    @Suppress("UnusedEquals")
    fun setUp() {
        mockkObject(JpqlRendererHolder)

        every { JpqlRendererHolder.get() } returns renderer

        excludeRecords { JpqlRendererHolder.get() }
        excludeRecords { stageQuery1.equals(any()) }
    }

    @Test
    fun `createQuery() with a query and a return type`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any()) } returns rendered1
        every { session.createQuery(any<String>(), any<Class<String>>()) } returns stageQuery1
        every { stageQuery1.setParameter(any<String>(), any()) } returns stageQuery1

        // when
        val actual = JpqlStageStatelessSessionUtils.createQuery(session, query1, String::class, context)

        // then
        assertThat(actual).isEqualTo(stageQuery1)

        verifySequence {
            renderer.render(query1, context)
            session.createQuery(rendered1.query, String::class.java)
            stageQuery1.setParameter(renderedParam1.first, renderedParam1.second)
            stageQuery1.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }

    @Test
    fun `createQuery() with a query and query params and a return type`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any(), any()) } returns rendered1
        every { session.createQuery(any<String>(), any<Class<String>>()) } returns stageQuery1
        every { stageQuery1.setParameter(any<String>(), any()) } returns stageQuery1

        // when
        val actual = JpqlStageStatelessSessionUtils
            .createQuery(session, query1, mapOf(queryParam1, queryParam2), String::class, context)

        // then
        assertThat(actual).isEqualTo(stageQuery1)

        verifySequence {
            renderer.render(query1, mapOf(queryParam1, queryParam2), context)
            session.createQuery(rendered1.query, String::class.java)
            stageQuery1.setParameter(renderedParam1.first, renderedParam1.second)
            stageQuery1.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }

    @Test
    fun `createQuery() with a query`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any()) } returns rendered1
        every { session.createQuery<String>(any<String>()) } returns stageQuery1
        every { stageQuery1.setParameter(any<String>(), any()) } returns stageQuery1

        // when
        val actual = JpqlStageStatelessSessionUtils.createQuery<String>(session, query1, context)

        // then
        assertThat(actual).isEqualTo(stageQuery1)

        verifySequence {
            renderer.render(query1, context)
            session.createQuery<String>(rendered1.query)
            stageQuery1.setParameter(renderedParam1.first, renderedParam1.second)
            stageQuery1.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }

    @Test
    fun `createQuery() with a query and query params`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any(), any()) } returns rendered1
        every { session.createQuery<String>(any<String>()) } returns stageQuery1
        every { stageQuery1.setParameter(any<String>(), any()) } returns stageQuery1

        // when
        val actual = JpqlStageStatelessSessionUtils
            .createQuery<String>(session, query1, mapOf(queryParam1, queryParam2), context)

        // then
        assertThat(actual).isEqualTo(stageQuery1)

        verifySequence {
            renderer.render(query1, mapOf(queryParam1, queryParam2), context)
            session.createQuery<String>(rendered1.query)
            stageQuery1.setParameter(renderedParam1.first, renderedParam1.second)
            stageQuery1.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }
}
