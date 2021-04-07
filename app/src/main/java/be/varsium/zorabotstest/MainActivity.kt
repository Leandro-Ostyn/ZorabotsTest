package be.varsium.zorabotstest

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import be.varsium.DataServiceImpl
import be.varsium.models.Advanced_composer
import kotlinx.coroutines.launch
import org.koin.core.context.startKoin
import org.koin.dsl.module
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.lang.reflect.Field
import java.nio.file.Files
import java.nio.file.Paths


class MainActivity : AppCompatActivity() {
    private lateinit var resource: MutableList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        lifecycleScope.launch() {
            resource = returnFilesFromRaw()
        }

        val jsonFile = Advanced_composer.fromJson(resource[0])


        if (jsonFile != null) {
            val advancedComposerModule = module {
                single { DataServiceImpl(jsonFile).Data() }
            }

            startKoin {
                modules(advancedComposerModule)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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

    private fun returnFilesFromRaw(): MutableList<String> {
        val mutableList = mutableListOf<String>()
        val c = R.raw::class.java
        val fields: Array<Field> = c.declaredFields
        returnNamesFromModels()
        //    val classes: Array<String>=
        for (field in fields) {
            mutableList.add(
                readTextFile(
                    resources.openRawResource(
                        resources.getIdentifier(
                            field.name,
                            "raw",
                            this.packageName
                        )
                    )
                )
            )
        }
        return mutableList
    }

    private fun returnNamesFromModels() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Files.list(Paths.get(System.getProperty("user.dir"))).forEach{ it -> println(it.fileName) }
        }
        Log.d("test",File("models").path)
        val files = File(filesDir.toURI()).listFiles()
        val fileNames = arrayOfNulls<String>(files.size)
        files?.mapIndexed { index, item ->
            fileNames[index] = item?.name
        }
        Log.d("name",fileNames[0].toString())
    }


    //Advanced_composer::class.simpleName!!)
}