package com.cotel.architecture.base.infrastructure

data class PaginationState(
    val page: Int = 1,
    val isLoading: Boolean = false,
    val areThereMorePages: Boolean = true
) {
    fun canRequestNextPage(): Boolean =
        !isLoading && areThereMorePages

    fun isLoading(): PaginationState =
        copy(isLoading = true)

    fun isNotLoading(): PaginationState =
        copy(isLoading = false)

    fun noMorePages(): PaginationState =
        copy(areThereMorePages = false)

    fun nextPage(): PaginationState =
        copy(page = page + 1)
}
