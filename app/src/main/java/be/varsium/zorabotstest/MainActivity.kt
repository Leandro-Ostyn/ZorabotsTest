package be.varsium.zorabotstest

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import be.varsium.DataServiceImpl
import be.varsium.models.AdvancedComposer
import be.varsium.models.NamedFile
import kotlinx.coroutines.launch
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.lang.reflect.Field


class MainActivity : AppCompatActivity() {
    private lateinit var resource: MutableList<NamedFile>
   public var moduleList = mutableListOf<Module>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        lifecycleScope.launch() {
            resource = returnFilesFromRaw()
        }

        for (file in resource) {
            val module =
                module {
                    single(named(file.name)) { AdvancedComposer.fromJson(file.fileInJsonString)?.let { it1 ->
                        DataServiceImpl(
                            it1
                        )
                    } }
                }
            moduleList.add(module)
        }
        startKoin {
            modules(moduleList)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun readTextFile(inputStream: InputStream): String {
        val outputStream = ByteArrayOutputStream()
        val buf = ByteArray(2056)
        var len: Int
        try {
            while (inputStream.read(buf).also { len = it } != -1) {
                outputStream.write(buf, 0, len)
            }
            outputStream.close()
            inputStream.close()
        } catch (e: IOException) {
        }
        return outputStream.toString()
    }

    private fun returnFilesFromRaw(): MutableList<NamedFile> {
        val mutableList = mutableListOf<NamedFile>()
        val c = R.raw::class.java
        val fields: Array<Field> = c.declaredFields
        for (field in fields) {
            mutableList.add(
                NamedFile(
                    name = field.name, readTextFile(
                        resources.openRawResource(
                            resources.getIdentifier(
                                field.name,
                                "raw",
                                this.packageName
                            )
                        )
                    )
                )
            )
        }
        return mutableList
    }
}