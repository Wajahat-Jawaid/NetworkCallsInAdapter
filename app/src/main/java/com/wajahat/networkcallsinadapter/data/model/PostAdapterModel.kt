package com.wajahat.networkcallsinadapter.data.model

/**
 * Base class for the Posts list models. Since there are multiple view types to be maintained by the Adapter,
 * we need a post-level generic class to map data onto the adapter.
 * It's inherited by the [Post] and [PostHeader]
 * */
abstract class PostAdapterModel {

    // View type to be overridden by the child classes
    abstract var type: Int

    companion object {
        const val TYPE_HEADER = 1
        const val TYPE_ITEM = 2
    }
}