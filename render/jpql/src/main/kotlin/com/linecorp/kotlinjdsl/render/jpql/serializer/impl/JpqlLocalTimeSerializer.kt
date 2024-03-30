package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlLocalTime
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlLocalTimeSerializer : JpqlSerializer<JpqlLocalTime> {
    override fun handledType(): KClass<JpqlLocalTime> {
        return JpqlLocalTime::class
    }

    override fun serialize(part: JpqlLocalTime, writer: JpqlWriter, context: RenderContext) {
        writer.write("LOCAL TIME")
    }
}
