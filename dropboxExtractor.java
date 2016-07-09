package dropbox_cp;


import com.dropbox.core.*;

import java.io.*;
import java.util.Locale;

public class dropboxExtractor 
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

        /* upload
        File inputFile = new File("working-draft.txt");
        FileInputStream inputStream = new FileInputStream(inputFile);
        try {
            DbxEntry.File uploadedFile = client.uploadFile("/magnum-opus.txt",
                DbxWriteMode.add(), inputFile.length(), inputStream);
            System.out.println("Uploaded: " + uploadedFile.toString());
        } finally {
            inputStream.close();
        }
*/
        DbxEntry.WithChildren listing = client.getMetadataWithChildren("/camera_arch/2014");
        System.out.println("Files in the root path:");
        for (DbxEntry child : listing.children) 
        {
//            System.out.println("	" + child.name + ": " + child.toString());

        	
        	// 3gp avi jpg mov  mp4  png
        	
            if (child.name.endsWith("jpg")||
            		child.name.endsWith("avi")||
            		child.name.endsWith("3gp")||
            		child.name.endsWith("mov")||
            		child.name.endsWith("png")||
            		child.name.endsWith("mp4"))
            {
		        FileOutputStream outputStream = new FileOutputStream("C:\\dropbox_dl\\camera_arch\\2014\\"+child.name);
		        try {
		            DbxEntry.File downloadedFile = client.getFile(child.path, null,
		                outputStream);
		          //  System.out.println("Metadata: " + downloadedFile.toString());
		        } finally {
		            outputStream.close();
		        }
            }
        }
        System.out.println("-=END=-");
    }  // main
} // class
