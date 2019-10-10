package fr.niels.epicture.ui.activity.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import fr.niels.epicture.R
import fr.niels.epicture.model.AuthPayload
import fr.niels.epicture.ui.activity.HomeActivity
import fr.niels.epicture.utils.CLIENT_ID
import java.util.regex.Matcher
import java.util.regex.Pattern

class AuthorizationActivity : AppCompatActivity() {

    private val tag = "AuthorizationActivity"

    private var webView: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_authorization)

        webView = findViewById<WebView>(R.id.webView)
        webView!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                parseRedirectUrl(url)
                return true
            }
        }

        webView!!.loadUrl("https://api.imgur.com/oauth2/authorize?client_id=$CLIENT_ID&response_type=token")
    }

    fun parseRedirectUrl(url: String?) {
        if (url == null) {
            Log.e(tag, "Invalid redirect URL")
            return
        }

        // Initialize AuthPayload Singleton with redirection url query params.
        AuthPayload.token = getFragmentParameterFromRegex(url, "access_token=([^&]*)")
        AuthPayload.expiresIn = getFragmentParameterFromRegex(url, "expires_in=([^&]*)").toLong()
        AuthPayload.id = getFragmentParameterFromRegex(url, "id=([^&]*)")
        AuthPayload.username = getFragmentParameterFromRegex(url, "username=([^&]*)")

        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    private fun getFragmentParameterFromRegex(url: String, regex: String): String {
        val matcher: Matcher = Pattern.compile(regex).matcher(url)
        matcher.find()
        return matcher.group(1)
    }
}
