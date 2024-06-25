package im.liuxing.dict

import org.apache.commons.dbutils.QueryRunner
import org.apache.commons.dbutils.handlers.MapListHandler
import org.sqlite.Function
import org.sqlite.SQLiteDataSource
import java.sql.Connection
import java.util.regex.Pattern


fun main(args: Array<String>) {

}


val queryRunner = QueryRunner(
    object: SQLiteDataSource() {
        override fun getConnection(): Connection {
            return super.getConnection().apply {
                val patterns = mutableMapOf<String, Pattern>()
                Function.create(this, "regexp", object : Function() {
                    override fun xFunc() {
                        val pattern = value_text(0).let { exp -> patterns.computeIfAbsent(exp, Pattern::compile) }
                        val value = value_text(1) ?: ""
                        result(if (pattern.matcher(value).find()) 1 else 0)
                    }
                })
            }
        }
    }.apply {
        url = "jdbc:sqlite:" + System.getenv()["DICT_DB_PATH"]
    }
)

fun findByRegex(regex: String, limit: Int = 5): List<String> {
    return queryRunner.query(
        // language=sqlite
        "select spelling from word where spelling regexp ? limit ?",
        MapListHandler(),
        regex, limit
    ).map { row ->
        row["spelling"] as String
    }
}
