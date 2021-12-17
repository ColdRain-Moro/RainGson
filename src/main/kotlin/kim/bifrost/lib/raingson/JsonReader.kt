package kim.bifrost.lib.raingson

import kim.bifrost.lib.raingson.TempClass.Companion.serializeName
import org.json.JSONObject

/**
 * kim.bifrost.lib.raingson.JsonReader
 * RainGson
 *
 * @author 寒雨
 * @since 2021/12/17 10:41
 **/
class JsonReader(private val jsonObj: JSONObject) {

    // 将读取的字段表通过反射注入unsafeInstance
    fun into(unsafeInstance: Any) {
        val fields = TempClass.getTempClass(unsafeInstance::class.java).getAllFields()
        jsonObj.keySet().forEach { k ->
            val field = fields.firstOrNull { it.serializeName == k } ?: return@forEach
            val any = jsonObj.get(k)
            // 递归
            if (any is JSONObject) {
                val inner = unsafeInstance(field.type)
                JsonReader(any).into(inner)
                field.set(unsafeInstance, inner)
            } else field.set(unsafeInstance, jsonObj.get(k))
        }
    }

    enum class Type {
        // json格式文件中数据均以浮点数形式储存
        NUMBER,
        STRING,
        OBJECT,
        ARRAY
    }
}