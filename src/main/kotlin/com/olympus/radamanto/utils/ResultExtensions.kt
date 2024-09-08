package com.olympus.radamanto.utils

/**
 * Transforms a Result<T> into a Result<R> by applying a transformation function that itself returns a Result.
 *
 * This function allows for chaining of Result-returning operations, where each operation depends on the success
 * of the previous one. If the original Result is a failure, the transformation is not applied and the failure is propagated.
 *
 * @param T The type of the value in the original Result.
 * @param R The type of the value in the transformed Result.
 * @param transform A function that takes a value of type T and returns a Result<R>.
 * @return A new Result<R> that is either the result of the transformation or a propagated failure.
 */
inline fun <T, R> Result<T>.flatMap(transform: (T) -> Result<R>): Result<R> {
    return when {
        isSuccess -> transform(getOrThrow())
        else -> Result.failure(exceptionOrNull()!!)
    }
}
