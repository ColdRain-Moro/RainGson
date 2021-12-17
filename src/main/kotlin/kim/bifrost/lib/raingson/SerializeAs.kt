package kim.bifrost.lib.raingson

import java.lang.annotation.ElementType

/**
 * kim.bifrost.lib.raingson.SerializeAs
 * RainGson
 *
 * @author 寒雨
 * @since 2021/12/17 0:06
 **/
@Target(AnnotationTarget.FIELD)
annotation class SerializeAs(val name: String)
