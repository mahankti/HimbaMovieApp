package com.hexco.himba.ui.util

import com.hexco.himba.domain.util.Resource

class Paginator<Item, Key>(
  private val initialKey: Key,
  private inline val getNextKey: (Key) -> Key,
  private inline val request: suspend (nextKey: Key) -> Resource<List<Item>>,
  private inline val onLoadUpdated: (load: Boolean) -> Unit,
  private inline val onSuccess: (result: List<Item>?) -> Unit,
  private inline val onError: (message: String?) -> Unit,
) {
  private var key: Key = initialKey
  private var isBusy = false
  private var endReached = false

  suspend fun fetchNextPage() {
    if (isBusy || endReached) return

    isBusy = true

    val newKey = getNextKey(key)
    val result = request(newKey)

    onLoadUpdated(true)
    when (result) {
      is Resource.Success -> {
        onSuccess(result.data)
        key = newKey
      }

      is Resource.Error -> {
        if (key == initialKey) {
          onError(result.message)
        }
        endReached = true
      }
    }

    onLoadUpdated(false)
    isBusy = false
  }
}