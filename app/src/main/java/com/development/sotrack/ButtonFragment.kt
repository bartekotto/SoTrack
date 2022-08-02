import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.development.sotrack.R
import com.development.sotrack.log.LogListHolder
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class ButtonFragment : Fragment(R.layout.fragment_button) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.export_button).setOnClickListener {
            writeTextToFile(collectData())
        }

    }


    private fun collectData(): String? {
        var gson: Gson? = null
        gson = GsonBuilder().serializeNulls().setPrettyPrinting().create()
        return gson?.toJson(LogListHolder.logList.list)
    }

    private fun getRandomFileName(): String {
        return Calendar.getInstance().timeInMillis.toString() + ".json"
    }

    private fun writeTextToFile(jsonResponse: String?) {
        if (jsonResponse != "") {
            val dir = File("//sdcard//Download//")
            val myExternalFile = File(dir, getRandomFileName())
            var fos: FileOutputStream? = null
            try {
                fos = FileOutputStream(myExternalFile)
                fos.write(jsonResponse?.toByteArray())
                fos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            Toast.makeText(
                context,
                "Information saved to SD card. $myExternalFile",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
