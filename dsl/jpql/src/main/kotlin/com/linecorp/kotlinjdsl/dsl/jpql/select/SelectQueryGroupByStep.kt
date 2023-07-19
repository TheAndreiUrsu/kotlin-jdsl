package com.linecorp.kotlinjdsl.dsl.jpql.select

import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery

interface SelectQueryGroupByStep<T> : SelectQueryHavingStep<T>, JpqlQueryable<SelectQuery<T>> {
    fun groupBy(vararg expr: Expressionable<*>): SelectQueryHavingStep<T>

    fun groupBy(expr: Iterable<Expressionable<*>>): SelectQueryHavingStep<T>
}
