package im.liuxing.dict


import picocli.CommandLine
import picocli.CommandLine.*


fun main(args: Array<String>) {
    CommandLine(Main()).execute(*args)
}

@Command(
    name = "",
    subcommands = [
        SearchRunner::class,
    ],
    description = ["字典工具"],
    version = ["0.0.1"],
    usageHelpAutoWidth = true
)
class Main {
    @Mixin
    @Suppress("unused")
    lateinit var helpOpt: HelpOption
}

@Command(
    name = "search",
    description = ["搜索"]
)
class SearchRunner : Runnable {

    @Mixin
    @Suppress("unused")
    lateinit var helpOpt: HelpOption

    @Option(names = ["-c", "--count"])
    private var count: Int = 10

    @Parameters(paramLabel = "<key>", arity = "1")
    private lateinit var keyword: String

    override fun run() {
        findByRegex(keyword, count).forEach(::println)
    }

}

class HelpOption {
    @Suppress("unused")
    @Option(names = ["-h", "--help"], usageHelp = true, description = ["帮助"])
    private var helpOpt: Boolean = false
}

fun findByRegex(regex: String, limit: Int = 5): List<String> {
    // TODO
    return listOf()
}
