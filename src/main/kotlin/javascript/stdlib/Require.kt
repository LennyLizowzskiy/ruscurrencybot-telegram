package javascript.stdlib

/**
 * Импорт модулей из JavaScript
 *
 * @param[moduleOrPath] Путь к файлу или название NPM-пакета
 * @return Запрошенное [Any] или пишет ошибку в терминал
 */
external fun require(moduleOrPath: String): dynamic