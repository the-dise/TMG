package me.thedise.tmg

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.FileProvider
import me.thedise.tmg.BuildConfig.APPLICATION_ID
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //set switches
        getData()

        //Buttons Telegram
        val buttonTelegramDark: Button = findViewById(R.id.setup_dark_button)
        val buttonTelegramLight: Button = findViewById(R.id.setup_light_button)

        //Buttons About Card
        val buttonAboutGithub: Button = findViewById(R.id.button_github)
        val buttonAboutTg: Button = findViewById(R.id.button_tg)
        val descriptionTitle: TextView = findViewById(R.id.description_title)

        descriptionTitle.text = getString(R.string.about_card_title, BuildConfig.VERSION_NAME)

        //Create telegram Dark theme
        buttonTelegramDark.setOnClickListener {
            val darkMonetFile = "monet_dark.attheme"
            var darkThemeImport =
                application.assets.open(darkMonetFile).bufferedReader().readText()
            var fileName = "Dark Theme.attheme"

            val themeString = changeTextTelegram(darkThemeImport, applicationContext)

            File(applicationContext.cacheDir, fileName).writeText(text = themeString)

            val themeName: String = resources.getString(R.string.dark_theme)

            shareTheme(themeName, fileName)
        }

        //Create telegram Light theme
        buttonTelegramLight.setOnClickListener {
            val lightMonetFile = "monet_light.attheme"
            var lightThemeImport =
                application.assets.open(lightMonetFile).bufferedReader().readText()

            val fileName = "Light Theme.attheme"

            val themeString = changeTextTelegram(lightThemeImport, applicationContext)

            File(applicationContext.cacheDir, fileName).writeText(text = themeString)

            val themeName: String = resources.getString(R.string.light_theme)

            shareTheme(themeName, fileName)
        }

        //Button - go to GitHub
        buttonAboutGithub.setOnClickListener {
            openLink("https://github.com/the-dise/TMG")
        }

        //Button - go to Telegram
        buttonAboutTg.setOnClickListener {
            openLink("https://t.me/thedise")
        }
    }

    //Open links fun
    private fun openLink(link: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(link)
        startActivity(i)
    }

    //Share theme fun
    private fun shareTheme(theme: String, file_name: String) {
        val file = File(applicationContext.cacheDir, file_name)
        val uri = FileProvider.getUriForFile(
            applicationContext,
            "$APPLICATION_ID.provider",
            file
        )
        val intent = Intent(Intent.ACTION_SEND)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.type = "*/*"
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(Intent.createChooser(intent, theme))
    }

    private fun getData() {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("switchSettings", MODE_PRIVATE)

        val useGradient: SwitchCompat = findViewById(R.id.switchGradient)
        val useGradientAvatars: SwitchCompat = findViewById(R.id.switchGradientAvatars)
        val isAmoledMode: SwitchCompat = findViewById(R.id.switchAmoledPhone)

        useGradient.isChecked = sharedPreferences.getBoolean("useGradient", false)
        useGradientAvatars.isChecked = sharedPreferences.getBoolean("useGradientAvatars", false)
        isAmoledMode.isChecked = sharedPreferences.getBoolean("isAmoledMode", false)
    }

    override fun onPause() {
        super.onPause()
    }

}