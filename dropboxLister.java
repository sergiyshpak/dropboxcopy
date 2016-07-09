package dropbox_cp;


import com.dropbox.core.*;

import java.io.*;
import java.util.Locale;

public class dropboxLister 
{
    public static void main(String[] args) throws IOException, DbxException 
    {
        // Get your app key and secret from the Dropbox developers website.
        final String APP_KEY = "4zfmg2c06m7cctu";
        final String APP_SECRET = "ov4gi80scic5fyc";

        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);

        DbxRequestConfig config = new DbxRequestConfig("JavaTutorial/1.0",
            Locale.getDefault().toString());
        DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);

        // Have the user sign in and authorize your app.
        String authorizeUrl = webAuth.start();
        System.out.println("1. Go to: " + authorizeUrl);
        System.out.println("2. Click \"Allow\" (you might have to log in first)");
        System.out.println("3. Copy the authorization code.");
        String code = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();

        // This will fail if the user enters an invalid authorization code.
        DbxAuthFinish authFinish = webAuth.finish(code);
        String accessToken = authFinish.accessToken;

        DbxClient client = new DbxClient(config, accessToken);

        System.out.println("Linked account: " + client.getAccountInfo().displayName);

        DbxEntry.WithChildren listing = client.getMetadataWithChildren("/camera_arch/2014");
        PrintWriter writer = new PrintWriter("C:\\dropbox_dl\\camera_arch\\2014\\list");
        for (DbxEntry child : listing.children) 
        {
        	writer.println("	" + child.name + ": " + child.toString());
        }
        writer.close();
        System.out.println("-=END=-");
    }  // main
} // class
