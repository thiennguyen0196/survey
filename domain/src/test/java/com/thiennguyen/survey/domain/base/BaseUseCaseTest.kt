package com.thiennguyen.survey.domain.base

import org.junit.Before
import org.mockito.MockitoAnnotations

open class BaseUseCaseTest {

    @Before
    open fun setUp() {
        MockitoAnnotations.openMocks(this)
    }
}
