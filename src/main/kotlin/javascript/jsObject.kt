package javascript

/**
 * Возвращает пары key-value JS-объекта для обхода через цикл
 *
 * [Взято отсюда](https://gist.github.com/Foso/f254410ba99722101b000e03181c4f45)
 */
fun entriesOf(jsObject: dynamic): List<Pair<String, Any?>> =
    (js("Object.entries") as (dynamic) -> Array<Array<Any?>>)
        .invoke(jsObject)
        .map { entry -> entry[0] as String to entry[1] }

/**
 * JS-объект в виде [Map]
 *
 * [Взято отсюда](https://gist.github.com/Foso/f254410ba99722101b000e03181c4f45)
 */
fun mapOf(jsObject: dynamic): Map<String, Any?> =
    entriesOf(jsObject).toMap()