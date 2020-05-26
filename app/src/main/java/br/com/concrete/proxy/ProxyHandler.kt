package br.com.concrete.proxy

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.lang.StringBuilder
import java.lang.reflect.Method
import java.lang.reflect.Proxy


@Suppress("UNCHECKED_CAST")
class ProxyHandler {
    val client = OkHttpClient()
    fun <T> create(service: Class<T>): T {
        return Proxy.newProxyInstance(
            service.classLoader,
            arrayOf<Class<*>>(service)
        ) { _, method, args ->
            interfaceValidate(service)
            parse( method, args)

        } as T
    }

    private fun interfaceValidate(service: Class<*>) {
        require(service.isInterface) { "Api precisa ser uma interface" }
    }

    private fun parse(method: Method, args: Array<*>?) {
        print(method)
        checkNotNull(args)

        for (annotation in method.annotations){
            if(annotation is GET){
                val query = StringBuilder("?")
                for ((index, arg) in args.withIndex()){
                    val queryAnnotation = method.parameters[index].getAnnotation(Query::class.java)
                    queryAnnotation?.run {
                        query
                            .append(this.value)
                            .append("=")
                            .append(arg)
                            .append("&")
                    }

                }

                print(run(query.toString(),"GET"))
            }
        }

    }

    @Throws(IOException::class)
    private fun run(query: String, method: String): String? {
    val url = "https://baconipsum.com/api/$query"
        println(url)
        val request =  Request.Builder()
            .url(url)
            .build()

        client.newCall(request).execute().use { response -> return response.body?.string() }
    }

}