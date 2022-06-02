import java.io.File
import java.io.PrintStream
import javax.swing.*
import javax.swing.event.EventListenerList
import javax.swing.event.ListDataListener
import kotlin.system.exitProcess


var tries = 0
val text = JList(arrayOf("Deleter starting..."))

val logfile = File("skyclientupdater/files/deleterlog.txt")

fun main(files : Array<String>) {
    append("+++ ARGS")
    append(files.joinToString())
    append("--- ARGS")

    val frame = JFrame("Deleter 1.7")
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
    val scroll = JScrollPane(text)
    scroll.setBounds(0, 0, 500, 500)
    frame.add(scroll)
    frame.setSize(500, 500)
    frame.isVisible = true
    frame.isFocusable = true
    frame.isAlwaysOnTop = true
    frame.setLocationRelativeTo(null)
    frame.isResizable = false
    frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
    for (file in files) {
        tries = 0
        delete(File(file))
    }
    Thread.sleep(1000)
    exitProcess(0)
}

private fun delete(file: File) {
    append("checking if \"${file.absolutePath}\" exists")
    if (!file.exists()) {
        append("\"${file.absolutePath}\" does not exist!")
        return
    }

    append("deleting file \"${file.absolutePath}\"")

    if (!file.delete()) {
        tries += 1
        if (tries >= 4) {
            append("\nToo many tries for \"${file.absolutePath}\", cancelling.")
            return
        }
        append("\n\"${file.absolutePath}\" failed to delete, trying again in $tries seconds...")
        Thread.sleep((1000 * tries).toLong())
        delete(file)
    } else {
        append("Successfully deleted \"${file.absolutePath}\"!")
    }
}

private fun append(string: String) {
    logfile.appendText(string + "\n")
    println(string)
    text.model = JListList(getList().also { it.add(string) })
}

private fun getList(): ArrayList<String> {
    val list = arrayListOf<String>()
    var i = 0
    while (i < text.model.size) {
        i++
        list.add(text.model.getElementAt(i - 1))
    }
    return list
}

class JListList(private val list: List<String>): ListModel<String> {
    private var listeners = EventListenerList()
    override fun getSize(): Int = list.size

    override fun getElementAt(index: Int): String = list[index]

    override fun addListDataListener(l: ListDataListener) {
        listeners.add(ListDataListener::class.java, l)
    }

    override fun removeListDataListener(l: ListDataListener) {
        listeners.remove(ListDataListener::class.java, l)
    }
}
