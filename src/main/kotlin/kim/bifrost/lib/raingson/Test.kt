package kim.bifrost.lib.raingson

/**
 * kim.bifrost.lib.raingson.Test
 * RainGson
 *
 * @author 寒雨
 * @since 2021/12/16 23:39
 **/
fun main() {
    val gson = RainGson()
    val json = gson.toJson(TestBean(TestInnerBean("test", 1, 2), "测试"))
    println(json)
    val testBean = gson.fromJson<TestBean>(json)
    println(testBean.str)
    println(testBean.inner.str)
}

class TestBean(
    @SerializeAs("innerObj") val inner: TestInnerBean,
    val str: String
)

class TestInnerBean(
    val str: String,
    val int: Int,
    val long: Long
)