package kim.bifrost.lib.raingson

import java.lang.reflect.Field

/**
 * kim.bifrost.lib.raingson.TempClass
 * RainGson
 * 缓存类
 * 只缓存超类与字段即可
 *
 * @author 寒雨
 * @since 2021/12/16 0:25
 **/
class TempClass(clazz: Class<*>) {

    val clazz: Class<*>

    // 超类缓存
    private var superClass: TempClass? = null

    // 字段缓存 只缓存字段即可
    val savingField = ArrayList<Field>()

    init {
        this@TempClass.clazz = clazz.nonPrimitive()
        kotlin.runCatching {
            savingField.addAll(clazz.declaredFields.map {
                it.isAccessible = true
                it
            })
            if (clazz.superclass != null && clazz.superclass != Object::class.java) {
                superClass = TempClass(clazz.superclass)
            }
            tempClasses.put(clazz.name, this)
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun getAllFields() = (savingField.clone() as ArrayList<Field>).also {
        var superClazz = superClass
        while (superClazz != null) {
            if (superClazz.clazz == Object::class.java) break
            it.addAll(superClazz.savingField)
            superClazz = superClazz.superClass
        }
    }

    companion object {
        val tempClasses = HashMap<String, TempClass>()

        fun getTempClass(clazz: Class<*>) = if (tempClasses.containsKey(clazz.name)) tempClasses[clazz.name]!!
        else TempClass(clazz)

        val Field.serializeName
            get() = if (isAnnotationPresent(SerializeAs::class.java)) getAnnotation(SerializeAs::class.java).name else name

        // 取得装箱之后的类型
        private fun Class<*>.nonPrimitive(): Class<*> {
            return when {
                this == Integer.TYPE -> Integer::class.java
                this == Character.TYPE -> Character::class.java
                this == java.lang.Byte.TYPE -> java.lang.Byte::class.java
                this == java.lang.Long.TYPE -> java.lang.Long::class.java
                this == java.lang.Double.TYPE -> java.lang.Double::class.java
                this == java.lang.Float.TYPE -> java.lang.Float::class.java
                this == java.lang.Short.TYPE -> java.lang.Short::class.java
                this == java.lang.Boolean.TYPE -> java.lang.Boolean::class.java
                else -> this
            }
        }
    }
}