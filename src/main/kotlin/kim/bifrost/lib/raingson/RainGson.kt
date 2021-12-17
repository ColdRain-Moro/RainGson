package kim.bifrost.lib.raingson

import org.json.JSONObject
import sun.misc.Unsafe

/**
 * kim.bifrost.lib.raingson.RainGson
 * 仿写Gson
 *
 * @author 寒雨
 * @since 2021/12/16 0:17
 **/
class RainGson {

    inline fun <reified T> fromJson(str: String): T {
        val unsafeInstance = unsafeInstance(T::class.java)!!
        JsonReader(JSONObject(str)).into(unsafeInstance)
        return unsafeInstance
    }

    fun toJson(obj: Any): String {
        return JsonTextParser.parseJson(obj)
    }
}