package kim.bifrost.lib.raingson

import kim.bifrost.lib.raingson.TempClass.Companion.serializeName

/**
 * kim.bifrost.lib.raingson.JsonTextBuilder
 * RainGson
 *
 * @author 寒雨
 * @since 2021/12/16 22:57
 **/
internal object JsonTextParser {

    fun parseJson(any: Any): String {
        val tempClazz = TempClass.getTempClass(any.javaClass)
        return when  {
            tempClazz.clazz == String::class.java -> "\"${any as String}\""
            tempClazz.clazz == Integer::class.java ||
            tempClazz.clazz == java.lang.Long:: class.java || tempClazz.clazz == java.lang.Short:: class.java ||
            tempClazz.clazz == java.lang.Double::class.java ||
            tempClazz.clazz == java.lang.Float::class.java -> any.toString()
            tempClazz.clazz.isArray -> {
                val builder = StringBuilder("[")
                val array = any as Array<*>
                val iter = array.iterator()
                while (iter.hasNext()) {
                    val next = iter.next() ?: continue
                    builder.append(parseJson(next))
                    if (iter.hasNext()) {
                        builder.append(",")
                    }
                }
                builder.append("]").toString()
            }
            else -> {
                val builder = StringBuilder("{")
                val iter = tempClazz.getAllFields().iterator()
                while (iter.hasNext()) {
                    val next = iter.next()
                    // 递归
                    builder.append("\"${next.serializeName}\":${parseJson(next.get(any))}")
                    if (iter.hasNext()) {
                        builder.append(",")
                    }
                }
                builder.append("}").toString()
            }
        }
    }
}