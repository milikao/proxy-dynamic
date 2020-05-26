package br.com.concrete.proxy

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method


fun main() {
    ProxyHandler().create(Api::class.java).myMethod("meat-and-filler",2,1)
}

