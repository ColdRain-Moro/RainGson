package kim.bifrost.lib.raingson

import sun.misc.Unsafe

/**
 * kim.bifrost.lib.raingson.Utils
 * RainGson
 *
 * @author 寒雨
 * @since 2021/12/17 11:27
 **/
// 用unsafe获取unsafe的类实例
private val unsafe by lazy {
    Unsafe::class.java.getDeclaredField("theUnsafe").run {
        isAccessible = true
        get(null) as Unsafe
    }
}

@Suppress("UNCHECKED_CAST")
fun <T> unsafeInstance(clazz: Class<T>): T = unsafe.allocateInstance(clazz) as T