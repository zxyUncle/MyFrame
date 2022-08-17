package com.normal.zbase.http.build

/**
 * Created by zsf on 2022/8/16 19:44
 * ******************************************
 * *
 * ******************************************
 */
class PostHttpRequestServiceImp : HttpRequestService() {
    private var body: Map<String, @JvmSuppressWildcards Any> = mapOf()

    override fun path(path: String): PostHttpRequestServiceImp {
        super.path(path)
        return this
    }

    override fun host(host: String): PostHttpRequestServiceImp {
         super.host(host)
        return this
    }

    override fun params(params: Map<Any, @JvmSuppressWildcards Any>?): PostHttpRequestServiceImp {
        super.params(params)
        return this
    }

    fun body (body: Map<String, @JvmSuppressWildcards Any>):PostHttpRequestServiceImp{
        this.body = body;
        return this
    }





}